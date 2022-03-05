package com.example.demo.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockPk implements Serializable
{
	private Long shop;
	private Long shoe;
}
