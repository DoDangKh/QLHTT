package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.UserLogin;
import com.example.qlhtt.Repos.UserLoginRepos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    //private static Logger logger=Logger.getLogger(LoginController.class);
    @Autowired
    UserLoginRepos userLoginRepos;

    @RequestMapping("/login")
    public String login(){
        //logger.error(error);
        return "login";
    }
}
