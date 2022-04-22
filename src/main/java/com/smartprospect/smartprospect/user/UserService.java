package com.smartprospect.smartprospect.user;

import com.smartprospect.smartprospect.businessdomain.BusinessDomainRepository;
import com.smartprospect.smartprospect.role.RoleRepository;
import com.smartprospect.smartprospect.useraccount.UserAccount;
import com.smartprospect.smartprospect.useraccount.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final BusinessDomainRepository businessDomainRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserAccount> userAccount = userAccountRepository.findByLogin(login);
        if (!userAccount.isPresent()) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", login);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userAccount.get().getRole().getName()));
        return new org.springframework.security.core.userdetails.User(userAccount.get().getLogin(), userAccount.get().getPassword(), authorities);
    }

    public void addNewUser(User user, UserAccount userAccount) {
        if (user!=null && userAccount!=null) {
            Optional<User> userOptional = userRepository.findById(user.getEmail());
            Optional<UserAccount> userAccountOptional = userAccountRepository.findByLogin(userAccount.getLogin());
            if (!user.getEmail().contains("@")) {
                throw new IllegalStateException("Please enter a valid email.");
            } else if (userOptional.isPresent()) {
                throw new IllegalStateException("Email taken. Please enter another email.");
            }
            if (userAccountOptional.isPresent()) {
                throw new IllegalStateException("Login taken. Please enter another login.");
            }

            userAccount.setRole(roleRepository.findByName("USER"));
            userAccount.setActive(true);
            userAccount.setUser(user);
            userAccount.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
            user.getAccounts().add(userAccount);

            userRepository.save(user);
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
