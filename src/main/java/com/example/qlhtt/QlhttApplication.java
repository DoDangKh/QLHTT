package com.example.qlhtt;

import Repos.PersonRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import Entity.Person;

import java.util.List;

@SpringBootApplication
public class QlhttApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PersonRepos personRepos;

    public static void main(String[] args) {
        SpringApplication.run(QlhttApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String sql="Select * from person";
        List<Person> Persons=personRepos.findAll();
        for(int i=0;i<Persons.size();i++){
            System.out.println(Persons.get(i).getName());
        }
    }
}
