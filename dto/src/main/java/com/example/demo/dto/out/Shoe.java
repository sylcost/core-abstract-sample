package com.example.demo.dto.out;

import lombok.Builder;
import lombok.Value;
import com.example.demo.dto.in.ShoeFilter.Color;
import com.example.demo.dto.out.Shoe.ShoeBuilder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@Value
@Builder
@JsonDeserialize(builder = ShoeBuilder.class)
public class Shoe {

  String     name;
  Long       size;
  Color      color;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ShoeBuilder {

  }


}
