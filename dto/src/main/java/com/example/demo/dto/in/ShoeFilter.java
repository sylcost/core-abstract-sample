package com.example.demo.dto.in;

import lombok.Value;
import com.example.demo.dto.enums.Color;

@Value
public class ShoeFilter {

  Long size;
  Color color;

}
