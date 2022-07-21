package com.example.Training.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "person_id")
    @SequenceGenerator(name = "person_id", allocationSize = 50)
    Long id;

    @Column(name = "first" , nullable = false , length = 50, unique = true)
    String firstName;
    String lastName;
    String department;

    @NotEmpty
    @Email
    String email;

    @OneToOne
    @JoinColumn(name = "address_id")
    Address address;

}
