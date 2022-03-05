package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shop")
public class ShopEntity
{
	@Id
	@Column(name = "shop_id")
	private Long id;

	@Column(name = "shop_name")
	private String name;
}
