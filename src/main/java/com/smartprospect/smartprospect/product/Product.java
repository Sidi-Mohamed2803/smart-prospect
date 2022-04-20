package com.smartprospect.smartprospect.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @EqualsAndHashCode.Include
    private String reference;
    private String name;
    private double price;
    private String description;
    @Lob
    @Column(length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;
    @Column(nullable = false)
    private String type;
}
