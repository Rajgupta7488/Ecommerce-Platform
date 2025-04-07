package com.Raj.service;

import com.Raj.exceptions.ProductException;
import com.Raj.model.Product;
import com.Raj.model.Seller;
import com.Raj.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req , Seller seller);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId,Product product) throws ProductException;
    public Product findProductById(Long productId) throws ProductException;
    public List<Product> searchProducts(String query);
    public Page<Product> getAllProducts(
           String category,
           String brand,
           String colors,
           String sizes,
           Integer minPrice,
           Integer maxPrice,
           Integer minDiscount,
           String sort,
           String stock,
           Integer pageNumber
    );
    List<Product> getProductsBySellerId(Long sellerId);


    


}
