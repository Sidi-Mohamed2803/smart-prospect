package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/")
public class HomeController {
    private final CompanyService companyService;

    @GetMapping
    public String homePage(ModelMap modelMap) {
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

        modelMap.addAttribute("governorates", companyService.getGovernorates());
        return "research";
    }

}
