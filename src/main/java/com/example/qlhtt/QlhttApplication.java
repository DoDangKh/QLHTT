package com.example.qlhtt;

import com.example.qlhtt.Repos.PersonRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.qlhtt.Entity.Person;

import java.util.List;

@SpringBootApplication
@ComponentScan
@EntityScan
@EnableJpaRepositories
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
        //List<Person> Persons=jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(Person.class));
        List<Person> Persons2=personRepos.findAll();
        Persons2.forEach(System.out :: println);
        //for(int i=0;i<Persons.size();i++){
        //    System.out.println(Persons.get(i).getName());
        //}
    }
}
