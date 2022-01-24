package com.example.OrderCartService.controller;

import com.example.OrderCartService.dto.CartDto;
import com.example.OrderCartService.entity.Cart;
import com.example.OrderCartService.service.CartService;
import net.minidev.json.writer.BeansMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping(value = "/{id}")
    CartDto getCartByUserId(@PathVariable Long id){
        Cart cart = cartService.getCartByUserId(id);
        CartDto cartDto = new CartDto();
        BeanUtils.copyProperties(cart,cartDto);
        return cartDto;
    }


    @DeleteMapping(value = "/{id}")
    void deleteCartByid(@PathVariable String  id){
        cartService.delete(id);
    }

    @RequestMapping(value = "/addToCart",method = {RequestMethod.POST,RequestMethod.PUT})
    void addToCart(@RequestBody CartDto cartDto){

        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDto,cart);
        cartService.save(cart);
    }

}
