package com.example.qlhtt.Controller;


import com.example.qlhtt.Entity.*;
import com.example.qlhtt.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    TypeRepos typeRepos;


    @Autowired
    OrderRepos orderRepos;



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

    @RequestMapping("/type")
    public ModelAndView type(){
        ModelAndView mav= new ModelAndView("adminProductType");
        List<Type> typeList=typeRepos.getall();
        mav.addObject("types", typeList);
        return mav;
    }

    @RequestMapping("/order")
    public ModelAndView order(){
        ModelAndView mav=new ModelAndView("adminOrder");
        List<CustomerOrder> customerOrders=orderRepos.getall();
        mav.addObject("orders",customerOrders);
        return mav;
    }

    @RequestMapping("/customer")
    public ModelAndView customer(){
        ModelAndView mav=new ModelAndView("adminCustomer");
        List<Person> personList=personRepos.getCustomer();
        System.out.print("xxxxxxxxxxxxxxxxxxxxx "+personList.size()+" xxxxxxxxxxxxxxx");
        mav.addObject("persons",personList);
        return mav;
    }
}
