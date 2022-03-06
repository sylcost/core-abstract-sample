package com.example.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dto.in.ShopFilter;
import com.example.demo.dto.out.Shop;
import com.example.demo.models.ShopEntity;
import com.example.demo.repository.ShopRepository;
import com.example.demo.repository.StockRepository;

@Implementation(version = 1)
public class ShopCoreImpl
	extends AbstractShopCore {

  private final ShopRepository shopRepository;

  @Autowired
  public ShopCoreImpl(final ShopRepository shopRepository, final StockRepository stockRepository) {
    this.shopRepository = shopRepository;
  }

  @Override
  public Shop search(final ShopFilter filter) {
    ShopEntity shop = shopRepository.findFirstByName(filter.getName());
    if (shop != null) {
      return Shop.builder()
                 .id(shop.getId())
                 .name(shop.getName())
                 .build();
    }

    return Shop.builder().build();
  }

}
