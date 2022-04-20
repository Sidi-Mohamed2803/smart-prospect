package com.smartprospect.smartprospect.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping(path = "/error", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public String handleError(HttpServletRequest request, ModelMap modelMap) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            modelMap.addAttribute("code", statusCode);

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                modelMap.addAttribute("message", "L'URL demandée, est introuvable, veuillez bien vérifier l'URL.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                modelMap.addAttribute("message", "Oups, il semblerait qu'un problème est survenu, ne vous inquiétez pas, nous nous occupons de tout, veuillez réessayez ultérieurement.");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                modelMap.addAttribute("message", "Désolé, vous n'avez pas les droits d'accès nécessaires, pour accéder à cette page.");
            }
        }
        return "error";
    }
}
