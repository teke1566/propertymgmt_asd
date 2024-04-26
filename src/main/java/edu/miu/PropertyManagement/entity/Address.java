package edu.miu.PropertyManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@ToString
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String street;
    private  String  city;
    private  String state;
    private  String zipCode;
    @OneToOne(mappedBy = "address")
    private  Property property;



}
