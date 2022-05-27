package com.smartprospect.smartprospect.user;

import com.smartprospect.smartprospect.businessdomain.BusinessDomain;
import com.smartprospect.smartprospect.product.Product;
import com.smartprospect.smartprospect.useraccount.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor @Table(name = "T_USER")
public class User implements Serializable {
    @Id @EqualsAndHashCode.Include
    @NotBlank(message = "L'e-mail est un champ obligatoire")
    @Email(regexp = "^(.+)@(.+)\\.(.+)$", message = "Veuillez renseigner un email valide.\nEx. : abcd@efg.hij")
    private String email;
    @NotBlank(message = "Le prénom est est un champ obligatoire")
    private String firstName;
    @NotBlank(message = "Le nom est est un champ obligatoire")
    private String lastName;
    @NotBlank(message = "La profession est est un champ obligatoire")
    private String profession;
    @Column(nullable = true)
    @Pattern(regexp = "[0-9]{8}", message = "Le numéro doit comporter 8 chiffres")
    @Size(max = 8)
    private String phoneNumber;
    @Lob
    @Column(length = Integer.MAX_VALUE, nullable = true)
    private String image;
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message = "Veuillez sélectionner un domaine.")
    private BusinessDomain domain;
    //@Valid
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserAccount account;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Collection<Product> products = new ArrayList<Product>();
}
