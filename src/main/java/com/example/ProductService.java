package com.example;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

  private final ProductRepository repository;

  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public Page<Product> listAll(int pageNum, String sortField, String sortDir, String keyword) {
    int pageSize = 25;
    if (keyword != null ) {
      Pageable pageable =
          PageRequest.of(
              pageNum - 1,
              pageSize,
              sortDir.equals("asc")
                  ? Sort.by(sortField).ascending()
                  : Sort.by(sortField).descending());
      return repository.searchSimplify(keyword, pageable);
    }

    Pageable pageable =
        PageRequest.of(
            pageNum - 1,
            pageSize,
            sortDir.equals("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending());

    return repository.findAll(pageable);
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
