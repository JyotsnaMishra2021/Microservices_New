package com.microservices.productservice.repository;

import com.microservices.productservice.modal.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
