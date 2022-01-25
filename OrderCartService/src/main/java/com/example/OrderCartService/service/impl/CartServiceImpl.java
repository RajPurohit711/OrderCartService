package com.example.OrderCartService.service.impl;

import com.example.OrderCartService.entity.Cart;
import com.example.OrderCartService.repository.CartRepository;
import com.example.OrderCartService.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart get(Long id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getCartByUserId(Long id) {
        return cartRepository.findById(id).get();
    }

}
