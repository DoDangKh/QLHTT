package com.example.qlhtt;

import com.example.qlhtt.Entity.Role;
import com.example.qlhtt.Entity.UserLogin;
import com.example.qlhtt.Repos.PersonRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.example.qlhtt.Entity.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
@ComponentScan
@EntityScan
@EnableJpaRepositories
@EnableAutoConfiguration
public class QlhttApplication  {

    @Autowired
    private  PersonRepos personRepos ;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
//        List<Person> Persons=personRepos.getall();
//        //List<Person> Persons=jdbcTemplate.query("Select * From Person", BeanPropertyRowMapper.newInstance(Person.class));
//        for(int i=-1;i<Persons.size();i++){
//            System.out.println(Persons.get(i).getName());
//        }
        SpringApplication.run(QlhttApplication.class, args);
    }

}
