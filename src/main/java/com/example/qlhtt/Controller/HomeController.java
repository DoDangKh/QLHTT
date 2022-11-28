package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.Person;
import com.example.qlhtt.Entity.Product;
import com.example.qlhtt.Entity.Role;
import com.example.qlhtt.Entity.Type;
import com.example.qlhtt.Repos.PersonRepos;
import com.example.qlhtt.Repos.ProductRepos;

import com.example.qlhtt.Repos.TypeRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ProductRepos productRepos;

    @Autowired
    private PersonRepos personRepos;

    @Autowired
    private TypeRepos typeRepos;

    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView mav=new ModelAndView("main");
        //List<Person> persons=personRepos.getall();
        List<Product> products=productRepos.getall();
        List<Type> types=typeRepos.getall();
        mav.addObject("p", products);
        mav.addObject("r",types);
        System.out.print(products.get(0).getName());
        return mav;
    }
}
