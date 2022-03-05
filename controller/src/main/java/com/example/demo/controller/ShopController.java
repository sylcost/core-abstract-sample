package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.dto.in.ShopFilter;
import com.example.demo.dto.out.Shop;
import com.example.demo.facade.ShopFacade;

@Controller
@RequestMapping(path = "/shop")
@RequiredArgsConstructor
public class ShopController
{

  private final ShopFacade shopFacade;

  @GetMapping(path = "/search")
  public ResponseEntity<Shop> search(@RequestHeader final Integer version, final ShopFilter filter) {
    return ResponseEntity.ok(shopFacade.get(version).search(filter));
  }

}
