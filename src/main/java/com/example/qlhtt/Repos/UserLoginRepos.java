package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserLoginRepos {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public UserLogin getUserName(String UserName){
        try {
            UserLogin userLogin = jdbcTemplate.queryForObject("Select * From UserLogin Where username='" + UserName+"'", (rs, rowNum) ->
                    new UserLogin(rs.getInt("person_id"), rs.getString("username"), rs.getString("password"), rs.getInt("role_id")));
            return userLogin;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
