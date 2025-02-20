package com.example.curso.webapp.curso_sb.controllers;

import com.example.curso.webapp.curso_sb.models.Product;
import com.example.curso.webapp.curso_sb.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

  private final Product testProduct = new Product("Test Product", 10.0);
  private final Product newProduct = new Product("New Product", 20.0);
  private final Product updatedProduct = new Product("Updated Product", 30.0);

  @Test
  public void testGetAllProducts_empty() throws Exception {
    Mockito.when(productService.getAllProducts()).thenReturn(List.of());
    mockMvc
      .perform(MockMvcRequestBuilders.get("/api/products"))
      .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void testGetAllProducts_nonEmpty() throws Exception {
    Mockito
      .when(productService.getAllProducts())
      .thenReturn(List.of(testProduct));
    mockMvc
      .perform(MockMvcRequestBuilders.get("/api/products"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(
        MockMvcResultMatchers.jsonPath("$[0].name").value(testProduct.getName())
      )
      .andExpect(
        MockMvcResultMatchers
          .jsonPath("$[0].price")
          .value(testProduct.getPrice())
      );
  }

  @Test
  public void testGetProductById_found() throws Exception {
    Mockito.when(productService.getProductById(1L)).thenReturn(testProduct);
    mockMvc
      .perform(MockMvcRequestBuilders.get("/api/products/1"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.name").value(testProduct.getName())
      )
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.price").value(testProduct.getPrice())
      );
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
    Mockito
      .when(productService.createProduct(Mockito.any(Product.class)))
      .thenReturn(newProduct);

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/api/products")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(newProduct))
      )
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.name").value(newProduct.getName())
      )
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.price").value(newProduct.getPrice())
      );
  }

  @Test
  public void testUpdateProduct_found() throws Exception {
    Mockito
      .when(
        productService.updateProduct(
          Mockito.anyLong(),
          Mockito.any(Product.class)
        )
      )
      .thenReturn(updatedProduct);

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .put("/api/products/1")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(updatedProduct))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(
        MockMvcResultMatchers.jsonPath("$.name").value(updatedProduct.getName())
      )
      .andExpect(
        MockMvcResultMatchers
          .jsonPath("$.price")
          .value(updatedProduct.getPrice())
      );
  }

  @Test
  public void testUpdateProduct_notFound() throws Exception {
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
          .content(objectMapper.writeValueAsString(updatedProduct))
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
