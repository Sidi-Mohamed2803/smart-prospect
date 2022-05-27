package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.product.Product;
import com.smartprospect.smartprospect.product.ProductService;
import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

//    @GetMapping("edit/{ref}")
//    public String editProduct(@PathVariable(name = "ref")String ref, RedirectAttributes redirectAttributes) {
//        Product product = productService.
//
//        return "editProduct";
//    }

    @GetMapping
    public String productsPage(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("products", currentUser.getProducts());
        return "products";
    }

    @GetMapping("catalog")
    public String catalog(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("products", currentUser.getProducts());
        return "catalog";
    }

    @GetMapping(path = "new")
    public String newProduct(Product product) {
        return "addProduct";
    }

    @PostMapping(path = "add")
    public String addNewProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, @RequestParam(name = "pic", required = false) MultipartFile pic, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return  "addProduct";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        if (!pic.isEmpty() && pic != null) {
            try {
                product.setImage(Base64.getEncoder().encodeToString(pic.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        product.setUser(currentUser);
        //log.info(product.toString());
        currentUser.getProducts().add(product);
        productService.addNew(product);
        userService.refresh(currentUser);
        return "redirect:/products";
    }
}
