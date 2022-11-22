package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PersonRepos  {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;




    public List<Person> getall() {
        //jdbcTemplate.setDataSource(dataSource);
        //dataSource.toString();
        //jdbcTemplate.getDataSource().toString();
        try {
            List<Person> persons = jdbcTemplate.query("Select * From Person", BeanPropertyRowMapper.newInstance(Person.class));
            return persons;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Person getbyid(int id){
        try{
            Person person= jdbcTemplate.queryForObject("Select * From Person Where id=" + Integer.toString(id), (rs, rowNum) ->
                    new Person(rs.getInt("id"), rs.getString("name"), rs.getString("gender"),rs.getString("identity_card"), rs.getDate("day_of_birth"), rs.getString("phone_num"), rs.getString("address")));
            return person;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}