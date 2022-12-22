package com.example.qlhtt.Repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CustomerUserRepos {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean update(int id){
        try{
            jdbcTemplate.update("Update CustomerUser set status = 0 where customer_id=? ",id);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    public boolean delete(int id){
        try{
            jdbcTemplate.update("Delete CustomerUser where customer_id=? ",id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

}