package com.e_commerce.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.e_commerce.ecommerce.dto.CartResponseDto;
import com.e_commerce.ecommerce.entity.Cart;
import com.e_commerce.ecommerce.entity.CartItem;
import com.e_commerce.ecommerce.entity.Product;
import com.e_commerce.ecommerce.entity.User;
import com.e_commerce.ecommerce.repo.CartItemRepo;
import com.e_commerce.ecommerce.repo.CartRepo;
import com.e_commerce.ecommerce.repo.ProductRepo;
import com.e_commerce.ecommerce.repo.UserRepo;

@Service
public class CartService {

    private CartRepo cartrep;
    private CartItemRepo itemrepo;
    private UserRepo userrep;
    private ProductRepo productrep;

    public CartService(CartRepo cartrep, UserRepo userrep, ProductRepo productrep, CartItemRepo itemrepo){
        this.cartrep = cartrep;
        this.productrep = productrep;
        this.userrep = userrep;
        this.itemrepo = itemrepo;
    }

    /** add product to cart and assign a new cart for a user */
    public String addToCart(String userName, String productName){
        User existedUser = userrep.findByUsername(userName);
        Product existedProduct = productrep.findByProductName(productName);

        if(existedUser == null){
            return "User Not Found";
        }

        if(existedProduct == null){
            return "Product not found";
        }

           Cart cart = cartrep.findByUserName(userName);
           if(cart==null){
                cart = new Cart();
                cart.setUserName(userName);
                cart.setItems(new ArrayList<>());
                cart.setTotalPrice(0.0);
           }
                List<CartItem> items = cart.getItems();
                CartItem foundItem = null;
                for(CartItem item : items){
                    if(item.getProductName().equals(productName)){
                        foundItem = item;
                        break;
                    }
                }

                if(foundItem != null){
                    foundItem.setQuantity(foundItem.getQuantity()+1);
                    foundItem.setPrice(foundItem.getPrice() + existedProduct.getPrice());
                    itemrepo.save(foundItem);
                }
                else{
                    CartItem newItem = new CartItem();
                    newItem.setProductName(productName);
                    newItem.setPrice(existedProduct.getPrice());
                    newItem.setQuantity(1);
                    itemrepo.save(newItem);
                    items.add(newItem);
                }

                cart.setTotalPrice(cart.getTotalPrice() + existedProduct.getPrice());
                cartrep.save(cart);

        return "Product added successfully";
    }

    public CartResponseDto getCartItems(String username){

        User existedUser = userrep.findByUsername(username);
        CartResponseDto res = new CartResponseDto();
        if(existedUser == null){ 
            return null;
        }

        Cart cart = cartrep.findByUserName(username);
        System.out.println(cart);

        List<CartItem> items = cart.getItems();
        res.setUsername(username);
        res.setItems(items);
        res.setTotalPrice(cart.getTotalPrice());
        return res;
    }
}
