package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller @RequiredArgsConstructor @Slf4j
public class MyErrorController implements ErrorController {
    private final UserService userService;

    @RequestMapping(path = "/error", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public String handleError(HttpServletRequest request, ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        if(String.valueOf(auth.getAuthorities()).equals("[ROLE_USER]")) {
            modelMap.addAttribute("auth", true);
        }
        else {
            modelMap.addAttribute("auth", false);
        }

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            String error = message.toString();
            modelMap.addAttribute("code", statusCode);

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                modelMap.addAttribute("message", "L'URL demandée, est introuvable, veuillez bien vérifier l'URL.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                modelMap.addAttribute("message", "Oups, il semblerait qu'un problème est survenu, ne vous inquiétez pas, nous nous occupons de tout, veuillez réessayez ultérieurement.");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                modelMap.addAttribute("message", "Désolé, vous n'avez pas les droits d'accès nécessaires, pour accéder à cette page.");
            } else {
                modelMap.addAttribute("message", error);
            }
        }
        return "error";
    }
}
