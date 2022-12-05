package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.Person;
import com.example.qlhtt.Entity.UserLogin;
import com.example.qlhtt.Repos.PersonRepos;
import com.example.qlhtt.Repos.UserLoginRepos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.Authenticator;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class LoginController {
    //private static Logger logger=Logger.getLogger(LoginController.class);
    @Autowired
    UserLoginRepos userLoginRepos;

    @Autowired
    PersonRepos personRepos;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/login")
    public String login(){
        //logger.error(error);
        return "login";
    }
//    @RequestMapping("/logut")
//    public String logout(HttpServletRequest request, HttpServletResponse response){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication != null){
//            new SecurityContextLogoutHandler().logout(request,response,authentication);
//        }
//        return "redirect:/home";
//    }
    @RequestMapping("/register")
    public ModelAndView register(){
        ModelAndView mav=new ModelAndView("register");
        mav.addObject("person",new Person());
        mav.addObject("userLogin", new UserLogin());
        String date=new String();
        mav.addObject("date", date);
        return mav;
    }
    @PostMapping("Confirmregister")
    public String SaveUsers(Model model, @ModelAttribute("person") Person person, @ModelAttribute("userLogin") UserLogin userLogin, @ModelAttribute("date") String date) throws  Exception{
        //date=date.substring(0,10);
        //date.replace("-","/");

        if(userLoginRepos.CheckUserName(userLogin.getUsername())==0) {
            personRepos.insertPerson(person);
            Person temp=personRepos.getbyidcard(person.getIdentity_card());
            userLogin.setPerson_id(temp.getId());
            userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
            userLoginRepos.saveUser(userLogin);
            return "redirect:/home";
        }
        else{
            String error="Dang Ky That Bai";
            model.addAttribute("error",error);
            return "register";
        }
    }
    @RequestMapping("/LoginHandel")
    public String LoginHandel(HttpServletRequest request){
        System.out.print(request.getUserPrincipal()+"RRRRRRRRRRRRRRRRRRRRRRRRRRR");
        if(request.isUserInRole("ROLE_CUSTOMER")){
            return"redirect:/home";
        }
        if(request.isUserInRole("ROLE_EMPLOYEE")){
            return"redirect:/Employee";
        }
        return null;
    }
}
