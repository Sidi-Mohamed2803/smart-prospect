package com.smartprospect.smartprospect.user;

import com.smartprospect.smartprospect.businessdomain.BusinessDomain;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service @RequiredArgsConstructor @Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final BusinessDomainRepository businessDomainRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Override
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        Optional<UserAccount> userAccount = userAccountRepository.findByLogin(login);
//        if (!userAccount.isPresent()) {
//            log.error("User not found in the database");
//            throw new UsernameNotFoundException("User not found in the database");
//        } else {
//            log.info("User found in the database: {}", login);
//        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(userAccount.get().getRole().getName()));
//        return new org.springframework.security.core.userdetails.User(userAccount.get().getLogin(), userAccount.get().getPassword(), authorities);
//    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public void addNewUser(User user) {
        if (user!=null && user.getAccount()!=null) {
            Optional<User> userOptional = userRepository.findById(user.getEmail());
            Optional<UserAccount> userAccountOptional = userAccountRepository.findByLogin(user.getAccount().getLogin());
            if (userOptional.isPresent()) {
                throw new IllegalStateException("Email taken. Please enter another email.");
            }
            if (userAccountOptional.isPresent()) {
                throw new IllegalStateException("Login taken. Please enter another login.");
            }


            user.getAccount().setRole(roleRepository.findByName("USER"));
            user.getAccount().setActive(true);
            user.getAccount().setPassword(bCryptPasswordEncoder.encode(user.getAccount().getPassword()));

            userRepository.save(user);
        }
    }

    @Transactional
    public void editUser(User user, String firstName, String lastName, String login, String profession, BusinessDomain domain, String phoneNumber, MultipartFile pic) {
        User editedUser = userRepository.findByEmail(user.getEmail()).get();
        editedUser.setFirstName(firstName);
        editedUser.setLastName(lastName);
        editedUser.getAccount().setLogin(login);
        editedUser.setProfession(profession);
        editedUser.setDomain(domain);
        editedUser.setPhoneNumber(phoneNumber);
        if (!pic.isEmpty() && pic != null) {
            try {
                editedUser.setImage(Base64.getEncoder().encodeToString(pic.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByLogin(String login) {
        return userRepository.getByLogin(login);
    }
}
