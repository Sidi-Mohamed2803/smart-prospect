package com.smartprospect.smartprospect.businessdomain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity @Data @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor
public class BusinessDomain implements Serializable {
    @Id @GeneratedValue @EqualsAndHashCode.Include
    private Long id;
    private String name;
}
