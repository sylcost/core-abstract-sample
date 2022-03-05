package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="stock")
@IdClass(StockPk.class)
public class StockEntity
{

	@Id
	@ManyToOne
	@JoinColumn(name = "shop_id", referencedColumnName = "id")
	private ShopEntity shop;

	@Id
	@ManyToOne
	@JoinColumn(name = "shoe_id", referencedColumnName = "id")
	private ShoeEntity shoe;

	@Column
	private Long quantity;
}
