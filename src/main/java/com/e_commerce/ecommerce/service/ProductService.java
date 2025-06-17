package com.e_commerce.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.e_commerce.ecommerce.dto.ProductDto;
import com.e_commerce.ecommerce.entity.Product;
import com.e_commerce.ecommerce.exceptions.DataNotFoundException;
import com.e_commerce.ecommerce.exceptions.IdExistException;
import com.e_commerce.ecommerce.repo.ProductRepo;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo){
        this.productRepo = productRepo;
    }
    
    public ProductDto addProduct(ProductDto productReq) throws IdExistException{

        Product product = new Product();

        Product validProduct = productRepo.findByProductName(productReq.getProductName());

        if(validProduct == null){
            product.setProductName(productReq.getProductName());
            product.setDescription(productReq.getDescription());
            product.setCategory(productReq.getCategory());
            product.setPrice(productReq.getPrice());

            productRepo.save(product);
        }
        else{
            throw new IdExistException(productReq.getProductName() + " already exist.");
        }

        ProductDto res = new ProductDto();
        BeanUtils.copyProperties(product, res);

        return res;
        
    }

    public ProductDto getProductById(String productName) throws DataNotFoundException{

        Product product = productRepo.findByProductName(productName);
        ProductDto res = new ProductDto();
        if(product == null){
            throw new DataNotFoundException("Data not found for " + productName);
        }

        BeanUtils.copyProperties(product, res);

        return res;
    }

    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepo.findAll();
        List<ProductDto> res = new ArrayList<>();

        for(Product p : products){
            ProductDto product = new ProductDto();
            BeanUtils.copyProperties(p, product);
            res.add(product);
        }
        return res;
    }

    public ProductDto updateProduct(String productName, ProductDto productreq) throws DataNotFoundException {
      
        Product product = productRepo.findByProductName(productName);
        ProductDto updatedProduct = new ProductDto();

        if(product != null){
            if(productreq.getDescription()!=null){
                product.setDescription(productreq.getDescription());
            }
              
            if(productreq.getCategory()!=null){
                product.setCategory(productreq.getCategory());
            }
               
            if(productreq.getPrice()>0.0){
                product.setPrice(productreq.getPrice());
            }

            productRepo.save(product);
            BeanUtils.copyProperties(product, updatedProduct);
        }
        else{
            throw new DataNotFoundException("Data not found for " + productName);
        }

        return updatedProduct;
    }

    public void deleteProductById(String productName) throws DataNotFoundException{
        Product product = productRepo.findByProductName(productName);
        if(product != null){
            productRepo.deleteById(product.getProductid());
        }
        else{
            throw new DataNotFoundException("Data not found for " + productName);
        }

    }
}
