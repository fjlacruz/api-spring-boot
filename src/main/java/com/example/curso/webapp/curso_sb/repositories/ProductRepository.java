package com.example.curso.webapp.curso_sb.repositories;

import com.example.curso.webapp.curso_sb.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  // Métodos adicionales para consultas personalizadas (si es necesario)
}
