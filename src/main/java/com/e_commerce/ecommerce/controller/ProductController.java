package com.e_commerce.ecommerce.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }
    
    @PostMapping("/")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto product) throws IdExistException{
        
        ProductDto res = productService.addProduct(product);

        logger.info("Product added to DB.");
        URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/api/product")
        .buildAndExpand(product.getProductName())
        .toUri();

        return ResponseEntity.created(location).body(res);
    }

    @GetMapping("/{productid}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productName) throws DataNotFoundException{

        logger.info("Getting product details for {}.....",productName);
        ProductDto res = productService.getProductById(productName);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        logger.info("Getting product list....");
        List<ProductDto> res = productService.getAllProducts();

        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/update/{productName}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productName, @RequestBody ProductDto productreq) throws DataNotFoundException{
        logger.info("Updating product......");
        ProductDto res = productService.updateProduct(productName, productreq);

        return ResponseEntity.ok().body(res);
    }

    @DeleteMapping("/delete/{productName}")
    public ResponseEntity<String> deleteProductById(@PathVariable String productName) throws DataNotFoundException{
        logger.info("Deleting product.....");
        productService.deleteProductById(productName);

        return ResponseEntity.ok().body("Product deleted for " + productName);
    }
}
