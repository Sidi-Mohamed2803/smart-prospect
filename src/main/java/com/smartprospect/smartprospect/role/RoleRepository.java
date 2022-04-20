package com.smartprospect.smartprospect.role;

import com.smartprospect.smartprospect.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByName(String name);
}
