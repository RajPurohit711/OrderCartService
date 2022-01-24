package com.example.OrderCartService.service;

import com.example.OrderCartService.entity.Cart;

public interface CartService {

    void save(Cart cart);
    Cart get(String id);
    void delete(String id);
    Cart getCartByUserId(Long id);

}
