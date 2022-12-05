package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Cart;
import com.example.qlhtt.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CartRepos {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ProductRepos productRepos;

    public List<Cart> getbyID(int person_Id){
        try{
            List<Cart> cart=jdbcTemplate.query("Select * From Cart Where customer_id='"+ person_Id+"'"
            ,(rs, rowNum) -> new Cart(rs.getInt("customer_id"),rs.getInt("product_id"),rs.getInt("quantity")));
            for(int i=0;i<cart.size();i++){
                cart.get(i).setProduct(productRepos.getid(cart.get(i).getProduct().getProduct_id()));
            }
            return cart;
        }
        catch(Exception e){
           e.printStackTrace();
        }
        return null;
    }
    public boolean add(int product_id,int person_id){
        try{
            int count=jdbcTemplate.queryForObject("Select count(*) from Cart Where product_id="+ product_id+" and customer_id="+person_id,Integer.class);
            if (count==0){
                jdbcTemplate.update("Insert into cart(customer_id,product_id,quantity) values(?,?,?)"
                ,person_id, product_id, 1);
                return true;
            }
            else{
                int quantity=jdbcTemplate.queryForObject("Select quantity from cart where customer_id=" +person_id+" and product_id="+product_id,Integer.class);
                quantity++;
                jdbcTemplate.update("Update cart set quantity= ? where product_id=? and customer_id=?",quantity,product_id,person_id);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean dec(Cart cart){
        try{
            if(cart.getQuantity()>0){
                jdbcTemplate.update("Update cart set quantity=? Where Customer_id=? and product_id=?",cart.getQuantity(),cart.getCustomer_id(),cart.getProduct().getProduct_id());
            }
            else{
                jdbcTemplate.update("Delete cart Where Customer_id=? and product_id=?",cart.getCustomer_id(),cart.getProduct().getProduct_id());
            }
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return  false;
    }
    public boolean Delete(List<Cart> cartList){
        try{
            for(int i=0;i<cartList.size();i++){
                jdbcTemplate.update("Delete from Cart Where customer_id=? and product_id=?",cartList.get(i).getCustomer_id(),cartList.get(i).getProduct().getProduct_id());
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
