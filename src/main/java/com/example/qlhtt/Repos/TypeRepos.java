package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Role;
import com.example.qlhtt.Entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TypeRepos {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Type> getall(){
        try{
            List<Type> types= jdbcTemplate.query("Select * From ROLE", BeanPropertyRowMapper.newInstance(Type.class));
            return types;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
