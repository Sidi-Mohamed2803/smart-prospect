package com.smartprospect.smartprospect.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CompanyRepository extends JpaRepository<Company,String> {
    Collection<Company> findByGovernorate(String governorate);

    @Query(value = "Select distinct governorate from company", nativeQuery = true)
    Collection<String> findGovernorates();

    @Query(value = "SELECT c FROM Company c WHERE c.governorate=':gov' AND c.activities LIKE '%:act%'")
    Collection<Company> advancedResearch(@Param("gov") String governorate, @Param("act") String activities);
}
