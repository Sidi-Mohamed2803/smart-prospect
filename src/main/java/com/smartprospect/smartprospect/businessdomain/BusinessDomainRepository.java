package com.smartprospect.smartprospect.businessdomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessDomainRepository extends JpaRepository<BusinessDomain,String> {
    BusinessDomain findById(Long id);
}
