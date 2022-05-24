package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Controller @RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/")
public class HomeController {
    private final CompanyService companyService;

    @GetMapping
    public String homePage(ModelMap modelMap, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info(String.valueOf(auth.getAuthorities()));
        if(String.valueOf(auth.getAuthorities()).equals("[ROLE_ADMIN]")){
            try {
                response.sendRedirect("/admin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(String.valueOf(auth.getAuthorities()).equals("[ROLE_USER]")) {
            modelMap.addAttribute("auth", true);
        }
        else {
            modelMap.addAttribute("auth", false);
        }
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
