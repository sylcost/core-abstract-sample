package com.example.demo.models;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import com.example.demo.dto.enums.Color;

@Getter
@Setter
@Table(name = "shoe")
@Entity
public class ShoeEntity {

	@Id
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private Color color;

	private BigInteger size;

	@Lazy
	@OneToMany(mappedBy = "shoe")
	private List<StockEntity> stocks;

}