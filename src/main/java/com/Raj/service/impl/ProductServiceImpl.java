package com.Raj.service.impl;

import com.Raj.exceptions.ProductException;
import com.Raj.model.Category;
import com.Raj.model.Product;
import com.Raj.model.Seller;
import com.Raj.request.CreateProductRequest;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl  implements com.Raj.service.ProductService{

    private final com.Raj.repository.ProductRepository productRepository;
    private final com.Raj.repository.CategoryRepository categoryRepository ;

    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) {
        Category category1=categoryRepository.findByCategoryId(req. getCategory();

        if(category1 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category1=categoryRepository.save(category);
        }

        Category category2 = categoryRepository.findByCategoryId(req.getCategory2());

        if(category2 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2=categoryRepository.save(category);
        }

        Category category3 = categoryRepository.findByCategoryId(req.getCategory3());
        if(category3 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3=categoryRepository.save(category);
        }

        int discountPercentage=calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());


        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setDescription(req.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setSellingPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSizes(req.getSizes());
        product.setDiscountPercent(discountPercentage);

        return productRepository.save(product);
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if (mrpPrice <= 0){
            throw new IllegalArgumentException("MRP price must be greater than zero");
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount/mrpPrice) * 100;
        return (int) discountPercentage;
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = findProductById(productId);
        productRepository.delete(product);

    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        findProductById(productId);
        product.setId(productId);

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(()->
                new ProductException("product not found"+productId));
        }

    @Override
    public List<Product> searchProducts(String query) {
        return List.of();
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
        Specification<Product> spec = (root,query,criteriaBuilder) ->
        {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null) {
                Join<Product, Category> categoryJoin = root.join("category");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("categoryId"), category));
            }

            if (colors != null && !colors.isEmpty()) {
                System.out.println("color: " + colors);
                predicates.add(criteriaBuilder.equal(root.get("color"),
                        colors));

            }

            if (sizes != null && ! sizes.isEmpty()) {
            predicates . add (criteriaBuilder. equal(root. get("size") ,
            sizes));
        }
            if (minPrice != null) {
                predicates.add (criteriaBuilder.greaterThanOrEqualTo(root. get("selling price") ,
                        minPrice));
            }
            if (maxPrice != null) {
                predicates.add (criteriaBuilder.lessThanOrEqualTo(root. get("selling price") ,
                        maxPrice));
            }

            if (minDiscount != null) {
                predicates.add (criteriaBuilder.greaterThanOrEqualTo(root. get("Discount percentage") ,
                        minDiscount));
            }

            if (stock != null) {
                predicates.add (criteriaBuilder.equal(root. get("stock") ,
                        stock));
            }





        return null;
    }

    @Override
    public List<Product> getProductsBySellerId(Long sellerId) {
        return List.of();
    }
}
