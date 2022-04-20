package com.smartprospect.smartprospect.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Company {
    @Id
    @EqualsAndHashCode.Include
    private String denomination;
    private String companyName;
    private String certificate;
    private String personInCharge;
    @Column(length = 400)
    private String activities;
    @Column(length = 500)
    private String products;
    private String factoryAddress;
    private String governorate;
    private String delegation;
    private String telephone;
    private String fax;
    private String email;
    private String webSiteUrl;
    private String regime;
    private String partnerCountries;
    private LocalDate productionEntryDate;
    private double capital;
    private int employment;
    private String area;
}
