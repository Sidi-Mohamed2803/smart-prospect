package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

@Controller @RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/")
public class HomeController {
    private final CompanyService companyService;

    @GetMapping
    public String homePage(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(auth.getName());
        return "index";
    }

    @GetMapping(path = "login")
    public String loginPage(ModelMap modelMap) {
        return "login";
    }

    @GetMapping(path = "contact-admin")
    public String contactAdminPage(ModelMap modelMap) {
        return "contact";
    }

    @GetMapping(path = "search")
    public String searchPage(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        modelMap.addAttribute("governorates", companyService.getGovernorates());
        return "research";
    }

}
