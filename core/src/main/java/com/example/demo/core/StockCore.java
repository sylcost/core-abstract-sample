package com.example.demo.core;

import com.example.demo.dto.exceptions.TooMuchShoesException;
import com.example.demo.dto.in.StocksUpdate;
import com.example.demo.dto.out.Stock;

public interface StockCore
{
  Stock get(Long shopId);
  void patchFull(Long shopId, StocksUpdate stocksUpdate)
	  throws TooMuchShoesException;
	void patchUnitary(Long shopId, StocksUpdate.StockUpdate stockUpdate)
		throws TooMuchShoesException;
}
