package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.example.demo.models.ShopEntity;

@Service
public interface ShopRepository
	extends JpaRepository<ShopEntity, Long>
{
	ShopEntity findFirstByName(String name);
}
