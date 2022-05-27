package com.smartprospect.smartprospect.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service @RequiredArgsConstructor @Slf4j
public class RoleService {
    private final RoleRepository roleRepository;

    public Collection<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }
}
