package com.example.demo.dto.out;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Value;
import com.example.demo.dto.enums.Color;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@Value
@Builder
@JsonDeserialize(builder = ShoeStock.ShoeStockBuilder.class)
public class ShoeStock
{
	Color color;
	BigInteger size;
	Long quantity;

	@JsonPOJOBuilder(withPrefix = "")
	public static class ShoeStockBuilder {

	}
}
