package com.smartprospect.smartprospect.businessdomain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessDomainService {
    private final BusinessDomainRepository businessDomainRepository;

    public Collection<BusinessDomain> getAll() {
        return businessDomainRepository.findAll();
    }

    public BusinessDomain getByName(String name) {
        return businessDomainRepository.findByName(name);
    }

    public void save(BusinessDomain domain) {
        businessDomainRepository.save(domain);
    }

    @Transactional
    public void edit(String name, String activities) {
        BusinessDomain domain = getByName(name);
        if (activities!=null && !activities.equals("")) {
            domain.setActivities(activities);
        }
    }


}
