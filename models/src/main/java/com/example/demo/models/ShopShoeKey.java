package com.example.demo.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopShoeKey implements Serializable
{
	private Long shopId;
	private Long shoeId;
}
