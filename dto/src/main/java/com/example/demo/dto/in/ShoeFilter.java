package com.example.demo.dto.in;

import lombok.Value;

@Value
public class ShoeFilter {

  Long size;
  Color color;

  public enum Color{
    BLACK,
    BLUE,
    RED,
    GREEN
  }

}
