package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Product;
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
    public Type getid(int id){
        try{
            Type type= jdbcTemplate.queryForObject("Select * From Type Where Type_id="+ Integer.toString(id),(rs, rowNum) ->
                    new Type(rs.getString("Type_id"), rs.getString("name")));
            return type;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean add(Type type){
        try{
            jdbcTemplate.update("insert into Type(name) Values(?)",standardNames(type.getName()));
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private String standardNames(String name){
        String firstLetter = name.substring(0, 1);
        String remainingLetters = name.substring(1, name.length());
        firstLetter = firstLetter.toUpperCase();

        return firstLetter + remainingLetters;
    }

    public boolean delete(int type_id){
        try{
            jdbcTemplate.update("Delete Type Where Type_id=?",type_id);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
