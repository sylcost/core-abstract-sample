package com.example.demo.core;

import java.math.BigInteger;
import java.util.List;

import com.example.demo.dto.enums.Color;
import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;

@Implementation(version = 1)
public class ShoeCoreLegacy extends AbstractShoeCore {

  @Override
  public Shoes search(final ShoeFilter filter) {
    return Shoes.builder()
                .shoes(List.of(Shoe.builder()
                                   .name("Legacy shoe")
                                   .color(Color.BLUE)
                                   .size(BigInteger.ONE)
                                   .build()))
                .build();
  }
}
