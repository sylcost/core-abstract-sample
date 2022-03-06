package com.example.demo.dto.out;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Value;
import com.example.demo.dto.enums.Color;
import com.example.demo.dto.out.Shoe.ShoeBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@Value
@Builder
@JsonDeserialize(builder = ShoeBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shoe {

  Long       id;
  String     name;
  BigInteger size;
  Color      color;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ShoeBuilder {

  }


}
