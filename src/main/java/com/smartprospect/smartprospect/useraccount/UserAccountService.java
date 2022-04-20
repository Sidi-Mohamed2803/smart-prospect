package com.smartprospect.smartprospect.useraccount;

import com.smartprospect.smartprospect.businessdomain.BusinessDomainRepository;
import com.smartprospect.smartprospect.role.RoleRepository;
import com.smartprospect.smartprospect.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Slf4j
public class UserAccountService {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final BusinessDomainRepository businessDomainRepository;
    private final RoleRepository roleRepository;

    public List<UserAccount> getAll() {
        return userAccountRepository.findAll();
    }

    public UserAccount getByLogin(String login) {
        if (login!=null)
            return null;
        return userAccountRepository.getById(login);
    }

    @Transactional
    public void changeStatus(String login) {
        if (login==null)
            return;
        UserAccount userAccount = userAccountRepository.getById(login);
        if (userAccount==null) {
            throw new IllegalStateException("Account not found. Please enter another login.");
        }
        boolean active = userAccount.isActive() ? false : true;
        userAccount.setActive(active);
    }
}
