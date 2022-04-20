package com.smartprospect.smartprospect.user;

import com.smartprospect.smartprospect.businessdomain.BusinessDomain;
import com.smartprospect.smartprospect.catalog.Catalog;
import com.smartprospect.smartprospect.product.Product;
import com.smartprospect.smartprospect.useraccount.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor @Table(name = "T_USER")
public class User implements Serializable {
    @Id @EqualsAndHashCode.Include
    private String email;
    private String firstName;
    private String lastName;
    private String profession;
    @Column(nullable = true)
    private String phoneNumber;
    @Lob
    @Column(length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;
    @ManyToOne(fetch = FetchType.EAGER)
    private BusinessDomain domain;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UserAccount> accounts = new ArrayList<>();
    @OneToOne
    private Catalog catalog;
}
