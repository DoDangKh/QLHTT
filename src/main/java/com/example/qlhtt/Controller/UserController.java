package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.Person;
import com.example.qlhtt.Repos.PersonRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PersonRepos personRepos;


    @GetMapping("")
    public ResponseEntity<?> getListUser(){

            List<Person> persons = personRepos.getall();
            return ResponseEntity.ok(persons);
    }
}
