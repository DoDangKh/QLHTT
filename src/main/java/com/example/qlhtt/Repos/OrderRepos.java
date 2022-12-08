package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Cart;
import com.example.qlhtt.Entity.CustomerOrder;
import org.hibernate.criterion.Order;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Repository
public class OrderRepos {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductRepos productRepos;

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
    public List<Cart> OrderDetail(int id){
        try{
            List<Cart> cart= jdbcTemplate.query("Select order_id,product_Id,num_of_product From OrderDetail where order_id=" + id
                    , (rs, rowNum) -> new Cart(rs.getInt("order_id"),rs.getInt("Product_id"),rs.getInt("num_of_product")));
            for(int i=0;i< cart.size();i++){
                cart.get(i).setProduct(productRepos.getid(cart.get(i).getProduct().getProduct_id()));
            }
            return cart;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean checkOrder(int id, int Employee_id){
        try{
            jdbcTemplate.update("Update CustomerOrder set status_now=? ,staff_id=? where order_id=?"
            ,1 ,Employee_id ,id);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
