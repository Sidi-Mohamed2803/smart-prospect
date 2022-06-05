package com.smartprospect.smartprospect.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,ProductId> {

    @Query(value = "SELECT * FROM PRODUCT WHERE USER_EMAIL = :user AND REFERENCE = :ref", nativeQuery = true)
    Product findByUserAndReference(@Param("user") String user, @Param("ref") String reference);
}
