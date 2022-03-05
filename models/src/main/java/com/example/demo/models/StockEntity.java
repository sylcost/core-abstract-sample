package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="stock")
@IdClass(ShopShoeKey.class)
public class StockEntity
{
	@Id
	@Column(name="shop_id")
	private Long shopId;

	@Id
	@Column(name="shoe_id")
	private Long shoeId;

	private Long quantity;
}
