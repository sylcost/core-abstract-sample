package com.example.demo.dto.in;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor
public class StocksUpdate
{
  List<StockUpdate> stocks = new ArrayList<>();

  @Value
  @AllArgsConstructor
  public static class StockUpdate {
    Long shoeId;
    Long quantity;

  }
}
