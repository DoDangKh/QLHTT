package com.example.qlhtt.Controller;


import com.example.qlhtt.Entity.*;
import com.example.qlhtt.Repos.*;
import net.bytebuddy.matcher.StringMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/product/page/{p}")
    public ModelAndView product(@PathVariable("p")Optional<Integer> p){
        Pageable pageable= PageRequest.of(p.orElse(0),2) ;
        Page<Product> page=productRepos.getPage(pageable);
        ModelAndView mav=new ModelAndView("adminProduct");
        List<Product> product=productRepos.getall();
        mav.addObject("products",page);
        return mav;
    }
    @RequestMapping("/product/detail")
    public ModelAndView updateview(){
        ModelAndView mav=new ModelAndView("adminProductDetail");
        mav.addObject("product",new Product());
        return mav;
    }

    @PostMapping("/product/updateConfirm")
    public String update(@ModelAttribute("product") Product product, @RequestParam MultipartFile photo){

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

    @RequestMapping("/type/new")
    public ModelAndView newtype(){
        ModelAndView mav=new ModelAndView( "adminAddProductType");
        mav.addObject("type",new Type());
        return mav;
    }

    @PostMapping("/type/add")
    public String addtpye(@ModelAttribute("type") Type type){
        typeRepos.add(type);
        return "redirect:/Employee";
    }

    @RequestMapping("/order")
    public ModelAndView order(){
        ModelAndView mav=new ModelAndView("adminOrder");
        List<CustomerOrder> customerOrders=orderRepos.getall();
        mav.addObject("orders",customerOrders);
        return mav;
    }

    @RequestMapping("/order/{id}")
    public ModelAndView orderdetail(@PathVariable("id") int id){
        ModelAndView mav=new ModelAndView("adminOrderDetail");
        List<Cart> order= orderRepos.OrderDetail(id);
        mav.addObject("order",order);
        int sum=0;
        for(int i=0;i<order.size();i++){
            sum+=order.get(i).getQuantity()*order.get(i).getProduct().getPrice();
        }
        mav.addObject("id",String.valueOf(id));
        System.out.print(id+"cccccccccccccc");
        mav.addObject("sum",sum);
        return mav;

    }

    public class id{
        int id=100;
    }

    @RequestMapping("/order/check/{id}")
    public String checkorder(@PathVariable("id") int id){
        System.out.print(id+"xxxxxxxxxxxxxxxx");
        try {
            UserLogin user = userLoginRepos.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());

            orderRepos.checkOrder(id, user.getPerson_id());
            return "redirect:/Employee";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/Employee";
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
