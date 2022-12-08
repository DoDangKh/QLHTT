package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.*;
import java.util.List;

@Repository
public class TypeRepos {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Type> getall(){
        try{
            List<Type> types=jdbcTemplate.query("Select * From Type", BeanPropertyRowMapper.newInstance(Type.class));
            return types;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean add(Type type){
        try{
            jdbcTemplate.update("insert into Type(name) Values(?)",type.getName());
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
