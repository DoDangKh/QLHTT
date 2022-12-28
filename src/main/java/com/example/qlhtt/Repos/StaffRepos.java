package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Staff;
import com.example.qlhtt.Entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class StaffRepos {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean updateSalary(Staff staff){
        try{
            jdbcTemplate.update("Update Staff set salary = ? where staff_id=? ",staff.getSalary(), staff.getStaff_id());
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    public boolean delete(int id){
        try{
            jdbcTemplate.update("Delete Staff where staff_id=? ",id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public Staff getStaffById(int id){
        try{
            Staff staff= jdbcTemplate.queryForObject("Select * From Staff Where staff_id="+ Integer.toString(id),(rs, rowNum) ->
                    new Staff(rs.getInt("staff_id"), rs.getInt("salary"), rs.getInt("status")));
            return staff;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}