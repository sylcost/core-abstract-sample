package com.example.demo.models;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import com.example.demo.dto.in.ShoeFilter;

@Getter
@Setter
@Table(name = "shoe")
@Entity
public class ShoeEntity {

	@Id
	@Column(name = "shoe_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private ShoeFilter.Color color;

	private BigInteger size;

}