package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.*;
import com.example.qlhtt.Repos.OrderRepos;
import com.example.qlhtt.Repos.PersonRepos;
import com.example.qlhtt.Repos.ProductRepos;
import com.example.qlhtt.Repos.UserLoginRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PersonRepos personRepos;
    @Autowired
    private ProductRepos productRepos;

    @Autowired
    private UserLoginRepos userLoginRepos;

    @Autowired
    private OrderRepos orderRepos;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/info")
    public ModelAndView info(Principal principal){

        ModelAndView mav= new ModelAndView("customer");
        UserLogin userLogin=userLoginRepos.getUserName(principal.getName());
        mav.addObject("person",personRepos.getbyid(userLogin.getPerson_id()));
        mav.addObject("account",userLogin);
        return mav;
    }
    @RequestMapping("/order")
    public ModelAndView order(Principal principal){
        ModelAndView mav= new ModelAndView("customerorder");
        UserLogin userLogin= userLoginRepos.getUserName(principal.getName());
        List<CustomerOrder> orders=orderRepos.getall();
        //System.out.print(orders.size()+"kkkkkkkkkkkkkkkk");
        for(int i=0;i<orders.size();i++){
            System.out.println(i);
            //System.out.print(orders.size()+"kkkkkkkkkkkkkkkk");
            if(orders.get(i).getCustomer_id()!=userLogin.getPerson_id()){
                orders.remove(i);
                i--;
                //System.out.print(orders.size()+"kkkkkkkkkkkkkkkk");
            }
            if(orders.size()==0) break;

        }
        mav.addObject("orders",orders);
        return mav;
    }
    @PostMapping("/info/update")
    public String Update(Model model,@ModelAttribute("person") Person person,@ModelAttribute("account") UserLogin userLogin,Principal principal){
        System.out.print(person.getName());
        person.setId(userLoginRepos.getUserName(principal.getName()).getPerson_id());
        System.out.print(person.getId());
        Person temp=personRepos.getbyidcard(person.getIdentity_card());
        userLogin.setPerson_id(temp.getId());
        if(userLogin.getPassword().equals(""))
            userLogin.setPassword(userLoginRepos.getUserName(userLogin.getUsername()).getPassword());
        else{
            userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
        }
        personRepos.update(person);
        userLoginRepos.updateUser(userLogin,2);
        return "redirect:/users/info";
    }
    @RequestMapping("/order/{id}")
    public ModelAndView orderdetail(@PathVariable("id") int id){
        ModelAndView mav=new ModelAndView("adminOrderDetail");
        List<CustomerOrder> customerOrders=orderRepos.getall();
        Person person=new Person();
        for(int i=0;i<customerOrders.size();i++){
            if(customerOrders.get(i).getOrder_id()==id){
                person=personRepos.getbyid(customerOrders.get(i).getCustomer_id());
                break;
            }
        }
        //System.out.println(order.get(0).getCustomer_id());

//        System.out.println(person.getName());
        mav.addObject("person",person);
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
//    @RequestMapping("/login")
//    public ModelAndView Login(){
//        ModelAndView mav= new ModelAndView("login");
//        return mav;
//    }
//    @PostMapping("/checklogin")
//    public ModelAndView checklogin(@RequestParam("username")String username,@RequestParam("password")String password, HttpSession session){
//        UserLogin userLogin=userLoginRepos.getUserName(username);
//        if(userLogin!=null){
//            if(userLogin.getPassword().equals(password)){
//                System.out.print("xxxxxxxxxxxxx true xxxxxxxxxxxxxxxxxxx");
//                ModelAndView mav = new ModelAndView("redirect:/home");
//                Person person=personRepos.getbyid(userLogin.getPerson_id());
//                session.setAttribute("person",person);
//                return mav;
//            }
//        }
//        System.out.print("xxxxxxxxxxx false xxxxxxxxxxxx");
//        ModelAndView mav=new ModelAndView("redirect:login");
//        mav.addObject("error","????ng Nh???p Th???t B???i");
//        return mav;
//    }

}
