package com.example.demo.dto.out;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@Value
@Builder
@JsonDeserialize(builder = Stock.StockBuilder.class)
public class Stock
{
	String state;
	List<ShoeStock> shoes;

	@JsonPOJOBuilder(withPrefix = "")
	public static class StockBuilder {

	}
}
