package com.example.demo.core;

import com.example.demo.dto.in.ShopFilter;
import com.example.demo.dto.out.Shop;

public interface ShopCore
{
  Shop search(ShopFilter filter);
}
