package com.example.demo.dto.out;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@Value
@Builder
@JsonDeserialize(builder = Shop.ShopBuilder.class)
public class Shop
{
	String state;
	String name;
	List<ShoeStock> shoes;

	@JsonPOJOBuilder(withPrefix = "")
	public static class ShopBuilder {

	}
}
