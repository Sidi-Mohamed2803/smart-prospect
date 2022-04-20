package com.smartprospect.smartprospect.useraccount;

import com.smartprospect.smartprospect.role.Role;
import com.smartprospect.smartprospect.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Data @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor
public class UserAccount implements Serializable {
    @Id @EqualsAndHashCode.Include
    private String login;
    private String password;
    private boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;
}
