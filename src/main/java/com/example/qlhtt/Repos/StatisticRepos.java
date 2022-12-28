package com.example.qlhtt.Repos;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticRepos {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<String> loadyear(){
        try{
            List<String> years= jdbcTemplate.query("Select DISTINCT order_time from CustomerOrder order by order_time ",(rs, rowNum) -> rs.getString("order_time"));
            System.out.print(years.size());
            String temp=years.get(0).substring(0,4);
            List<String> templist= new ArrayList<>();
            templist.add(temp);
            for(int i=1;i<years.size();i++) {
                if (!years.get(i).substring(0, 4).equals(temp)) {
                    temp = years.get(i).substring(0, 4);
                    templist.add(temp);
                }
                System.out.println(years.get(i) + " xxxxxxxxxxxxx");
            }
            return templist;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
    public int loadincome(int year,int month){
        try{
            return jdbcTemplate.queryForObject("Select sum(total_money) from CustomerOrder where Month(order_time)="+month+" and Year(order_time)="+year,Integer.class);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

}
