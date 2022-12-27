package com.example.qlhtt.Controller;


import com.example.qlhtt.Entity.*;
import com.example.qlhtt.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    CustomerUserRepos customerUserRepos;



    @RequestMapping()
    public  String home(HttpSession session){
        List<Type> types=typeRepos.getall();
//        session.setAttribute("type",types);
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
    public ModelAndView update(HttpSession session,@ModelAttribute("person") Person person){
        //Person person= (Person)session.getAttribute("person");
        person.setId((int)session.getAttribute("id"));
        System.out.print(person.getId()+"asdasdsadsadsaad");
        ModelAndView mav=new ModelAndView("Employee");
        if(personRepos.update(person)){
            System.out.println("update thanh cong");
            mav.addObject("success","Cập nhật thông tin thành công!");
        }else{
            mav.addObject("error","Cập nhật thông tin thất bại!");
        }
        return mav;
//        return "redirect:/Employee/info";
    }

    @GetMapping("/product/page/{p}")
    public ModelAndView product(@PathVariable("p") int p){
        System.out.println("Trang dang dung "+p);
        ModelAndView mav=new ModelAndView("adminProduct");
        List<Product> product=productRepos.getall();

        if( p <1)
            p=0;
        else if(p>(product.size()/3)) {
            p = product.size() / 3;
        }
        System.out.println("so page " + p);
        Pageable pageable= PageRequest.of(p,3);
        System.out.println("loi pageble");

        Page<Product> page=productRepos.getPage(pageable);
        mav.addObject("products",page);

        List<Type> typeList=typeRepos.getall();
        typeList.add(0,new Type(Long.parseLong("-1"), "Tất cả"));
        mav.addObject("types", typeList);



        mav.addObject("selected_id", -1);
        return mav;
    }
    @GetMapping("/product/search/page/{p}")
    public ModelAndView getProductByType(@PathVariable("p") int p,@RequestParam(name="name",required = false) String name) {
        System.out.println("string search "+ name);
        Pageable pageable=PageRequest.of(p,3);
        Page<Product> page=productRepos.getPagebystring(pageable,name);

        ModelAndView mav=new ModelAndView("adminProduct");
        mav.addObject("products",page);
        List<Type> typeList=typeRepos.getall();
        typeList.add(0,new Type(Long.parseLong("-1"), "Tất cả"));
        mav.addObject("types",typeList);
        mav.addObject("selected_id", -1);
        return mav;
    }

    @GetMapping("/product/type/{t}/page/{p}")
    public ModelAndView getProductByType(@PathVariable("p") int p,@PathVariable("t") Long t) {
        ModelAndView mav=new ModelAndView("adminProduct");
        if(t==-1){
            Pageable pageable= PageRequest.of(p,3);
            Page<Product> page=productRepos.getPage(pageable);
            mav.addObject("products",page);
            List<Type> typeList=typeRepos.getall();
            typeList.add(0,new Type(Long.parseLong("-1"), "Tất cả"));
            mav.addObject("types", typeList);
            mav.addObject("selected_id", -1);
            return mav;
        }


        Pageable pageable=PageRequest.of(p,3);
        Page<Product> page=productRepos.getPagebytpye(pageable,t);

        mav.addObject("products",page);
        List<Type> typeList=typeRepos.getall();
        int temp1= Math.toIntExact(t);
        System.out.println(typeList.size());
//        try {
//            for (int i=0;i< typeList.size();i++) {
//                if (typeList.get(i).getType_id() == t) {
//                    Type temp = typeList.get(i);
//                    typeList.remove(i);
//                    typeList.add(0, temp);
//                }
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        typeList.add(0,new Type(Long.parseLong("-1"), "Tất cả"));
        mav.addObject("selected_id",t);
        mav.addObject("types",typeList);
        return mav;
    }
    @RequestMapping("/product/detail")
    public ModelAndView updateview(@ModelAttribute("product") Product product){
        System.out.println("img ban dau " + product.getImg());
        ModelAndView mav=new ModelAndView("adminProductDetail");
        List<Type> listtype=new ArrayList<>();
        listtype=typeRepos.getall();
        mav.addObject("types",listtype);
        mav.addObject("product",product);
        return mav;
    }
    @RequestMapping("/product/{id}")
    public ModelAndView updateproduct(@PathVariable("id") int id){
        Product product=productRepos.getid(id);
        ModelAndView mav=new ModelAndView("adminProductDetail");
        mav.addObject("myfile",product.getImg());
        mav.addObject("product",product);
        List<Type> listtype=new ArrayList<>();
        listtype=typeRepos.getall();
        mav.addObject("types",listtype);
        return mav;
    }

    @PostMapping("/product/updateConfirm")
    public String update(@ModelAttribute("product") Product product, @RequestParam MultipartFile photo, RedirectAttributes redirectAttributes) throws IOException {
        System.out.println("product " + product.getName() + " " + product.getImg());
        Path path= Paths.get("src/main/resources/static/public/images/");
        if(product.getName().equals("")){
            redirectAttributes.addFlashAttribute("error", "Tên sản phẩm không được bỏ trống!");
            System.out.println("photto " +photo);
            if(!photo.getOriginalFilename().equals("")) {
                InputStream inputStream = photo.getInputStream();
                Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                product.setImg(photo.getOriginalFilename());
            }
            redirectAttributes.addFlashAttribute("product", product);
            return "redirect:/Employee/product/detail";
        }else if(photo.getOriginalFilename().equals("")) {
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addFlashAttribute("error", "Vui lòng thêm hình ảnh sản phẩm!");
            return "redirect:/Employee/product/detail";
        }


        try{
            if(!photo.getOriginalFilename().equals("")) {
                InputStream inputStream = photo.getInputStream();
                Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                product.setImg(photo.getOriginalFilename());
                System.out.println(1);
            }
            else{
                Product temp=productRepos.getid(product.getProduct_id());
                product.setImg(temp.getImg());
                System.out.println(2);
            }

            if(product.getProduct_id()==0)
            {
                if(productRepos.add(product)){
                    redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công");
                }else{
                    redirectAttributes.addFlashAttribute("error", "Thêm sản phẩm thất bại");
                }

            }
            else {
                productRepos.update(product);
                redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm thành công");
            }
        }
        catch(Exception e){
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm sản phẩm");
            e.printStackTrace();
        }

        return "redirect:/Employee/product/page/0";
    }


    @RequestMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        System.out.println("xoa product "+id);
        if(productRepos.delete(id)){
            redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm thành công");
        }else{
            redirectAttributes.addFlashAttribute("error", "Xóa sản phẩm thất bại (Vì sản phẩm có tồn tại trong giỏ hàng)");
        }
        return "redirect:/Employee/product/page/0";
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
        mav.addObject("status", "new");
        return mav;
    }
    @RequestMapping("/type/update/{id}")
    public ModelAndView updateType(@PathVariable("id") int id){
        System.out.println("update type " + id);
        Type type = typeRepos.getid(id);
        ModelAndView mav=new ModelAndView( "adminAddProductType");
        mav.addObject("status", "update");
        mav.addObject("type", type);
        mav.addObject("idUpdate", id);
        return mav;
    }
    @PostMapping("/type/update")
    public ModelAndView updateType1(@ModelAttribute("type") Type type){
        System.out.println("update type ne " + type.getType_id()+" "+ type.getName());
        ModelAndView mav =null;

        if(typeRepos.update(type)){
            mav = new ModelAndView("adminProductType");
            List<Type> typeList=typeRepos.getall();
            mav.addObject("types", typeList);
            mav.addObject("success", "Cập nhật loại "+type.getName()+" thành công!");
        }else{
            mav=new ModelAndView( "adminAddProductType");
            mav.addObject("status", "update");
            mav.addObject("type", type);
            mav.addObject("idUpdate", type.getType_id());
            mav.addObject("error", "Loại "+type.getName()+" đã tồn tại");
        }

        List<Type> typeList=typeRepos.getall();
        mav.addObject("types", typeList);
        return mav;
    }

    @PostMapping("/type/add")
    public ModelAndView addtpye(@ModelAttribute("type") Type type){
        ModelAndView mav = null;
        if(typeRepos.add(type)){
            mav = new ModelAndView("adminProductType");
            List<Type> typeList=typeRepos.getall();
            mav.addObject("types", typeList);
            mav.addObject("success", "Thêm loại "+type.getName()+" thành công!");
        }else{
            mav=new ModelAndView( "adminAddProductType");
            mav.addObject("error", "Loại "+type.getName()+" đã tồn tại");
        }

        return mav;
    }

    @RequestMapping("/type/delete/{id}")
    public ModelAndView deleteType(@PathVariable("id") int id){
        ModelAndView mav=new ModelAndView("adminProductType");
        String name = typeRepos.getid(id).getName();
        if(typeRepos.delete(id)){
            mav.addObject("success", "Xóa loại " +name +" thành công!");
        }else{
            mav.addObject("error","Không thể xóa loại "+ name +" (Vì có một số sản phẩm đang thuộc loại này)");
        }

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
            return "redirect:/Employee/order";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/Employee/order";
    }


    @RequestMapping("/customer")
    public ModelAndView customer(){
        ModelAndView mav=new ModelAndView("adminCustomer");
        List<Person> personList=personRepos.getCustomer();
        mav.addObject("persons",personList);
        return mav;
    }

    @RequestMapping("/customer/{id}")
    public ModelAndView customerdetail(@PathVariable("id") int id){
        ModelAndView mav =new ModelAndView("adminCustomerOrder");
        Person person=personRepos.getbyid(id);
        mav.addObject("person",person);
        return mav;
    }

    @PostMapping("/customer/update")
    public ModelAndView updateCustomer(@ModelAttribute("person") Person person){
        ModelAndView mav =new ModelAndView("adminCustomerOrder");
        if(person.getName().equals("")){
            mav.addObject("error", "Họ và tên không được bỏ trống");
        }else if(person.getIdentity_card().equals("")){
            mav.addObject("error", "CMND/CCCD không được bỏ trống");
        }else if(person.getPhone_num().equals("")){
            mav.addObject("error", "Số điện thoại không được bỏ trống");

        }else {
            if (personRepos.update(person)) {
                mav.addObject("success", "Cập nhật thông tin thành công!");
            } else {
                mav.addObject("error", "Cập nhật thông tin thất bại!");
            }
        }
        mav.addObject("person",person);
        return mav;
    }

    @RequestMapping("/customer/delete/{id}")
    public ModelAndView deleteCustomer(@PathVariable("id") String id){
        System.out.println("id xoa " +id );
        ModelAndView mav =new ModelAndView("adminCustomer");
        int idCustomner = Integer.parseInt(id);
        if(customerUserRepos.delete(idCustomner)){
            if(userLoginRepos.delete(idCustomner)){
                if(personRepos.delete(idCustomner)){
                    mav.addObject("success", "Xóa khách hàng thành công!");
                }
            }
        }else{
            mav.addObject("error", "Không thể xóa ( Vì có tồn tại đơn hàng thuộc khách hàng này)");//idUpdate
            mav.addObject("fail",true);
            mav.addObject("idUpdate",id);
        }

        List<Person> personList=personRepos.getCustomer();
        mav.addObject("persons",personList);
        return mav;
    }
    @RequestMapping("/customer/update/{id}")
    public ModelAndView updateCustomer(@PathVariable("id") int id){
        System.out.println("id update " +id );
        ModelAndView mav =new ModelAndView("adminCustomer");

        if(customerUserRepos.update(id)){
            if(userLoginRepos.updateStatus(id)) {
                mav.addObject("success", "Cập nhật khách hàng thành công!");
            }
        }else{
            mav.addObject("error", "Lỗi khi update");//idUpdate
            mav.addObject("fail",false);
        }

        List<Person> personList=personRepos.getCustomer();
        mav.addObject("persons",personList);
        return mav;
    }
    @RequestMapping("/statistic")
    public ModelAndView statistic(){
        ModelAndView mav= new ModelAndView("statistic");


        return mav;
    }
}
