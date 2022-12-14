package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Staff;
import com.example.qlhtt.Entity.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@ConfigurationPropertiesScan
@ComponentScan
public class UserLoginRepos {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public UserLogin getUserName(String UserName){
        try {
            UserLogin userLogin = jdbcTemplate.queryForObject("Select * From UserLogin Where username='" + UserName+"'", (rs, rowNum) ->
                    new UserLogin(rs.getInt("person_id"), rs.getString("username"), rs.getString("password"), rs.getInt("enable"),rs.getInt("role_id")));
            return userLogin;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public UserLogin getid(int id){
        try {
            UserLogin userLogin = jdbcTemplate.queryForObject("Select * From UserLogin Where person_id='" + id+"'", (rs, rowNum) ->
                    new UserLogin(rs.getInt("person_id"), rs.getString("username"), rs.getString("password"), rs.getInt("enable"),rs.getInt("role_id")));
            return userLogin;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public int CheckUserName(String UserName){
        try{
            int count=jdbcTemplate.queryForObject("SELECT count(*) FROM UserLogin WHERE USERNAME='"+UserName+"'",Integer.class);
            return count;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    public boolean saveUser(UserLogin userLogin){
        try{
            String password=userLogin.getPassword();
            //userLogin.setPassword(bCryptPasswordEncoder.encode(password));
            userLogin.setRole_id(2);
            jdbcTemplate.update("Insert into UserLogin(person_id, username, password, enable, role_id) VALUES(?, ?, ?, ?, ?)"
            , userLogin.getPerson_id(), userLogin.getUsername(), userLogin.getPassword(), 1, userLogin.getRole_id());
            jdbcTemplate.update("Insert into CustomerUser(customer_id ,status) VALUES(?,?)", userLogin.getPerson_id(),1);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean saveEmployee(UserLogin userLogin, Staff staff){
        try{
            String password=userLogin.getPassword();
            //userLogin.setPassword(bCryptPasswordEncoder.encode(password));
            userLogin.setRole_id(1);
            jdbcTemplate.update("Insert into UserLogin(person_id, username, password, enable, role_id) VALUES(?, ?, ?, ?, ?)"
                    , userLogin.getPerson_id(), userLogin.getUsername(), userLogin.getPassword(), 1, userLogin.getRole_id());
            jdbcTemplate.update("Insert into Staff(staff_id ,salary, status) VALUES(?,?,?)", userLogin.getPerson_id(),staff.getSalary(),1);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateUser(UserLogin userLogin,int role){
        try{
            String password=userLogin.getPassword();
            //userLogin.setPassword(bCryptPasswordEncoder.encode(password));
            userLogin.setRole_id(role);
            jdbcTemplate.update("Update UserLogin set Username=? ,Password=? ,Role_id=? Where person_id=?"
                    ,  userLogin.getUsername(), userLogin.getPassword(), userLogin.getRole_id(), userLogin.getPerson_id());
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean delete(int id){
        try{
            jdbcTemplate.update("Delete UserLogin where person_id=? ",id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean updateStatus(int id){
        try{
            jdbcTemplate.update("Update UserLogin set enable = 0 where person_id=? ",id);
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    public String checkAccount(UserLogin userLogin){
        if(userLogin.getUsername().equals("")){
            return "T??n t??i kho???n kh??ng ???????c b??? tr???ng!";
        }else if(userLogin.getPassword().equals(""))
            return "M???t kh???u kh??ng ???????c b??? tr???ng!";
        return "";
    }

}
