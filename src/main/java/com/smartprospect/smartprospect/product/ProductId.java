package com.smartprospect.smartprospect.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode @AllArgsConstructor
public class ProductId implements Serializable {
    //The 'catalog' attribute here has to have the same type of tha Catalog class id
    private String catalog;
    private String reference;
}
