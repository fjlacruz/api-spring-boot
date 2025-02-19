package com.example.curso.webapp.curso_sb.services;

import com.example.curso.webapp.curso_sb.models.Product;
import com.example.curso.webapp.curso_sb.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Product getProductById(Long id) {
    return productRepository.findById(id).orElse(null);
  }

  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  public Product updateProduct(Long id, Product product) {
    Product existingProduct = productRepository.findById(id).orElse(null);
    if (existingProduct != null) {
      existingProduct.setName(product.getName());
      existingProduct.setPrice(product.getPrice());
      return productRepository.save(existingProduct);
    }
    return null;
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
}
