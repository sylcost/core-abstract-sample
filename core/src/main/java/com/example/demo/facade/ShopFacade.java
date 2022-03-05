package com.example.demo.facade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.example.demo.core.ShopCore;

@Component
public class ShopFacade
{

  private final Map<Integer, ShopCore> implementations = new HashMap<>();

  public ShopCore get(Integer version){
    return implementations.get(version);
  }

  public void register(Integer version, ShopCore implementation){
    this.implementations.put(version, implementation);
  }

}
