package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import com.example.demo.dto.enums.Color;
import com.example.demo.models.ShoeEntity;

@Service
public interface ShoeRepository extends JpaRepository<ShoeEntity, Long>
{
	@Query("SELECT s FROM ShoeEntity s WHERE (:size is null or s.size = :size) and (:color is null or s.color = :color)")
	List<ShoeEntity> getAllByColorAndSize(Color color, Long size);
}
