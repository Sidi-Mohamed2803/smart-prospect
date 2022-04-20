package com.smartprospect.smartprospect.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity @Data @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true) @AllArgsConstructor
public class Role implements Serializable {
    @Id @EqualsAndHashCode.Include
    private String name;
    private String description;
}
