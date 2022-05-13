package com.smartprospect.smartprospect.useraccount;

import com.smartprospect.smartprospect.role.Role;
import com.smartprospect.smartprospect.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity @Data @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor
public class UserAccount implements Serializable {
    @Id @EqualsAndHashCode.Include
    @NotBlank(message = "Le login est un champ obligatoire.")
    @Pattern(regexp = "^[aA-zZ0-9]{8,}$", message = "Le login doit comporter minimum 8 caractères contenant des chiffres et des lettres.")
    private String login;
    @NotBlank(message = "Le mot de passe est un champ obligatoire.")
    @Pattern(regexp = "^(.+)[aA-zZ0-9]{8,}$", message = "Le mot de passe doit comporter minimum 8 caractères contenant des chiffres et des lettres.")
    private String password;
    private boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
}
