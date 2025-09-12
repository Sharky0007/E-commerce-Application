package com.e_commerce.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
    /**TODO: For now cart is able to add a single product. correct the logic
     to add multiple products with quantity and totalprice **/
     
    public String addToCart(String userName, String productName){
        User existedUser = userrep.findByUsername(userName);
        Product existedProduct = productrep.findByProductName(productName);

        if(existedUser!= null){
            Cart existingcart = cartrep.findByUserName(userName);
           if(existingcart==null){
                Cart cart = new Cart();
                cart.setUserName(userName);
                CartItem item = addItems(existedProduct);
                itemrepo.save(item); 
                List<CartItem> items = new ArrayList<>();
                items.add(item);
                cart.setItems(items);
                cart.setTotalPrice(existedProduct.getPrice()); 
                cartrep.save(cart);
           }
            existingcart.setTotalPrice(existingcart.getTotalPrice() + existedProduct.getPrice());
            cartrep.save(existingcart);

        }
        else{
            return "User Not Found";
        }
        return "Product added successfully";
    }

    public CartItem addItems(Product product){

        CartItem item = new CartItem();

        if(itemrepo.findByProductName(product.getProductName())!=null){
            item = itemrepo.findByProductName(product.getProductName());
            item.setPrice(item.getPrice() + product.getPrice());
            item.setQuantity(item.getQuantity()+1);
        }
        else{
            item.setProductName(product.getProductName());
            item.setPrice(product.getPrice());
            item.setQuantity(1);
        }

        return item;
    }
}
