package com.example.demo.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="shop")
public class ShopEntity
{
	@Id
	private Long id;
	private String name;

	@OneToMany(mappedBy = "shop")
	private List<StockEntity> stocks;
}
