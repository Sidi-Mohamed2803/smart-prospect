package com.smartprospect.smartprospect.catalog;

import com.smartprospect.smartprospect.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity @Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor
public class Catalog {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "catalog")
    private Collection<Product> products;
}
