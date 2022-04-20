package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping(path = "new")
    public String newProduct() {
        return "addProduct";
    }
}
