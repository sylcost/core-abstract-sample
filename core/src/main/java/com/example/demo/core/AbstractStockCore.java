package com.example.demo.core;

import java.util.Optional;
import javax.annotation.PostConstruct;

import lombok.val;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.facade.StockFacade;

public abstract class AbstractStockCore
	implements StockCore {

  @Autowired
  private StockFacade stockFacade;

  @PostConstruct
  void init(){

    val version = Optional.ofNullable(this.getClass().getAnnotation(Implementation.class))
                          .map(Implementation::version)
                          .orElseThrow(() -> new FatalBeanException("AbstractShopCore implementation should be annotated with @Implementation"));

    stockFacade.register(version, this);
  }

}
