package com.example.OrderCartService.entity;

import com.example.OrderCartService.dto.CartItemDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Cart {
    @Id
    private Long userId;
    private List<CartItemDto> cartItems;
    private Long total ;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void addCartItem(CartItemDto cartItemDto){
        this.addCartItem(cartItemDto);
    }
}
