package com.example.OrderCartService.controller;

import com.example.OrderCartService.dto.CartDto;
import com.example.OrderCartService.dto.CartItemDto;
import com.example.OrderCartService.dto.ProductDto;
import com.example.OrderCartService.entity.Cart;
import com.example.OrderCartService.repository.CartRepository;
import com.example.OrderCartService.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;

    @GetMapping()
    List<Cart> getall(){
        return cartRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    CartDto getCartByUserId(@PathVariable Long id){
        Cart cart = cartService.get(id);


//        List<CartItemDto> cartItemDtos = cart.getCartItems();


//        for(CartItemDto cartItemDto : cartItemDtos ) {
//          String url = "http://localhost:8082/product/getProduct/"+cartItemDto.getProductId()+"/"+cartItemDto.getMerchantId();
//          ProductDto productDto = new RestTemplate().getForObject(url, ProductDto.class);
//            cartItemDto.setPrice(productDto.getPrice());
//            if(cartItemDto.getQuantity() > productDto.getStock()){
//                cartItemDto.setQuantity(0L);
//            }
//
//        }

//        cart.setCartItems(cartItemDtos);

        CartDto cartDto = new CartDto();
        BeanUtils.copyProperties(cart,cartDto);
        return cartDto;
    }


    @DeleteMapping(value = "/{id}")
    void deleteCartByid(@PathVariable Long  id){
        cartService.delete(id);
    }

    @PostMapping(value = "/createCart")
    void createCart(@RequestBody CartDto cartDto){
        Cart cart = new Cart();
        cartDto.setTotal(0L);
        cartDto.setCartItems(null);
        BeanUtils.copyProperties(cartDto,cart);

        cartService.save(cart);
    }



    @RequestMapping(value = "/addToCart",method = {RequestMethod.POST,RequestMethod.PUT})
    String addToCart(@RequestBody CartItemDto cartItemDto){


        Cart cart = cartService.get(cartItemDto.getUserId());
        List<CartItemDto> cartItemDtos = cart.getCartItems();
        if(cartItemDtos==null){
            System.out.println(cartItemDto.getUserId()+"if \n\n ");
            cart.setCartItems(new ArrayList<>());
            List<CartItemDto> cartItemDtos1 = new ArrayList<>();
            cartItemDtos1.add(cartItemDto);
            cart.setCartItems(cartItemDtos1);
        }
        else {

            System.out.println("else \n\n ");


            boolean flag=false;
            for(CartItemDto cartItemDto1 : cartItemDtos){

                if(cartItemDto1.getProductId().equals(cartItemDto.getProductId())){

                    if(cartItemDto1.getMerchantId().equals(cartItemDto.getMerchantId())){

                        cartItemDto1.setQuantity(cartItemDto.getQuantity()+cartItemDto1.getQuantity());
                        flag=true;
                        break;
                    }
                }
            }
            if(!flag) {
                cart.addCartItem(cartItemDto);
            }


        }
        
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
