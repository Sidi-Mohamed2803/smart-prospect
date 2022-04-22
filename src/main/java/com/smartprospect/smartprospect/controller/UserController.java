package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.businessdomain.BusinessDomainRepository;
import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserService;
import com.smartprospect.smartprospect.useraccount.UserAccount;
import com.smartprospect.smartprospect.useraccount.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller @RequiredArgsConstructor @RequestMapping(path = "user") @Slf4j
public class UserController {
    private final UserService userService;
    private final UserAccountService userAccountService;
    private final BusinessDomainRepository businessDomainRepository;

    @GetMapping(path = "subscribe")
    public String subscribePage(ModelMap modelMap) {
        modelMap.addAttribute("domains", businessDomainRepository.findAll());
        return "subscribe";
    }

    @GetMapping(path = "{username}")
    public String viewProfile(@PathVariable(name = "username") String username, ModelMap modelMap) {

        return "profile";
    }

    @PostMapping(path = "signing-in")
    public void signingIn(User user, UserAccount userAccount, @RequestParam(name = "BDomain") String domainId, @RequestParam(name = "phone", required = false) String phoneNumber, HttpServletResponse response) {
        Long id = Long.valueOf(domainId);
        user.setDomain(businessDomainRepository.findById(id));
        user.setPhoneNumber(phoneNumber);
        userService.addNewUser(user, userAccount);
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "admin")
    public String manageAccounts(ModelMap modelMap) {
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
