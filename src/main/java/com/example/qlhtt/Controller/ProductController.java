package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.Product;
import com.example.qlhtt.Repos.ProductRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

//    @GetMapping("/all")
//    public ResponseEntity<?> getall(){
//        List<Product> products= productRepos.getall();
//        return ResponseEntity.ok(products);
//    }
    @GetMapping("/{id}")
    public ModelAndView ProductDetail(@PathVariable int id){
        Product product= productRepos.getid(id);
        ModelAndView mav=new ModelAndView("productinfo");
        mav.addObject("p",product);
        return mav;
    }
    @RequestMapping("/type/{id}/page/{p}")
    public ModelAndView all(@PathVariable("p") int p,@PathVariable("id") Long id){
        Pageable pageable= PageRequest.of(p,10);
        Page<Product> page=productRepos.getPagebytpye(pageable,id);
        ModelAndView mav=new ModelAndView("listproduct");
        mav.addObject(page);
        return mav;
    }
    @RequestMapping("/search/page/{p}")
    public ModelAndView search(@RequestParam("name")String name,@PathVariable("p") int p){
        Pageable pageable=PageRequest.of(p,10);
        Page<Product> page=productRepos.getPagebystring(pageable,name);
        ModelAndView mav=new ModelAndView("listproduct");
        mav.addObject(page);
        return mav;
    }
}
