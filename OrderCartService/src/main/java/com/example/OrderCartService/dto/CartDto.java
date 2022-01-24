package com.example.OrderCartService.dto;

import com.example.OrderCartService.entity.Cart;

import java.util.List;

public class CartDto {

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

    public void calculateTotal(){
        CartIteamDto cartIteamDto = new CartIteamDto();
        total= 0L;
        for(CartIteamDto cartIteamDto1 : cartItems){
            total += cartIteamDto1.getPrice();
        }
    }

    public void addCartItem(CartIteamDto cartIteamDto){
        cartItems.add(cartIteamDto);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
