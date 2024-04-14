package com.nhn.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nhn.demo.service.ProductService;

@WebMvcTest(TestControllerTest.class)
public class TestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    public void test() throws Exception {
         this.mockMvc.perform(MockMvcRequestBuilders.get("/api/products"));
    }
}
