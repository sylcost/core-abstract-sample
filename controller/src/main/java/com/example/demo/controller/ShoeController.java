package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Shoes;
import com.example.demo.facade.ShoeFacade;

@RestController
@RequestMapping(path = "/shoes")
@RequiredArgsConstructor
public class ShoeController {

  private final ShoeFacade shoeFacade;

  @GetMapping(path = "/search")
  public Shoes all(final ShoeFilter filter, @RequestHeader final Integer version){
    return shoeFacade.get(version).search(filter);
  }

}
