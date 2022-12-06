package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Cart;
import com.example.qlhtt.Entity.CustomerOrder;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class OrderRepos {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean checkout(List<Cart> cartList){
        try{
            int sum=0;
            for(int i=0;i<cartList.size();i++){
                sum+=cartList.get(i).getQuantity()*cartList.get(i).getProduct().getPrice();
            }
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now=LocalDateTime.now();
            jdbcTemplate.update("insert into CustomerOrder(order_time, status_now, Customer_id, total_money) values(?,?,?,?)", dtf.format(now),0,cartList.get(0).getCustomer_id(),sum);
            int id=jdbcTemplate.queryForObject("Select Max(order_id) From CustomerOrder",Integer.class);
            for(int i=0;i<cartList.size();i++){
                jdbcTemplate.update("insert into OrderDetail(order_id,product_id,num_of_product,cur_price) values(?,?,?,?)",id,cartList.get(i).getProduct().getProduct_id()
                ,cartList.get(i).getQuantity(),cartList.get(i).getProduct().getPrice());
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<CustomerOrder> getall(){
        try{
            List<CustomerOrder>customerOrders =jdbcTemplate.query("Select * From CustomerOrder", BeanPropertyRowMapper.newInstance(CustomerOrder.class));
            return  customerOrders;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
