package com.smartprospect.smartprospect.company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Collection<Company> getAll() {
        return companyRepository.findAll();
    }

    public Page<Company> getAllPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Company> list;
        List<Company> companies = companyRepository.findAll();

        if(companies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, companies.size());
            list = companies.subList(startItem, toIndex);
        }

        Page<Company> companyPage = new PageImpl<Company>(list, PageRequest.of(currentPage,pageSize),companies.size());

        return companyPage;
    }

    public Page<Company> getAllPaginated(Pageable pageable, String governorate, String activities) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Company> list;
        List<Company> companies = (List<Company>) companyRepository.advancedResearch(governorate,"%"+activities+"%");

        if(companies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, companies.size());
            list = companies.subList(startItem, toIndex);
        }

        Page<Company> companyPage = new PageImpl<Company>(list, PageRequest.of(currentPage,pageSize),companies.size());

        return companyPage;
    }

    public Page<Company> getAllPaginated(Pageable pageable, String activities) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Company> list;
        List<Company> companies = (List<Company>) companyRepository.getByActivities("%"+activities+"%");

        if(companies.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, companies.size());
            list = companies.subList(startItem, toIndex);
        }

        Page<Company> companyPage = new PageImpl<Company>(list, PageRequest.of(currentPage,pageSize),companies.size());

        return companyPage;
    }

    public Collection<Company> getByGovernorate(String governorate) {
        return companyRepository.findByGovernorate(governorate);
    }

    public Collection<String> getGovernorates()
    {
        return companyRepository.findGovernorates();
    }

    public Company getByDenomination(String denomination) {
        return companyRepository.findByDenomination(denomination);
    }

    public void addNew(Company company) {

        companyRepository.save(company);
    }

    @Transactional
    public void edit(Company company) {

    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }
}
