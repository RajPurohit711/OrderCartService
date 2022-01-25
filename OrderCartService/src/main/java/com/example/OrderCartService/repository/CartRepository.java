package com.example.OrderCartService.repository;

import com.example.OrderCartService.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends MongoRepository<Cart,Long> {

}
