package com.decodebytes.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import lombok.Data;

@Entity(name = "name_address")
@NamedQuery(name = "NameAddress.findAll", query = "SELECT n FROM name_address n")
@Data
public class NameAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @Column(name = "house_number")
  private String houseNumber;

  private String city;
  private String province;

  @Column(name = "postal_code")
  private String postalCode;
}
