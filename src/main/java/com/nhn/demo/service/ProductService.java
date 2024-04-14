package com.nhn.demo.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhn.demo.dto.request.product.ProductCreationRequest;
import com.nhn.demo.dto.request.product.ProductUpdateRequest;
import com.nhn.demo.dto.response.category.CategoryDetailResponse;
import com.nhn.demo.dto.response.product.ProductDetailResponse;
import com.nhn.demo.entity.Category;
import com.nhn.demo.entity.Product;
import com.nhn.demo.exception.NotFoundException;
import com.nhn.demo.repository.CategoryRepository;
import com.nhn.demo.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductDetailResponse> findAll() {
        final List<Product>               products                  = this.repository.findAll();
        final List<ProductDetailResponse> productDetailResponseList = products.stream()
                                                                              .map(this::buildProductDetailResponse)
                                                                              .toList();
        return productDetailResponseList;
    }

    public ProductDetailResponse findById(final Integer id) {
        final Product product = this.repository.findById(id)
                                               .orElseThrow(() -> new NotFoundException("Product not found"));
        return this.buildProductDetailResponse(product);
    }

    public ProductDetailResponse createProduct(final ProductCreationRequest request) {
        final Integer  categoryId = request.getCategoryId();
        final Category category   = this.categoryRepository.findById(categoryId)
                                                           .orElseThrow(() -> new NotFoundException("Category not found"));
        final Product  product    = Product.builder().build();
        BeanUtils.copyProperties(request, product);
        product.setCategory(category);
        final Product savedProduct = this.repository.save(product);
        return this.buildProductDetailResponse(savedProduct);
    }

    public ProductDetailResponse updateProduct(final Integer id,
                                 final ProductUpdateRequest request) {
        final Product product = this.repository.findById(id)
                                               .orElseThrow(() -> new NotFoundException("Product not found"));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        final Product savedProduct = this.repository.save(product);
        return this.buildProductDetailResponse(savedProduct);
    }

    public void deleteProduct(final Integer id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else {
            throw new NotFoundException("Product not found");
        }
    }

    private ProductDetailResponse buildProductDetailResponse(final Product product) {
        final ProductDetailResponse response = ProductDetailResponse.builder()
                                                                    .build();
        BeanUtils.copyProperties(product, response);

        final Category               category         = product.getCategory();
        final CategoryDetailResponse categoryResponse = CategoryDetailResponse.builder()
                                                                              .build();
        BeanUtils.copyProperties(category, categoryResponse);
        response.setCategory(categoryResponse);
        return response;
    }
}
