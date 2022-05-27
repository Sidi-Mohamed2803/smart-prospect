package com.smartprospect.smartprospect.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);



    @Query(value = "SELECT * FROM T_USER WHERE ACCOUNT_LOGIN = :login", nativeQuery = true)
    User getByLogin(@Param("login") String login);
}
