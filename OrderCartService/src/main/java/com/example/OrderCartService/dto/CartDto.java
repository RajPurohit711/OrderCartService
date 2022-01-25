package com.example.OrderCartService.dto;


import java.util.List;

public class CartDto {

    private Long userId;
    private List<CartItemDto> cartItems;
    private Long total ;



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

    public void calculateTotal(){
        CartItemDto cartItemDto = new CartItemDto();
        total= 0L;
        for(CartItemDto cartItemDto1 : cartItems){
            total += cartItemDto1.getPrice();
        }
    }

    public void addCartItem(CartItemDto cartItemDto){
        cartItems.add(cartItemDto);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void deleteCartItem(CartItemDto cartItemDto){
        cartItems.remove(cartItemDto);
    }
}
