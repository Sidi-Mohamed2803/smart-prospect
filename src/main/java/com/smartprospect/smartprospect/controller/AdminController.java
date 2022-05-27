package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.businessdomain.BusinessDomainService;
import com.smartprospect.smartprospect.company.Company;
import com.smartprospect.smartprospect.company.CompanyService;
import com.smartprospect.smartprospect.role.Role;
import com.smartprospect.smartprospect.role.RoleService;
import com.smartprospect.smartprospect.scraper.CompanyScraper;
import com.smartprospect.smartprospect.user.User;
import com.smartprospect.smartprospect.user.UserService;
import com.smartprospect.smartprospect.useraccount.UserAccount;
import com.smartprospect.smartprospect.useraccount.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller @RequiredArgsConstructor @RequestMapping(path = "admin") @Slf4j
public class AdminController {
    private final UserService userService;
    private final UserAccountService userAccountService;
    private final BusinessDomainService businessDomainService;
    private final CompanyService companyService;
    private final CompanyScraper companyScraper;
    private final RoleService roleService;

    @GetMapping
    public String dashboard(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        return "dashboard";
    }

    @GetMapping("updating")
    public String updating() {
        companyScraper.ScrapeIndustriesPIT();
        return "redirect:/admin/companies";
    }

    @GetMapping("companies")
    public String companies(ModelMap modelMap, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam(name = "governorate", required = false)Optional<String> govern, @RequestParam(name = "activity", required = false) Optional<String> activity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(30);
        String governorate = govern.orElse("");
        String activities = activity.orElse("");
        Page<Company> companyPage;

        if (governorate.equals("") && activities.equals(""))
            companyPage = companyService.getAllPaginated(PageRequest.of(currentPage - 1, pageSize));
        else if (governorate.equals("") && !activities.equals(""))
            companyPage = companyService.getAllPaginated(PageRequest.of(currentPage - 1, pageSize), activities);
        else {
            companyPage = companyService.getAllPaginated(PageRequest.of(currentPage - 1, pageSize), governorate, activities);
        }

        modelMap.addAttribute("governorates", companyService.getGovernorates());
        modelMap.addAttribute("governorate", governorate);
        modelMap.addAttribute("companyPage", companyPage);

        int totalPages = companyPage.getTotalPages();
        if (totalPages> 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        return "databaseUpdate";
    }

    @GetMapping(path = "{username}")
    public String viewProfile(@PathVariable(name = "username") String username, ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("fullName", currentUser.getFirstName()+" "+currentUser.getLastName());
        modelMap.addAttribute("domains", businessDomainService.getAll());
        return "profile";
    }

    @PostMapping(path = "edit")
    public String editProfile(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(name = "pic", required = false) MultipartFile pic, ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        user.setEmail(currentUser.getEmail());
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("fullName", currentUser.getFirstName()+" "+currentUser.getLastName());
        modelMap.addAttribute("domains", businessDomainService.getAll());

        BindingResult result = new BeanPropertyBindingResult(user, "user");

        if (bindingResult.hasFieldErrors("firstName"))
            result.addError(bindingResult.getFieldError("firstName"));
        if (bindingResult.hasFieldErrors("lastName"))
            result.addError(bindingResult.getFieldError("lastName"));
        if (bindingResult.hasFieldErrors("account.login"))
            result.addError(bindingResult.getFieldError("account.login"));
        if (bindingResult.hasFieldErrors("profession"))
            result.addError(bindingResult.getFieldError("profession"));
        if (bindingResult.hasFieldErrors("phoneNumber"))
            result.addError(bindingResult.getFieldError("phoneNumber"));

        if (result.hasErrors()) {
            if (user.getDomain().getName().equals("")) {
                modelMap.addAttribute("domainCheck", "Veuillez selectionner un domaine");
                modelMap.addAttribute("domainChecked", "false");
            }
            log.info(currentUser.getEmail()+"lklvll");
            return "profile";
        }
        if (user.getDomain().getName().equals("")) {
            modelMap.addAttribute("domainCheck", "Veuillez selectionner un domaine");
            modelMap.addAttribute("domainChecked", "false");
            log.info(currentUser.getEmail()+"lklvllijtirjth888");
            return "profile";
        }
        userService.editUser(user, user.getFirstName(), user.getLastName(), user.getAccount().getLogin(), user.getProfession(), user.getDomain(), user.getPhoneNumber(), pic);
        return "redirect:/admin/"+currentUser.getEmail();
    }

    @GetMapping("users")
    public String users(ModelMap modelMap) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("users", userService.getAll());
        return "users";
    }

//    @GetMapping("update")
//    public String update(ModelMap modelMap) {Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = userService.getByLogin(auth.getName());
//        modelMap.addAttribute("user", currentUser);
//        return "";
//    }

    @GetMapping("changerole/{login}")
    public String roles(ModelMap modelMap, @PathVariable(name = "login") String login) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.getByLogin(auth.getName());
        modelMap.addAttribute("user", currentUser);
        modelMap.addAttribute("selectedUser", userAccountService.getByLogin(login));
        modelMap.addAttribute("roles", roleService.getAll());
        return "editRole";
    }

    @GetMapping("changingrole/{login}")
    public String changingRole(ModelMap modelMap, String role, @PathVariable(name = "login") String login) {
        UserAccount selectedUser = userAccountService.getByLogin(login);
        selectedUser.setRole(roleService.getByName(role));
        userAccountService.save(selectedUser);
        return "redirect:/admin/users";
    }

    @PutMapping(path = "editstatus/{login}")
    public void editStatus(@PathVariable(name = "login") String login, HttpServletResponse response) {
        userAccountService.changeStatus(login);
        try {
            response.sendRedirect("/admin/users");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
