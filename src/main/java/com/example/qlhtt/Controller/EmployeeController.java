package com.example.qlhtt.Controller;


import com.example.qlhtt.Entity.*;
import com.example.qlhtt.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.*;

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
    StatisticRepos statisticRepos;

    @Autowired
    CustomerUserRepos customerUserRepos;

    @Autowired
    StaffRepos staffRepos;


    @Autowired
    PasswordEncoder passwordEncoder;


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
            mav.addObject("success","C???p nh???t th??ng tin th??nh c??ng!");
        }else{
            mav.addObject("error","C???p nh???t th??ng tin th???t b???i!");
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
        typeList.add(0,new Type(Long.parseLong("-1"), "T???t c???"));
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
        typeList.add(0,new Type(Long.parseLong("-1"), "T???t c???"));
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
            typeList.add(0,new Type(Long.parseLong("-1"), "T???t c???"));
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
        typeList.add(0,new Type(Long.parseLong("-1"), "T???t c???"));
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
            redirectAttributes.addFlashAttribute("error", "T??n s???n ph???m kh??ng ???????c b??? tr???ng!");
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
            redirectAttributes.addFlashAttribute("error", "Vui l??ng th??m h??nh ???nh s???n ph???m!");
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
                    redirectAttributes.addFlashAttribute("success", "Th??m s???n ph???m th??nh c??ng");
                }else{
                    redirectAttributes.addFlashAttribute("error", "Th??m s???n ph???m th???t b???i");
                }

            }
            else {
                productRepos.update(product);
                redirectAttributes.addFlashAttribute("success", "C???p nh???t s???n ph???m th??nh c??ng");
            }
        }
        catch(Exception e){
            redirectAttributes.addFlashAttribute("error", "L???i khi th??m s???n ph???m");
            e.printStackTrace();
        }

        return "redirect:/Employee/product/page/0";
    }


    @RequestMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        System.out.println("xoa product "+id);
        if(productRepos.delete(id)){
            redirectAttributes.addFlashAttribute("success", "X??a s???n ph???m th??nh c??ng");
        }else{
            redirectAttributes.addFlashAttribute("error", "X??a s???n ph???m th???t b???i (V?? s???n ph???m c?? t???n t???i trong gi??? h??ng)");
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
            mav.addObject("success", "C???p nh???t lo???i "+type.getName()+" th??nh c??ng!");
        }else{
            mav=new ModelAndView( "adminAddProductType");
            mav.addObject("status", "update");
            mav.addObject("type", type);
            mav.addObject("idUpdate", type.getType_id());
            mav.addObject("error", "Lo???i "+type.getName()+" ???? t???n t???i");
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
            mav.addObject("success", "Th??m lo???i "+type.getName()+" th??nh c??ng!");
        }else{
            mav=new ModelAndView( "adminAddProductType");
            mav.addObject("error", "Lo???i "+type.getName()+" ???? t???n t???i");
        }
        mav.addObject("status", "new");
        return mav;
    }

    @RequestMapping("/type/delete/{id}")
    public ModelAndView deleteType(@PathVariable("id") int id){
        ModelAndView mav=new ModelAndView("adminProductType");
        String name = typeRepos.getid(id).getName();
        if(typeRepos.delete(id)){
            mav.addObject("success", "X??a lo???i " +name +" th??nh c??ng!");
        }else{
            mav.addObject("error","Kh??ng th??? x??a lo???i "+ name +" (V?? c?? m???t s??? s???n ph???m ??ang thu???c lo???i n??y)");
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
        List<CustomerOrder> customerOrders=orderRepos.getall();
        Person person=new Person();
        for(int i=0;i<customerOrders.size();i++){
            if(customerOrders.get(i).getOrder_id()==id){
               person=personRepos.getbyid(customerOrders.get(i).getCustomer_id());
                break;
            }
        }
        System.out.println(order.get(0).getCustomer_id());

//        System.out.println(person.getName());
        mav.addObject("person",person);
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
            mav.addObject("error", "H??? v?? t??n kh??ng ???????c b??? tr???ng");
        }else if(person.getIdentity_card().equals("")){
            mav.addObject("error", "CMND/CCCD kh??ng ???????c b??? tr???ng");
        }else if(person.getPhone_num().equals("")){
            mav.addObject("error", "S??? ??i???n tho???i kh??ng ???????c b??? tr???ng");

        }else {
            if (personRepos.update(person)) {
                mav.addObject("success", "C???p nh???t th??ng tin th??nh c??ng!");
            } else {
                mav.addObject("error", "C???p nh???t th??ng tin th???t b???i!");
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
                    mav.addObject("success", "X??a kh??ch h??ng th??nh c??ng!");
                }
            }
        }else{
            mav.addObject("error", "Kh??ng th??? x??a ( V?? c?? t???n t???i ????n h??ng thu???c kh??ch h??ng n??y)");//idUpdate
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
                mav.addObject("success", "C???p nh???t kh??ch h??ng th??nh c??ng!");
            }
        }else{
            mav.addObject("error", "L???i khi update");//idUpdate
            mav.addObject("fail",false);
        }

        List<Person> personList=personRepos.getCustomer();
        mav.addObject("persons",personList);
        return mav;
    }
//    @RequestMapping("/statistics")
//    public ModelAndView statistic(){
//        ModelAndView mav= new ModelAndView("statistics");
//
//        List<String> years = new ArrayList<>();
//        years=statisticRepos.loadyear();
//
//        ArrayList<Integer> data = new ArrayList<>();
//        data.add(1);
//        data.add(2);
//        data.add(3);
//        data.add(4);
//        data.add(5);
//        data.add(6);
//        data.add(7);
//        data.add(8);
//        data.add(9);
//        data.add(10);
//        data.add(11);
//        data.add(12);
//
//
//        mav.addObject("years", years);
//        mav.addObject("selected_id", new Date().getYear());
//        mav.addObject("data",data);
//
//        return mav;
//    }
    @GetMapping("/statistics/{year}")
    public ModelAndView getYearByStatictis(@PathVariable("year") String year) {
        ModelAndView mav= new ModelAndView("statistics");

        System.out.println("year " + year);
        List<String> years = new ArrayList<>();
        years=statisticRepos.loadyear();


        ArrayList<Integer> data = new ArrayList<>();
        for(int i=1;i<13;i++){
            data.add(statisticRepos.loadincome(Integer.parseInt(year),i));
        }

        mav.addObject("years", years);
        mav.addObject("selected_id", year);
        mav.addObject("data",data);
        return mav;
    }
        @RequestMapping("/list")
    public ModelAndView Employeelist(Model model){
        ModelAndView mav=new ModelAndView("adminEmployeelist");
        List<Person>listperson=personRepos.getStaff();
        mav.addObject("success", model.getAttribute("success"));
            mav.addObject("error", model.getAttribute("error"));
        mav.addObject("persons",listperson);

        return mav;
    }
    @RequestMapping("/list/add")
    public ModelAndView Employeeadd(){
        ModelAndView mav =new ModelAndView("adminadd");
        Person person = new Person();
        person.setGender("Nam");
        mav.addObject("person", person);
        mav.addObject("account", new UserLogin());
        mav.addObject("staff", new Staff());
        mav.addObject("btnName", "Th??m");
        return mav;
    }


    @PostMapping("/list/add/update")
    public String saveEmployee(@ModelAttribute("person") Person person, @ModelAttribute("account") UserLogin userLogin,@ModelAttribute("staff") Staff staff, Model model){
        System.out.println("\nThem nhan vien\n" + staff.getSalary());

        if (person.getId()==0){
            String error = personRepos.checkUser(person, "add");
            boolean ktAdd = true;
            if(!error.equals("")){
                ktAdd = false;
            }
            else if(!userLoginRepos.checkAccount(userLogin).equals("")){
                error = userLoginRepos.checkAccount(userLogin);
                ktAdd = false;
            }
            if(ktAdd==false){
                model.addAttribute("error", error);
                model.addAttribute("btnName", "Th??m");
                return "adminadd";
            }

            if (userLoginRepos.CheckUserName(userLogin.getUsername()) == 0) {
                personRepos.insertPerson(person);
                Person temp = personRepos.getbyidcard(person.getIdentity_card());
                userLogin.setPerson_id(temp.getId());
                userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));

                userLoginRepos.saveEmployee(userLogin, staff);
                model.addAttribute("success", "Th??m nh??n vi??n th??nh c??ng!");
                System.out.println("\nTh??m th??nh c??ng");

                List<Person>listperson=personRepos.getStaff();

                model.addAttribute("persons",listperson);

                return "redirect:/Employee/list";
            } else {
                model.addAttribute("btnName", "Th??m");
                model.addAttribute("error", "Username ???? t???n t???i t??i kho???n!");
                return "adminadd";
            }
        }
        else{
            String error = personRepos.checkUser(person, "update");
            boolean ktUpdate = true;
            if(!error.equals("")){
                ktUpdate=false;
            }
            else if(!userLoginRepos.checkAccount(userLogin).equals("")){
                error = userLoginRepos.checkAccount(userLogin);
                ktUpdate=false;
            }
            else if (userLoginRepos.CheckUserName(userLogin.getUsername()) > 1) {
                error="Username ???? t???n t???i t??i kho???n!";
                ktUpdate=false;
            }
            if(ktUpdate==false){
                model.addAttribute("error", error);
                model.addAttribute("btnName", "C???p nh???t");
                return "adminadd";
            }
                if(personRepos.update(person)&& userLoginRepos.updateUser(userLogin,1)){
                    if(staffRepos.updateSalary(staff)) {
                        model.addAttribute("success", "C???p nh???t th??ng tin nh??n vi??n th??nh c??ng!");
                        List<Person> listperson = personRepos.getStaff();

                        model.addAttribute("persons", listperson);

                        return "redirect:/Employee/list";
                    }
                    else{
                        model.addAttribute("btnName", "C???p nh???t");
                        model.addAttribute("error","C???p nh???t th???t b???i!");
                        return "adminadd";
                    }
            }
            else{
                model.addAttribute("btnName", "C???p nh???t");
                model.addAttribute("error","C???p nh???t th???t b???i!");
                return "adminadd";
            }
        }
    }
    @RequestMapping("/list/{id}")
    public ModelAndView  detailEmployee(@PathVariable("id") int id){
        ModelAndView mav =new ModelAndView("adminadd");
        Person person=personRepos.getbyid(id);
        UserLogin userLogin=userLoginRepos.getid(id);
        Staff staff = staffRepos.getStaffById(id);

        System.out.println("luong "+ staff.getSalary());

        mav.addObject("person",person);
        mav.addObject("account",userLogin);
        mav.addObject("staff",staff);
        mav.addObject("btnName", "C???p nh???t");
        return mav;
    }
    @RequestMapping("/list/delete/{id}")
    public String deleteEmployee(@PathVariable("id") int id,Model model){
        System.out.println("id xoa " +id );
        boolean kt = false;
        if(customerUserRepos.deletestaff(id)){
            if(userLoginRepos.delete(id)){
                if(personRepos.delete(id)){
//                    model.addAttribute("fail",true);
                    model.addAttribute("success", "X??a nh??n vi??n th??nh c??ng");
                    kt= true;
                    return "redirect:/Employee/list";
                }
            }
        }
        if(kt==false){
            model.addAttribute("error", "Kh??ng th??? x??a nh??n vi??n n??y!");//idUpdate
            model.addAttribute("fail",true);
            model.addAttribute("idUpdate",id);
            return "redirect:/Employee/list";
        }
        return "redirect:/Employee/list";
    }
}
