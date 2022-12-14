package com.example.qlhtt.Repos;

import com.example.qlhtt.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepos {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Product> getall(){
        try{
            List<Product> products= jdbcTemplate.query("Select * From Product", BeanPropertyRowMapper.newInstance(Product.class));
            return products;
        }
        catch(Exception e){
            System.out.print("xxxxxxxxxxxxxxxxxxxxx no xxxxxxxxxxxxxxxxxxxxxxxxxx");
            e.printStackTrace();
        }
        return null;
    }
    public Product getid(int id){
        try{
            Product product= jdbcTemplate.queryForObject("Select * From Product Where product_id="+ Integer.toString(id),(rs, rowNum) ->
                    new Product(rs.getInt(("product_id")), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("img"), rs.getString("describe"), rs.getInt("status"), rs.getInt("type_id")));
            return product;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Page<Product> getPage(Pageable page){
        try{
            List<Product> products=jdbcTemplate.query("Select * From Product Order by product_id OFFSET " + page.getOffset() + "ROWS Fetch NEXT " + page.getPageSize() +" ROWS ONLY"
            ,BeanPropertyRowMapper.newInstance(Product.class));
            int count=jdbcTemplate.queryForObject("SELECT count(*) from Product",Integer.class);
            return new PageImpl<Product>(products, page, count);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Page<Product> getPagebytpye(Pageable page,Long idtype){
        try{
            System.out.println(idtype);
            List<Product> products=jdbcTemplate.query("Select * From Product WHERE type_id="+idtype+" Order by product_id OFFSET " + page.getOffset() + "ROWS Fetch NEXT " + page.getPageSize() +" ROWS ONLY"
                    ,BeanPropertyRowMapper.newInstance(Product.class));
            int count=jdbcTemplate.queryForObject("SELECT count(*) from Product",Integer.class);
            return new PageImpl<Product>(products, page, count);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getProductsByType(Long idtype){
        List<Product> products=jdbcTemplate.query("Select * From Product WHERE type_id="+idtype+" Order by product_id",BeanPropertyRowMapper.newInstance(Product.class));
        return products;
    }

    public Page<Product> getPagebystring(Pageable page,String name){
        String name1 = name.toLowerCase();
        System.out.println(name1);
        try{
            List<Product> products=jdbcTemplate.query("Select * From Product WHERE name like '%"+name1+"%' Order by product_id OFFSET " + page.getOffset() + "ROWS Fetch NEXT " + page.getPageSize() +" ROWS ONLY"
                    ,BeanPropertyRowMapper.newInstance(Product.class));
            int count=jdbcTemplate.queryForObject("SELECT count(*) from Product",Integer.class);
            return new PageImpl<Product>(products, page, count);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List<Product> getProductsByName(String name){
        List<Product> products=jdbcTemplate.query("Select * From Product WHERE name like '%"+name+"%' Order by product_id",BeanPropertyRowMapper.newInstance(Product.class));
        return products;
    }
    public boolean update(Product product){
        try{
            jdbcTemplate.update("update Product set name =? ,quantity=?, price=?, img=?, describe=?,  type_id=? where product_id=? "
            , product.getName(), product.getQuantity(), product.getPrice(), product.getImg(), product.getDescribe(),  product.getType_id(), product.getProduct_id());
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean add(Product product){
        product.setName(product.getName().toLowerCase());
        try{
            jdbcTemplate.update("Insert into Product(name, quantity, price, img, describe, status,  type_id) Values(?,?,?,?,?,?,? )",product.getName(),product.getQuantity(),product.getPrice(),product.getImg(),product.getDescribe(), 1,product.getType_id());
            //jdbcTemplate.update("insert into product(name=")

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(int id){
        try{
            jdbcTemplate.update("Delete Product Where product_id=?",id);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
