package com.example.demo.dto.enums;

public enum StockState
{
	EMPTY,
	SOME,
	FULL;

	public static StockState fromQuantity(final Long quantity)
	{
		StockState result = FULL;
		if (quantity <= 0L) {
			result = EMPTY;
		} else if (quantity < 30L ) {
			result = SOME;
		}
		return result;
	}
}
