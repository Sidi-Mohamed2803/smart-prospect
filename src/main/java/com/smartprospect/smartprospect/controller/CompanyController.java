package com.smartprospect.smartprospect.controller;

import com.smartprospect.smartprospect.company.Company;
import com.smartprospect.smartprospect.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "companies") @Slf4j
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public String companies(ModelMap modelMap, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, @RequestParam(name = "governorate", required = false)Optional<String> govern, @RequestParam(name = "activity", required = false) Optional<String> activity) {
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

        return "companies";
    }

    @GetMapping(path = "/{denomination}")
    public String showCompany(ModelMap modelMap, @PathVariable(name = "denomination")String denomination, @RequestParam(name = "emailsent", required = false) Optional<Boolean> emailSent) {
        modelMap.addAttribute("company", companyService.getByDenomination(denomination));
        modelMap.addAttribute("emailSent", emailSent.orElse(null));
        return "showCompany";
    }
}
