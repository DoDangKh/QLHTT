package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.Person;
import com.example.qlhtt.Entity.UserLogin;
import com.example.qlhtt.Repos.PersonRepos;
import com.example.qlhtt.Repos.UserLoginRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.stream.LongStream;

@Controller
public class LoginController {
    //private static Logger logger=Logger.getLogger(LoginController.class);
    @Autowired
    UserLoginRepos userLoginRepos;

    @Autowired
    PersonRepos personRepos;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;


    @RequestMapping("/")
    public String defaultpage(){
        return "redirect:/home";
    }
    @RequestMapping("/login")
    public String login(){
//        logger.error(error);
        return "login";
    }
    @RequestMapping("/forgotpassword")
    public ModelAndView Forgotpassword(){
        ModelAndView mav=new ModelAndView("ForgotPassword");
        mav.addObject("username",new String());
        return mav;
    }
    @PostMapping("/sendemail")
    public String sendemail(@ModelAttribute("username") String username){
        SimpleMailMessage message= new SimpleMailMessage();
        Random random=new Random();
        int a=random.ints(1000000,9999999).findFirst().getAsInt();
        String str=Integer.toString(a);
        message.setFrom("n19dccn093@student.ptithcm.edu.vn");
        message.setTo(username);
        message.setText("Quen Mat Khau");
        message.setSubject("Mat Khau Moi La"+str);
        mailSender.send(message);
        UserLogin temp=userLoginRepos.getUserName(username);
        temp.setPassword(passwordEncoder.encode(str));

        userLoginRepos.updateUser(temp);
        return "redirect:/home";
    }
//    @RequestMapping("/logut")
//    public String logout(Http  ServletRequest request, HttpServletResponse response){
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
    @PostMapping("/Confirmregister")
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
        if(request.isUserInRole("ROLE_EMPLOYEE")||request.isUserInRole("ROLE_ADMIN")){
            return"redirect:/Employee/info";
        }
        return null;
    }
}
