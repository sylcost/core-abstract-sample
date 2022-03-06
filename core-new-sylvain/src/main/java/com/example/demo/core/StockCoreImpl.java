package com.example.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dto.enums.StockState;
import com.example.demo.dto.in.StocksUpdate;
import com.example.demo.dto.out.ShoeStock;
import com.example.demo.dto.out.Stock;
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
   * Update only if shop and shoe exists.
   */
  @Override
  public void patch(final Long shopId, final StocksUpdate stocksUpdate)
  {
    shopRepository.findById(shopId).ifPresent(shopEntity -> {
      stocksUpdate.getStocks().forEach(stockUpdate -> shoeRepository.findById(stockUpdate.getShoeId()).ifPresent(shoeEntity -> {
        StockEntity stockEntity = stockRepository.findFirstByShoeAndShop(shoeEntity, shopEntity);
        if (stockEntity != null) {
          stockEntity.setQuantity(stockUpdate.getQuantity());
          stockRepository.save(stockEntity);
        } else {
          StockEntity newStock = new StockEntity();
          newStock.setShop(shopEntity);
          newStock.setShoe(shoeEntity);
          newStock.setQuantity(stockUpdate.getQuantity());
          stockRepository.save(newStock);
        }
      }));
    });
  }
}
