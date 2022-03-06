package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MultipleCoreImplemSampleApplicationTest
{
  public static void main(final String[] args) {
    SpringApplication.run(MultipleCoreImplemSampleApplicationTest.class, args);
  }

  @Test
  public void contextLoads()
  {
    // Permet juste de charger tous les contexts Spring et vérifier que l'application démarre.
  }
}
