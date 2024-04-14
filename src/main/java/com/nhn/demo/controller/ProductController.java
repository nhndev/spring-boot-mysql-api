package com.nhn.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nhn.demo.annotation.Logging;
import com.nhn.demo.dto.request.product.ProductCreationRequest;
import com.nhn.demo.dto.request.product.ProductUpdateRequest;
import com.nhn.demo.service.ProductService;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    @Logging
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    @Logging
    public ResponseEntity<?> findById(@PathVariable final Integer id) {
        return ResponseEntity.ok(this.service.findById(id));
    }

    @PostMapping
    @Logging
    public ResponseEntity<?> create(@RequestBody final ProductCreationRequest request) {
        return ResponseEntity.ok(this.service.createProduct(request));
    }

    @PutMapping("/{id}")
    @Logging
    public ResponseEntity<?> update(@PathVariable final Integer id, @RequestBody final ProductUpdateRequest request) {
        return ResponseEntity.ok(this.service.updateProduct(id,request));
    }

    @DeleteMapping("/{id}")
    @Logging
    public ResponseEntity<?> delete(@PathVariable final Integer id) {
        this.service.deleteProduct(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
