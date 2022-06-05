package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.product.Product;
import com.smartprospect.smartprospect.product.ProductId;
import com.smartprospect.smartprospect.product.ProductService;
import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
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

    @GetMapping("edit/{ref}")
    public String editProduct(@PathVariable(name = "ref")String ref, ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        if(String.valueOf(auth.getAuthorities()).equals("[ROLE_USER]")) {
            modelMap.addAttribute("auth", true);
        }
        else {
            modelMap.addAttribute("auth", false);
        }

        Product product = productService.getByID(currentUser.getEmail(), ref);
        modelMap.addAttribute("product", product);

        return "editProduct";
    }

    @GetMapping("delete/{ref}")
    public String deleteProduct(@PathVariable(name = "ref")String ref) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        productService.remove(new ProductId(currentUser.getEmail(), ref));

        return "redirect:/products";
    }

    @PostMapping("editing")
    public String editing(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, String reference, @RequestParam(name = "pic", required = false) MultipartFile pic, ModelMap modelMap) {
        BindingResult result = new BeanPropertyBindingResult(product, "product");

        if (bindingResult.hasFieldErrors("name"))
            result.addError(bindingResult.getFieldError("name"));
        if (bindingResult.hasFieldErrors("price"))
            result.addError(bindingResult.getFieldError("price"));
        if (bindingResult.hasFieldErrors("description"))
            result.addError(bindingResult.getFieldError("description"));
        if (bindingResult.hasFieldErrors("selected"))
            result.addError(bindingResult.getFieldError("selected"));

        if (result.hasErrors()) {
            return "editProduct";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        if(String.valueOf(auth.getAuthorities()).equals("[ROLE_USER]")) {
            modelMap.addAttribute("auth", true);
        }
        else {
            modelMap.addAttribute("auth", false);
        }

        productService.edit(new ProductId(currentUser.getEmail(), reference), product.getPrice(), product.getDescription(), product.getName(), pic, product.getSelected());
        return "redirect:/products";
    }

    @GetMapping
    public String productsPage(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        if(String.valueOf(auth.getAuthorities()).equals("[ROLE_USER]")) {
            modelMap.addAttribute("auth", true);
        }
        else {
            modelMap.addAttribute("auth", false);
        }

        modelMap.addAttribute("products", currentUser.getProducts());
        return "products";
    }

    @GetMapping("catalog")
    public String catalog(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("products", currentUser.getProducts());
        return "catalog";
    }

    @GetMapping(path = "new")
    public String newProduct(Product product, ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        if(String.valueOf(auth.getAuthorities()).equals("[ROLE_USER]")) {
            modelMap.addAttribute("auth", true);
        }
        else {
            modelMap.addAttribute("auth", false);
        }

        return "addProduct";
    }

    @PostMapping(path = "add")
    public String addNewProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, @RequestParam(name = "pic", required = false) MultipartFile pic, ModelMap modelMap) {
        if (result.hasErrors()) {
            return  "addProduct";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        if(String.valueOf(auth.getAuthorities()).equals("[ROLE_USER]")) {
            modelMap.addAttribute("auth", true);
        }
        else {
            modelMap.addAttribute("auth", false);
        }

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
