package com.example.OrderCartService.service;

import com.example.OrderCartService.entity.Cart;

public interface CartService {

    void save(Cart cart);
    Cart get(Long id);
    void delete(Long id);
    Cart getCartByUserId(Long id);

}
