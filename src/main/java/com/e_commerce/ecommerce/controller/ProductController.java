package com.e_commerce.ecommerce.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.e_commerce.ecommerce.dto.ProductDto;
import com.e_commerce.ecommerce.exceptions.DataNotFoundException;
import com.e_commerce.ecommerce.exceptions.IdExistException;
import com.e_commerce.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }
    
    @PostMapping("/")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto product) throws IdExistException{
        
        ProductDto res = productService.addProduct(product);

        URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/api/product")
        .buildAndExpand(product.getProductName())
        .toUri();

        return ResponseEntity.created(location).body(res);
    }

    @GetMapping("/{productid}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productid) throws DataNotFoundException{

        ProductDto res = productService.getProductById(productid);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> res = productService.getAllProducts();

        return ResponseEntity.ok().body(res);
    }
}
