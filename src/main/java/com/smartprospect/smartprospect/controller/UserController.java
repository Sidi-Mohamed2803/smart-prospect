package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.businessdomain.BusinessDomainRepository;
import com.smartprospect.smartprospect.businessdomain.BusinessDomainService;
import com.smartprospect.smartprospect.product.Product;
import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserService;
import com.smartprospect.smartprospect.useraccount.UserAccount;
import com.smartprospect.smartprospect.useraccount.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

@Controller @RequiredArgsConstructor @RequestMapping(path = "user") @Slf4j
public class UserController {
    private final UserService userService;
    private final UserAccountService userAccountService;
    private final BusinessDomainService businessDomainService;

    @GetMapping(path = "subscribe")
    public String subscribePage(ModelMap modelMap, User user) {
        modelMap.addAttribute("domains", businessDomainService.getAll());
        //modelMap.addAttribute("account", account);
        return "subscribe";
    }

    @GetMapping(path = "{username}")
    public String viewProfile(@PathVariable(name = "username") String username, ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "profile";
    }

    @PostMapping(path = "signing-in")
    public String signingIn(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(name = "pic", required = false) MultipartFile pic, @RequestParam(name = "confirmPassword")String confirmPassword, ModelMap modelMap) {
        if (result.hasErrors()) {
            if (user.getDomain().getName().equals("")) {
                modelMap.addAttribute("domainCheck", "Veuillez selectionner un domaine");
                modelMap.addAttribute("domainChecked", "false");
            }
            modelMap.addAttribute("domains", businessDomainService.getAll());
            return "subscribe";
        }
        if (user.getDomain().getName().equals("")) {
            modelMap.addAttribute("domains", businessDomainService.getAll());
            modelMap.addAttribute("domainCheck", "Veuillez selectionner un domaine");
            modelMap.addAttribute("domainChecked", "false");
            return "subscribe";
        }
        if (!pic.isEmpty() && pic != null) {
            try {
                user.setImage(Base64.getEncoder().encodeToString(pic.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userService.addNewUser(user);
        return "login";
    }

    @GetMapping(path = "admin")
    public String manageAccounts(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelMap.addAttribute("users", userService.getAll());
        return "admin";
    }


}
