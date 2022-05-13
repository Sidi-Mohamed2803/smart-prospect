package com.smartprospect.smartprospect.product;

import com.smartprospect.smartprospect.catalog.Catalog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity @Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor
@IdClass(ProductId.class)
public class Product {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @EqualsAndHashCode.Include
//    private Long id;
    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private Catalog catalog;
    @Id
    @EqualsAndHashCode.Include @NotBlank(message = "La référence ne peut pas être vide.")
    private String reference;
    @NotBlank(message = "Le nom ne peut pas être vide.")
    private String name;
    @NotNull(message = "Le prix ne peut pas être vide.")
    @DecimalMin(value = "0.1", inclusive = true, message = "Le prix ne peut pas être égal à 0.")
    private double price;
    @NotBlank(message = "Veuillez renseigner une description.")
    private String description;
    @Lob
    @Column(nullable = true)
    private String image;
    @Column(nullable = false)
    @NotBlank(message = "Veuillez sélectionner un type.")
    private String type;

    @Override
    public String toString() {
        return "Product{" +
                "catalog=" + catalog +
                ", reference='" + reference + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
