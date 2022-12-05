package com.example.qlhtt.Controller;


import com.example.qlhtt.Entity.Person;
import com.example.qlhtt.Entity.Product;
import com.example.qlhtt.Entity.UserLogin;
import com.example.qlhtt.Repos.PersonRepos;
import com.example.qlhtt.Repos.ProductRepos;
import com.example.qlhtt.Repos.UserLoginRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/Employee")
public class EmployeeController {

    @Autowired
    UserLoginRepos userLoginRepos;

    @Autowired
    PersonRepos personRepos;

    @Autowired
    ProductRepos productRepos;

    @RequestMapping()
    public  String home(){
        return "admin";
    }

    @RequestMapping("/info")
    public ModelAndView info(Principal principal){
        UserLogin userLogin=userLoginRepos.getUserName(principal.getName());
        Person person=personRepos.getbyid(userLogin.getPerson_id());
        ModelAndView mav= new ModelAndView("Employee");
        mav.addObject("person",person);
        return mav;
    }

    @RequestMapping("/product")
    public ModelAndView product(){
        ModelAndView mav=new ModelAndView("adminProduct");
        List<Product> product=productRepos.getall();
        mav.addObject("products",product);
        return mav;
    }
}
