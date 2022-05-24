package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.businessdomain.BusinessDomainService;
import com.smartprospect.smartprospect.user.UserService;
import com.smartprospect.smartprospect.useraccount.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequiredArgsConstructor @RequestMapping(path = "admin") @Slf4j
public class AdminController {
    private final UserService userService;
    private final UserAccountService userAccountService;
    private final BusinessDomainService businessDomainService;

    @GetMapping
    public String dashboard(ModelMap modelMap) {
        return "dashboard";
    }
}
