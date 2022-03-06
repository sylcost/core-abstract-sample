package com.example.demo.dto.out;

import lombok.Builder;
import lombok.Value;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@Value
@Builder
@JsonDeserialize(builder = Shop.ShopBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shop
{
	Long id;
	String name;

	@JsonPOJOBuilder(withPrefix = "")
	public static class ShopBuilder {

	}
}
