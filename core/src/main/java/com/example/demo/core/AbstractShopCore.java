package com.example.demo.core;

import java.util.Optional;
import javax.annotation.PostConstruct;

import lombok.val;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.facade.ShopFacade;

public abstract class AbstractShopCore
	implements ShopCore {

  @Autowired
  private ShopFacade shopFacade;

  @PostConstruct
  void init(){

    val version = Optional.ofNullable(this.getClass().getAnnotation(Implementation.class))
                          .map(Implementation::version)
                          .orElseThrow(() -> new FatalBeanException("AbstractShopCore implementation should be annotated with @Implementation"));

    shopFacade.register(version, this);

  }

}
