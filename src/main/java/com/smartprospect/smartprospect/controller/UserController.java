package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.businessdomain.BusinessDomainRepository;
import com.smartprospect.smartprospect.catalog.Catalog;
import com.smartprospect.smartprospect.catalog.CatalogService;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

@Controller @RequiredArgsConstructor @RequestMapping(path = "user") @Slf4j
public class UserController {
    private final UserService userService;
    private final UserAccountService userAccountService;
    private final CatalogService catalogService;
    private final BusinessDomainRepository businessDomainRepository;

    @GetMapping(path = "subscribe")
    public String subscribePage(ModelMap modelMap, User user) {
        modelMap.addAttribute("domains", businessDomainRepository.findAll());
        //modelMap.addAttribute("account", account);
        return "subscribe";
    }

    @GetMapping(path = "{username}")
    public String viewProfile(@PathVariable(name = "username") String username, ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return "profile";
    }

    @PostMapping(path = "signing-in")
    public String signingIn(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap modelMap) {
        if (result.hasErrors()) {
            if (user.getDomain().getId()==0) {
                modelMap.addAttribute("domains", businessDomainRepository.findAll());
                modelMap.addAttribute("domainCheck", "Veuillez selectionner un domaine");
                modelMap.addAttribute("domainChecked", "false");
            }
            modelMap.addAttribute("domains", businessDomainRepository.findAll());
            return "subscribe";
        }
        if (user.getDomain().getId()==0) {
            modelMap.addAttribute("domains", businessDomainRepository.findAll());
            modelMap.addAttribute("domainCheck", "Veuillez selectionner un domaine");
            modelMap.addAttribute("domainChecked", "false");
            return "subscribe";
        }
//        Long id = Long.valueOf(domainId);
//        user.setDomain(businessDomainRepository.findById(id));
        //user.setAccount(account);
        Catalog catalog = new Catalog(user.getEmail(), new ArrayList<Product>());
        catalogService.addNew(catalog);
        user.setCatalog(catalog);
        userService.addNewUser(user);
        return "login";
    }

    @GetMapping(path = "admin")
    public String manageAccounts(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelMap.addAttribute("accounts", userAccountService.getAll());
        return "admin";
    }

    @PutMapping(path = "editstatus/{login}")
    public void editStatus(@PathVariable(name = "login") String login, HttpServletResponse response) {
        userAccountService.changeStatus(login);
        try {
            response.sendRedirect("/user/admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
