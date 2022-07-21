package com.example.Training.Repo;

import com.example.Training.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FirstRepo extends JpaRepository<Person,Long> {

    List<Person> findByFirstNameContains(String first);
}
