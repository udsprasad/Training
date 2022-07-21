package com.example.Training.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Address {

    @Id
    Long Id;
    String landMark;
    String streetName;
}
