package com.example.curso.webapp.curso_sb.controllers;

import com.example.curso.webapp.curso_sb.models.Product;
import com.example.curso.webapp.curso_sb.services.ProductService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    if (products.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if empty
    } else {
      return new ResponseEntity<>(products, HttpStatus.OK); // 200 OK with the list
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    if (product != null) {
      return new ResponseEntity<>(product, HttpStatus.OK);
    } else {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("message", "Product not found");
      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productService.createProduct(product);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateProduct(
    @PathVariable Long id,
    @RequestBody Product product
  ) {
    Product updatedProduct = productService.updateProduct(id, product);
    if (updatedProduct != null) {
      return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    } else {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("message", "Product not found");
      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
