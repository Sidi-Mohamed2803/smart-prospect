package com.smartprospect.smartprospect.businessdomain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessDomainService {
    private final BusinessDomainRepository businessDomainRepository;

    public Collection<BusinessDomain> getAll() {
        return businessDomainRepository.findAll();
    }


}
