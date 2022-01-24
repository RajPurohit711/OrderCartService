package com.example.OrderCartService.entity;

import com.example.OrderCartService.dto.CartIteamDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Cart {
    @Id
    private String id;
    private Long userId;
    private List<CartIteamDto> cartItems;
    private Long total ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartIteamDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartIteamDto> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
