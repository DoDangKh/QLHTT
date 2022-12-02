package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.Cart;
import com.example.qlhtt.Entity.Product;
import com.example.qlhtt.Entity.UserLogin;
import com.example.qlhtt.Repos.CartRepos;
import com.example.qlhtt.Repos.PersonRepos;
import com.example.qlhtt.Repos.ProductRepos;
import com.example.qlhtt.Repos.UserLoginRepos;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();


    @Autowired
    CartRepos cartRepos;

    @Autowired
    UserLoginRepos userLoginRepos;

    @Autowired
    ProductRepos productRepos;

    @GetMapping("/all")
    public ModelAndView cart(){
        System.out.print("dddddddddddd "+SecurityContextHolder.getContext().getAuthentication().getName()+" dddddddddd");
        UserLogin userLogin=userLoginRepos.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Cart> cart=cartRepos.getbyID(userLogin.getPerson_id());
        int sum=0;
        for(int i=0;i<cart.size();i++){
            sum+=cart.get(i).getProduct().getPrice()*cart.get(i).getQuantity();
        }
        ModelAndView mav=new ModelAndView("cartitem");
        mav.addObject("sum",sum);
        mav.addObject("cartitem", cart);
        mav.addObject(cart);
        return mav;
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable int id, Model model){
        System.out.print("dddddddddddd "+SecurityContextHolder.getContext().getAuthentication().getName()+" dddddddddd");
        UserLogin userLogin=userLoginRepos.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        //productRepos.getid(id);
        String error=new String();
        if(productRepos.getid(id).getQuantity()>0) {
            Product product=productRepos.getid(id);
           product.setQuantity(product.getQuantity()-1);
            cartRepos.add(id, userLogin.getPerson_id());
        }
        else {
            error="Hết Hàng";

        }
        model.addAttribute("error",error);
        return "redirect:/cart/all";
    }
}
