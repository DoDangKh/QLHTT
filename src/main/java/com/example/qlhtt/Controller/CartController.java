package com.example.qlhtt.Controller;

import com.example.qlhtt.Entity.Cart;
import com.example.qlhtt.Entity.CustomerOrder;
import com.example.qlhtt.Entity.Product;
import com.example.qlhtt.Entity.UserLogin;
import com.example.qlhtt.Repos.*;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    OrderRepos orderRepos;

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
    public String add(@PathVariable int id, Model model, @ModelAttribute("quantity") String quantity){
        System.out.println("quảtity " + quantity);
        UserLogin userLogin=userLoginRepos.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        //productRepos.getid(id);
        String error=new String();
        if(productRepos.getid(id).getQuantity()>0) {
            Product product=productRepos.getid(id);
           product.setQuantity(product.getQuantity()-1);
           productRepos.update(product);
            cartRepos.add(id, userLogin.getPerson_id());
        }
        else {
            error="Hết Hàng";

        }
        model.addAttribute("error",error);
//        model.addAttribute("quantity",error);
        return "redirect:/cart/all";
    }
    @GetMapping("/dec/{id}")
    public String dec(@PathVariable int id, Model model){
        UserLogin userLogin=userLoginRepos.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Cart> cart=cartRepos.getbyID(userLogin.getPerson_id());
        for(int i=0;i<cart.size();i++){
            if(cart.get(i).getProduct().getProduct_id()==id){
                cart.get(i).setQuantity(cart.get(i).getQuantity()-1);
                Product product=productRepos.getid(id);
                product.setQuantity(product.getQuantity()+1);
                productRepos.update(product);
                cartRepos.dec(cart.get(i));
                break;
            }
        }
        return "redirect:/cart/all";
    }

    @RequestMapping("/checkout")
    public String checkout(RedirectAttributes redirectAttributes){
        UserLogin userLogin=userLoginRepos.getUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Cart> cart=cartRepos.getbyID(userLogin.getPerson_id());

        if(cart.size() <=0 ){
            redirectAttributes.addFlashAttribute("error", "Vui lòng thêm sản phẩm vào giỏ trước khi đặt hàng!");
            return "redirect:/cart/all";
        }

        if(orderRepos.checkout(cart)) {
            cartRepos.Delete(cart);

            List<CustomerOrder> orderList = orderRepos.getall();
            if (orderList.size() > 0) {
                CustomerOrder order = orderList.get(orderList.size() - 1);
                String redirect = "redirect:/users/order/" + order.getOrder_id();
                return redirect;
            }
        }
        redirectAttributes.addFlashAttribute("error", "Đặt hàng không thành công");
        return "redirect:/cart/all";
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

}
