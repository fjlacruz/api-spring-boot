package com.example.curso.webapp.curso_sb.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private double price;

  public Product() {}

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }
}
