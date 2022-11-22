package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.Person;
import com.example.qlhtt.Entity.Product;
import com.example.qlhtt.Entity.UserLogin;
import com.example.qlhtt.Repos.PersonRepos;
import com.example.qlhtt.Repos.ProductRepos;
import com.example.qlhtt.Repos.UserLoginRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/all")
    public ResponseEntity<?> getListUser(){

            List<Product> products=productRepos.getall();
            return ResponseEntity.ok(products);
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
//        mav.addObject("error","Đăng Nhập Thất Bại");
//        return mav;
//    }
}
