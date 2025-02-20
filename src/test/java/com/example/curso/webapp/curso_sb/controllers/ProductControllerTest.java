package com.example.curso.webapp.curso_sb.controllers;

import com.example.curso.webapp.curso_sb.models.Product;
import com.example.curso.webapp.curso_sb.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testGetAllProducts_empty() throws Exception {
    Mockito
      .when(productService.getAllProducts())
      .thenReturn(Collections.emptyList());

    mockMvc
      .perform(MockMvcRequestBuilders.get("/api/products"))
      .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void testGetAllProducts_nonEmpty() throws Exception {
    Product product = new Product("Test Product", 10.0);
    Mockito.when(productService.getAllProducts()).thenReturn(List.of(product));

    mockMvc
      .perform(MockMvcRequestBuilders.get("/api/products"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(
        MockMvcResultMatchers.jsonPath("$[0].name").value("Test Product")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(10.0));
  }

  @Test
  public void testGetProductById_found() throws Exception {
    Product product = new Product("Test Product", 10.0);
    Mockito.when(productService.getProductById(1L)).thenReturn(product);

    mockMvc
      .perform(MockMvcRequestBuilders.get("/api/products/1"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.0));
  }

  @Test
  public void testGetProductById_notFound() throws Exception {
    Mockito.when(productService.getProductById(1L)).thenReturn(null);

    mockMvc
      .perform(MockMvcRequestBuilders.get("/api/products/1"))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testCreateProduct() throws Exception {
    Product product = new Product("New Product", 20.0);
    Mockito
      .when(productService.createProduct(Mockito.any(Product.class)))
      .thenReturn(product);

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/api/products")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(product))
      )
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Product"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(20.0));
  }

  @Test
  public void testUpdateProduct_found() throws Exception {
    Product product = new Product("Updated Product", 30.0);
    Mockito
      .when(
        productService.updateProduct(
          Mockito.anyLong(),
          Mockito.any(Product.class)
        )
      )
      .thenReturn(product);

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .put("/api/products/1")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(product))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.name").value("Updated Product")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(30.0));
  }

  @Test
  public void testUpdateProduct_notFound() throws Exception {
    Product product = new Product("Updated Product", 30.0);
    Mockito
      .when(
        productService.updateProduct(
          Mockito.anyLong(),
          Mockito.any(Product.class)
        )
      )
      .thenReturn(null);

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .put("/api/products/1")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(product))
      )
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testDeleteProduct() throws Exception {
    mockMvc
      .perform(MockMvcRequestBuilders.delete("/api/products/1"))
      .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
