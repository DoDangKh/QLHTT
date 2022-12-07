package com.example.qlhtt.Controller;


import com.example.qlhtt.Entity.*;
import com.example.qlhtt.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/Employee")
public class EmployeeController {

    @Autowired
    ServletContext application;

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
    public  String home(HttpSession session){
        List<Type> types=typeRepos.getall();
        session.setAttribute("type",types);
        return "admin";
    }

    @RequestMapping("/info")
    public ModelAndView info(Principal principal, HttpSession session){
        UserLogin userLogin=userLoginRepos.getUserName(principal.getName());
        Person person=personRepos.getbyid(userLogin.getPerson_id());
        ModelAndView mav= new ModelAndView("Employee");
        //mav.addObject("person",person);
        session.setAttribute("id",person.getId());
        mav.addObject("person",person);

        return mav;
    }

    @PostMapping("/info/update")
    public String update(HttpSession session,@ModelAttribute("person") Person person){
        //Person person= (Person)session.getAttribute("person");
        person.setId((int)session.getAttribute("id"));
        System.out.print(person.getId()+"asdasdsadsadsaad");
        personRepos.update(person);
        return "redirect:/Employee";

    }

    @RequestMapping("/product")
    public ModelAndView product(){
        ModelAndView mav=new ModelAndView("adminProduct");
        List<Product> product=productRepos.getall();
        mav.addObject("products",product);
        return mav;
    }

    @PostMapping("/product/update")
    public String update(@ModelAttribute("newproduct") Product product, @RequestParam MultipartFile photo){

        Path path= Paths.get("src/main/resources/static/public/images/");

        System.out.print(path);
        try{
            InputStream inputStream=photo.getInputStream();
            Files.copy(inputStream,path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            product.setImg(photo.getOriginalFilename());
            productRepos.add(product);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/Employee/product";
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
