package com.smartprospect.smartprospect.catalog;

import com.smartprospect.smartprospect.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity @Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor
public class Catalog {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @OneToMany
    private Collection<Product> products;
}
