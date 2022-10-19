package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepos extends JpaRepository<Person, Integer> {

}
