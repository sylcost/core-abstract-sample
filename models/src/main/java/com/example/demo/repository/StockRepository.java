package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.example.demo.models.ShoeEntity;
import com.example.demo.models.ShopEntity;
import com.example.demo.models.StockEntity;

@Service
public interface StockRepository
	extends JpaRepository<StockEntity, Long>
{
	StockEntity findFirstByShoeAndShop(ShoeEntity shoe, ShopEntity shop);
}
