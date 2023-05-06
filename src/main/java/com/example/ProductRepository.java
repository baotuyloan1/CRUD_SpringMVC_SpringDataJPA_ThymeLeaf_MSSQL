package com.example;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query(
      "SELECT p from Product p where p.name like %?1% OR p.brand LIKE %?1% "
          + " OR p.madeIn LIKE %?1%"
          + " OR CONCAT(p.price, '')LIKE %?1%")
   Page<Product> search(String keyword, Pageable pageable);

  //  cách 2
  @Query( "SELECT p FROM Product p WHERE CONCAT(p.name,' ',p.brand,' ',p.madeIn, ' ',CAST(p.price AS string ) ) LIKE %?1%")
   Page<Product> searchSimplify(String keyword, Pageable pageable);
}
