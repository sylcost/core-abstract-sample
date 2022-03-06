package com.example.demo.dto.in;

import java.util.Collections;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor
public class StocksUpdate
{

  List<StockUpdate> stocks = Collections.emptyList();

  @Value
  public static class StockUpdate {
    Long shoeId;
    Long quantity;
  }
}
