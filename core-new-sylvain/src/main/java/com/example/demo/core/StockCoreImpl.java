package com.example.demo.core;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import com.example.demo.dto.enums.StockState;
import com.example.demo.dto.exceptions.TooMuchShoesException;
import com.example.demo.dto.in.StocksUpdate;
import com.example.demo.dto.out.ShoeStock;
import com.example.demo.dto.out.Stock;
import com.example.demo.models.ShoeEntity;
import com.example.demo.models.StockEntity;
import com.example.demo.repository.ShoeRepository;
import com.example.demo.repository.ShopRepository;
import com.example.demo.repository.StockRepository;

@Implementation(version = 1)
public class StockCoreImpl
	extends AbstractStockCore {

  private final ShopRepository shopRepository;
  private final ShoeRepository shoeRepository;
  private final StockRepository stockRepository;

  @Autowired
  public StockCoreImpl(final ShopRepository shopRepository,
                       final ShoeRepository shoeRepository,
                       final StockRepository stockRepository) {
    this.shopRepository = shopRepository;
    this.shoeRepository = shoeRepository;
    this.stockRepository = stockRepository;
  }

  @Override
  public Stock get(final Long shopId) {
    Stock.StockBuilder result = Stock.builder();
    shopRepository.findById(shopId).ifPresent(shop -> {
      Long totalQuantity = shop.getStocks().stream().map(StockEntity::getQuantity).reduce(0L, Long::sum);
      result
           .state(StockState.fromQuantity(totalQuantity).name())
           .shoes(shop.getStocks().stream().map(stockEntity -> ShoeStock.builder()
                                                                      .id(stockEntity.getShoe().getId())
                                                                      .size(stockEntity.getShoe().getSize())
                                                                      .color(stockEntity.getShoe().getColor())
                                                                      .name(stockEntity.getShoe().getName())
                                                                      .quantity(stockEntity.getQuantity())
                                                                      .build()).toList())
          .build();
    });
    return result.build();
  }

  /**
   * Update only if shop exist.
   * Remove all stock and replace by new one.
   * New stock is created only if shoe exist.
   */
  @Override
  public void patchFull(final Long shopId, final StocksUpdate stocksUpdate)
      throws TooMuchShoesException
  {
    shopRepository.findById(shopId).ifPresent(shopEntity -> {
        Long totalQuantity = stocksUpdate.getStocks().stream().map(StocksUpdate.StockUpdate::getQuantity).reduce(0L, Long::sum);
        if (totalQuantity > 30) {
          throw new TooMuchShoesException("Too much shoes for Shop " + shopId);
        }
        List<StockEntity> newStock = stocksUpdate.getStocks().stream().map(stockUpdate -> {
          // If shoe exist, add to new stock.
          Optional<ShoeEntity> shoeEntityOptional = shoeRepository.findById(stockUpdate.getShoeId());
          if (shoeEntityOptional.isPresent()) {
            StockEntity stockEntity = new StockEntity();
            stockEntity.setShop(shopEntity);
            stockEntity.setShoe(shoeEntityOptional.get());
            stockEntity.setQuantity(stockUpdate.getQuantity());
            return stockEntity;
          } else {
            return null;
          }
        }).filter(Objects::nonNull).toList();
        stockRepository.deleteAllByShop(shopEntity);
        stockRepository.saveAll(newStock);
    });

  }

  @Override
  public void patchUnitary(final Long shopId, final StocksUpdate.StockUpdate stockUpdate)
      throws TooMuchShoesException
  {

    shopRepository.findById(shopId).ifPresent(shopEntity -> shoeRepository.findById(stockUpdate.getShoeId()).ifPresent(shoeEntity -> {
      List<StockEntity> currentStock = stockRepository.findByShop(shopEntity);
      final AtomicLong oldQuantity = new AtomicLong(0);
      // Check if the new quantity will exceed the 30 shoes limit.
      if ( !CollectionUtils.isEmpty(currentStock) ) {
        currentStock.stream().filter(stockEntity -> Objects.equals(
            stockEntity.getShoe().getId(), stockUpdate.getShoeId())).findFirst().ifPresent(stock -> {
              oldQuantity.set(stock.getQuantity());
        });
      }

      if (getTotalQuantity(currentStock) - oldQuantity.get() + stockUpdate.getQuantity() > 30) {
        throw new TooMuchShoesException("Too much shoes for shop "+shopEntity.getId());
      }

      // Create or update StockEntity.
      StockEntity stockUpdated = new StockEntity();
      stockUpdated.setShoe(shoeEntity);
      stockUpdated.setShop(shopEntity);
      stockUpdated.setQuantity(stockUpdate.getQuantity());
      stockRepository.save(stockUpdated);
    }));
  }

  private Long getTotalQuantity(final List<StockEntity> stocks) {
    Long result = 0L;
    if (!CollectionUtils.isEmpty(stocks)) {
      result = stocks.stream().map(StockEntity::getQuantity).reduce(0L, Long::sum);
    }
    return result;
  }

}
