package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.emailsender.EmailSenderService;
import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller @RequiredArgsConstructor @Slf4j @RequestMapping(path = "mail")
public class MailController {
    private final EmailSenderService emailSenderService;
    private final UserService userService;

    @GetMapping
    public String newMail(ModelMap modelMap, @RequestParam(name = "toEmail") String toEmail, @RequestParam(name = "denomination") String denomination) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(auth.getName());
        modelMap.addAttribute("toEmail", toEmail);
        modelMap.addAttribute("denomination", denomination);
        return "newEmail";
    }

    @PostMapping(path = "send")
    public void sendEmail(@RequestParam(name = "denomination") String denomination, String fromEmail, String toEmail, String subject, String body, HttpServletResponse response, HttpServletRequest request) {
        emailSenderService.sendEmail(fromEmail, toEmail, subject, body);
        HttpSession session = request.getSession();
        try {
            response.sendRedirect("/companies/"+denomination+"?emailsent=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(path = "send-to-admin")
    public void sendEmailToAdmin(String fromEmail, String body, HttpServletResponse response) {
        emailSenderService.sendEmailToAdmin(fromEmail, body);
        try {
            response.sendRedirect("/?emailsent=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
