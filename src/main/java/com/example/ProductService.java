package com.example;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

  private final ProductRepository repository;

  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public List<Product> listAll() {
    return repository.findAll();
  }

  public void save(Product product) {
    repository.save(product);
  }

  public Product get(Long id) {
    return repository.findById(id).get();
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}
