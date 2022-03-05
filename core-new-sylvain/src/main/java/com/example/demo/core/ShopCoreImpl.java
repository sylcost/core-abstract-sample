package com.example.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dto.enums.StockState;
import com.example.demo.dto.in.ShopFilter;
import com.example.demo.dto.out.ShoeStock;
import com.example.demo.dto.out.Shop;
import com.example.demo.models.ShopEntity;
import com.example.demo.models.StockEntity;
import com.example.demo.repository.ShopRepository;

@Implementation(version = 1)
public class ShopCoreImpl
	extends AbstractShopCore {

  private final ShopRepository shopRepository;

  @Autowired
  public ShopCoreImpl(final ShopRepository shopRepository) {
    this.shopRepository = shopRepository;
  }

  @Override
  public Shop search(final ShopFilter filter) {
    ShopEntity shop = shopRepository.findFirstByName(filter.getName());
    if (shop != null) {
      Long totalQuantity = shop.getStocks().stream().map(StockEntity::getQuantity).reduce(0L, Long::sum);
      return Shop.builder()
                 .name(shop.getName())
                 .state(StockState.fromQuantity(totalQuantity).name())
                 .shoes(shop.getStocks().stream().map(stockEntity -> ShoeStock.builder()
                                                                               .size(stockEntity.getShoe().getSize())
                                                                               .color(stockEntity.getShoe().getColor())
                                                                               .quantity(stockEntity.getQuantity())
                                                                               .build())
                            .toList())
                 .build();
    }

    return null;
  }
}
