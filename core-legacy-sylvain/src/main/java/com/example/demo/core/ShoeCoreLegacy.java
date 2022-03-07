package com.example.demo.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.dto.in.ShoeFilter;
import com.example.demo.dto.out.Shoe;
import com.example.demo.dto.out.Shoes;
import com.example.demo.models.ShoeEntity;
import com.example.demo.repository.ShoeRepository;

@Implementation(version = 1)
public class ShoeCoreLegacy extends AbstractShoeCore {

  private final ShoeRepository shoeRepository;

  @Autowired
  public ShoeCoreLegacy(final ShoeRepository shoeRepository) {
    this.shoeRepository = shoeRepository;
  }

  @Override
  public Shoes search(final ShoeFilter filter) {
    List<ShoeEntity> l = shoeRepository.getAllByColorAndSize(filter.getColor(), filter.getSize());
    List<Shoe> result = l.stream().map(shoeEntity -> Shoe.builder()
                                                         .color(shoeEntity.getColor())
                                                         .size(shoeEntity.getSize())
                                                         .name(shoeEntity.getName()).build()).toList();

    return Shoes.builder()
                .shoes(result)
                .build();
  }
}
