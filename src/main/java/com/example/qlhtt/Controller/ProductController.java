package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.Product;
import com.example.qlhtt.Repos.ProductRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepos productRepos;

    @GetMapping("/all")
    public ResponseEntity<?> getall(){
        List<Product> products= productRepos.getall();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ModelAndView ProductDetail(@PathVariable int id){
        Product product= productRepos.getid(id);
        ModelAndView mav=new ModelAndView("productinfo");
        mav.addObject("p",product);
        return mav;
    }

}
