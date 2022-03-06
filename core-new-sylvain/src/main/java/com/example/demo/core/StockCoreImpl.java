package com.example.demo.core;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dto.enums.StockState;
import com.example.demo.dto.exceptions.TooMuchShoesException;
import com.example.demo.dto.in.StocksUpdate;
import com.example.demo.dto.out.ShoeStock;
import com.example.demo.dto.out.Stock;
import com.example.demo.models.ShoeEntity;
import com.example.demo.models.ShopEntity;
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
  public void patch(final Long shopId, final StocksUpdate stocksUpdate)
      throws TooMuchShoesException
  {
    Optional<ShopEntity> shopEntityOptional = shopRepository.findById(shopId);

    if (shopEntityOptional.isPresent()) {
      Long totalQuantity = stocksUpdate.getStocks().stream().map(StocksUpdate.StockUpdate::getQuantity).reduce(0L, Long::sum);
      if (totalQuantity > 30) {
        throw new TooMuchShoesException("Too much shoes for Shop " + shopId);
      }
      List<StockEntity> newStock = stocksUpdate.getStocks().stream().map(stockUpdate -> {
        // If shoe exist, add to new stock.
        Optional<ShoeEntity> shoeEntityOptional = shoeRepository.findById(stockUpdate.getShoeId());
        if (shoeEntityOptional.isPresent()) {
          StockEntity stockEntity = new StockEntity();
          stockEntity.setShop(shopEntityOptional.get());
          stockEntity.setShoe(shoeEntityOptional.get());
          stockEntity.setQuantity(stockUpdate.getQuantity());
          return stockEntity;
        } else {
          return null;
        }
      }).filter(Objects::nonNull).toList();
      stockRepository.deleteAllByShop(shopEntityOptional.get());
      stockRepository.saveAll(newStock);
    }

  }
}
