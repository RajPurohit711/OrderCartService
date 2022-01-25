package com.example.OrderCartService.controller;

import com.example.OrderCartService.dto.CartDto;
import com.example.OrderCartService.dto.CartItemDto;
import com.example.OrderCartService.dto.OrderItemDto;
import com.example.OrderCartService.dto.ProductDto;
import com.example.OrderCartService.entity.Cart;
import com.example.OrderCartService.service.CartService;
import net.minidev.json.writer.BeansMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping(value = "/{id}")
    CartDto getCartByUserId(@PathVariable Long id){
        Cart cart = cartService.get(id);


        List<CartItemDto> cartItemDtos = cart.getCartItems();


        for(CartItemDto cartItemDto : cartItemDtos ) {
            String url = "http://localhost:8082/product/getProduct/"+cartItemDto.getProductId()+"/"+cartItemDto.getMerchantId();
            ProductDto productDto = new RestTemplate().getForObject(url, ProductDto.class);
            cartItemDto.setPrice(productDto.getPrice());
            if(cartItemDto.getQuantity() > productDto.getStock()){
                cartItemDto.setQuantity(0L);
            }

        }

        cart.setCartItems(cartItemDtos);

        CartDto cartDto = new CartDto();
        BeanUtils.copyProperties(cart,cartDto);
        return cartDto;
    }


    @DeleteMapping(value = "/{id}")
    void deleteCartByid(@PathVariable Long  id){
        cartService.delete(id);
    }

    @RequestMapping(value = "/addToCart",method = {RequestMethod.POST,RequestMethod.PUT})
    String addToCart(@RequestBody CartItemDto cartItemDto){

        Cart cart = cartService.get(cartItemDto.getUserId());
        if(cart==null) {
            cart = new Cart();
            cart.setUserId(cartItemDto.getUserId());
            cart.setCartItems(new ArrayList<>());
            cart.addCartItem(cartItemDto);
        }
        cart.addCartItem(cartItemDto);
        
        String url = "http://localhost:8082/product/getProduct/"+cartItemDto.getProductId()+"/"+cartItemDto.getMerchantId();
        ProductDto productDto = new RestTemplate().getForObject(url, ProductDto.class);

        if(productDto.getStock() < cartItemDto.getQuantity())
            return "Out of Stock";
        else {
            cartService.save(cart);
            return "Succeed";
        }
        
    }

}
