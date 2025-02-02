package com.example.demo.controller;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.exceptions.TooMuchShoesException;
import com.example.demo.dto.in.StocksUpdate;
import com.example.demo.dto.out.Stock;
import com.example.demo.facade.StockFacade;

@RestController
@RequestMapping(path = "/stock")
@RequiredArgsConstructor
public class StockController
{

  private final StockFacade stockFacade;

  @GetMapping(path = "/shop/{shopId}")
  public Stock get(@RequestHeader final Integer version,
                                   @PathVariable(name="shopId") final Long shopId) {
    return stockFacade.get(version).get(shopId);
  }

  @PatchMapping(path= "/shop/{shopId}/full")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @Transactional
  public void patchFull(@RequestHeader final Integer version,
                    @PathVariable(name="shopId") final Long shopId,
                    @RequestBody final StocksUpdate stocksUpdate)
      throws TooMuchShoesException
  {
    stockFacade.get(version).patchFull(shopId, stocksUpdate);
  }

  @PatchMapping(path= "/shop/{shopId}/unitary")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @Transactional
  public void patchUnitary(@RequestHeader final Integer version,
                    @PathVariable(name="shopId") final Long shopId,
                    @RequestBody final StocksUpdate.StockUpdate stockUpdate)
      throws TooMuchShoesException
  {
    stockFacade.get(version).patchUnitary(shopId, stockUpdate);
  }

}
