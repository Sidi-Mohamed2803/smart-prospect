package com.smartprospect.smartprospect.product;

import com.smartprospect.smartprospect.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity @Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor
@IdClass(ProductId.class)
public class Product {
    @Id
    @ManyToOne
    @EqualsAndHashCode.Include
    private User user;
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
    @NotNull(message = "Veuillez préciser si vous voulez que ce produit figure ou non sur le catalogue.\nPeut être modifié ultérieurement.")
    private Boolean selected;
    @Column(nullable = false)
    @NotBlank(message = "Veuillez sélectionner un type.")
    private String type;

    @Override
    public String toString() {
        return "Product{" +
                "user=" + user +
                ", reference='" + reference + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
