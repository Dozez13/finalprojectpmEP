package com.example.finalprojectpm.web;


import com.example.finalprojectpm.db.entity.*;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.service.*;
import com.example.finalprojectpm.db.util.ImageUtil;
import com.example.finalprojectpm.web.model.ToggleButton;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@SessionAttributes({"Login","userType","userId"})
public class MainController {
      private TaxiServiceOrder taxiServiceOrder;
      private TaxiServiceCar taxiServiceCar;
      private TaxiServiceUser taxiServiceUser;
      private TaxiServiceProfile taxiServiceProfile;
      private TaxiServiceRegistration taxiServiceRegistration;
      private TaxiServiceCarCategory taxiServiceCarCategory;
      private TaxiServiceMakeOrder taxiServiceMakeOrder;
    @Autowired
    public MainController(TaxiServiceOrder taxiServiceOrder,
                          TaxiServiceCar taxiServiceCar,
                          TaxiServiceUser taxiServiceUser,
                          TaxiServiceProfile taxiServiceProfile,
                          TaxiServiceRegistration taxiServiceRegistration,
                          TaxiServiceCarCategory taxiServiceCarCategory,
                          TaxiServiceMakeOrder taxiServiceMakeOrder) {
        this.taxiServiceOrder = taxiServiceOrder;
        this.taxiServiceCar = taxiServiceCar;
        this.taxiServiceUser = taxiServiceUser;
        this.taxiServiceProfile = taxiServiceProfile;
        this.taxiServiceRegistration = taxiServiceRegistration;
        this.taxiServiceCarCategory = taxiServiceCarCategory;
        this.taxiServiceMakeOrder = taxiServiceMakeOrder;
    }

    @GetMapping({"/index","/"})
    public ModelAndView homePage(){
//        LOGGER.info("HomeAction is invoked");
        ModelAndView modelAndView = new ModelAndView("index");
        List<CarCategory> carCategories = taxiServiceCarCategory.findExistingCarC();
//        LOGGER.debug("Get Existing CarCategories, number Is {}",carCategories.size());
        List<Car> cars = taxiServiceCar.findAllCars();
//        LOGGER.debug("Get All Cars , Car number Is {}",cars.size());
        List<Integer> counts = taxiServiceCar.findNumberCarByCat();
        modelAndView.addObject("cars",cars);
        modelAndView.addObject("categories",carCategories);
        modelAndView.addObject("counts",counts);
//        view.setView(request.getPathInfo());

        return modelAndView;
    }
    @GetMapping("/guest/registration")
    public String registrationPage(){
       return "registration";
    }
    @GetMapping("/user/order")
    public ModelAndView orderPage() throws JsonProcessingException {
//        LOGGER.info("OrderGAction is invoked");
        ModelAndView modelAndView = new ModelAndView("order");
        List<CarCategory> carCategories = taxiServiceCarCategory.findExistingCarC();
        //LOGGER.debug("Get Existing CarCategories, number Is {}",carCategories.size());
        List<ToggleButton> buttons = IntStream.range(0, carCategories.size())
                .mapToObj(i -> {
                    ToggleButton button = new ToggleButton();
                    button.setIcon("img:data:image/png;base64," + ImageUtil.getBase64String(ImageUtil.imageToByte(carCategories.get(i).getCarCategoryImage())));
                    button.setValue(carCategories.get(i).getCarCategoryName());
                    button.setSlot(i+1+"");
                    return button;
                })
                .collect(Collectors.toList());
        //LOGGER.debug("Creating List of Toggle Buttons");
        ObjectMapper mapper = new ObjectMapper();
        modelAndView.addObject("carCategories",carCategories);
        modelAndView.addObject("carCategoriesButtons",mapper.writeValueAsString(buttons));
        //view.setView(request.getPathInfo());
        return modelAndView;
    }
    @GetMapping("/admin/orders")
    public ModelAndView allOrdersPage(){
        //LOGGER.info("OrdersCountAction is invoked");
        //HttpServletRequest request = view.getRequest();
        ModelAndView modelAndView = new ModelAndView("orders");
        int count = taxiServiceOrder.orderCount();
        //LOGGER.info("Get Order count , count is {}",count);
        modelAndView.addObject("Count",count);
        //view.setView(request.getPathInfo());
        return modelAndView;
    }
    @GetMapping("/admin/ordersJson")
    public ModelAndView getOrders(@RequestParam String sortBy,
                          @RequestParam String filter,
                          @RequestParam boolean descending,
                          @RequestParam int startRow,
                          @RequestParam int rowsPerPage) throws JsonProcessingException {
        //LOGGER.info("OrdersGetAction is invoked");
          ModelAndView modelAndView = new ModelAndView("ordersJson");

        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> filters= null ;
        if(!filter.equals("{}")){
            filters = mapper.readValue(filter, new TypeReference<Map<String, String>>() {});
        }
        List<Order> getOrders = taxiServiceOrder.findFilSortOrders(filters,sortBy,descending,startRow,rowsPerPage);
       // LOGGER.info("Filtered and Sorted Order count is {}",getOrders.size());
        modelAndView.addObject("ordersJsonValues",mapper.writeValueAsString(getOrders));
       // view.setView(request.getPathInfo());
        return modelAndView;
    }
    @GetMapping("/user/profile")
    public ModelAndView profilePage(@SessionAttribute("Login") String login){
        //LOGGER.info("ProfileAction is invoked");
        ModelAndView modelAndView = new ModelAndView("profile");
        User user = taxiServiceUser.findUser(login);
       // LOGGER.info("User has id {}",user.getUserId());
        Profile profile = taxiServiceProfile.findProfile(user.getUserId());
        modelAndView.addObject("profileFirstName",profile.getUserFirstName());
       // LOGGER.info("User has profileFirstName : {}",profile.getUserFirstName());
        modelAndView.addObject("profileSurName",profile.getUserSurName());
        //LOGGER.info("User has profileSurName : {}",profile.getUserSurName());
        modelAndView.addObject("profileRegistrationDate",profile.getUserRegistrationDate().toString());
        //LOGGER.info("User has registration date : {}",profile.getUserRegistrationDate());
        modelAndView.addObject("profileAccountBalance",profile.getAccountBalance());
        //view.setView(request.getPathInfo());
        return modelAndView;
    }
    @GetMapping("/user/addMoney")
    public String addMoneyPage(){
        return "addMoney";
    }
    @GetMapping("/user/myOrders")
    public ModelAndView myOrdersPage(@RequestParam(value = "startRow",required = false) String startRowSTR,@RequestParam(value = "currentPage",required = false) String currentPageSTR,
                                     @SessionAttribute("userId") int userId){
        //LOGGER.info("MyOrders Action is invoked");
        //HttpServletRequest request = view.getRequest();
        ModelAndView modelAndView = new ModelAndView("myOrders");
        int startRow = 0;
        int rowsPerPage = 3;
        int currentPage = 1;
        String strStartRow;
        String strCurrentPage;
        if((strStartRow =startRowSTR)!=null){
            try{
                int temp = Integer.parseInt(strStartRow);
                startRow = Math.max(temp, 0);
            }catch (NumberFormatException e){
                //LOGGER.error(e.getMessage());
                throw new ApplicationEXContainer.ApplicationCantRecoverException(e.getMessage());
            }
        }
        if((strCurrentPage =currentPageSTR)!=null){
            try{
                int temp = Integer.parseInt(strCurrentPage);
                currentPage = temp>0?temp:1;
            }catch (NumberFormatException e){
                //LOGGER.error(e.getMessage());
                throw new ApplicationEXContainer.ApplicationCantRecoverException(e.getMessage());
            }
        }
        List<Order> orders = taxiServiceOrder.findOrdersByUser(userId,startRow,rowsPerPage);
        int orderCount = taxiServiceOrder.orderCountByUser(userId);
        int numOfPages = (int)(Math.ceil(orderCount/ (double) rowsPerPage));
        modelAndView.addObject("myOrders",orders);
        modelAndView.addObject("startRow",startRow);
        modelAndView.addObject("currentPage",currentPage);
        modelAndView.addObject("totalOrders",numOfPages);
       // view.setView(request.getPathInfo());
        return modelAndView;
    }
    @PostMapping("/guest/login")
    public ModelAndView loginIn(@RequestParam("login") String login,@RequestParam("psw") String password){
        //LOGGER.info("LoginAction is invoked");
               System.out.println(login+" "+password);
        ModelAndView modelAndView = new ModelAndView();

        User user = taxiServiceUser.findUser(login);
        if(user!=null&&taxiServiceUser.validateUser(login,password)) {
           // LOGGER.debug("Get Login from user, Login Is {}",login);
            //LOGGER.debug("Get Password from user, Password Is {}",psw);
            modelAndView.addObject("Login",login);
            modelAndView.addObject("userType",user.getUserType());
            modelAndView.addObject("userId",user.getUserId());
            modelAndView.setViewName("redirect:/index");
            //view.setView(request.getContextPath()+"/pages/index");
        } else{
            String error= "Your login or password is wrong";
            modelAndView.addObject("errorMessage",error);
            modelAndView.setViewName("redirect:/index?errorMessage="+error);
        }
        return modelAndView;
    }
    @PostMapping("/guest/doRegistration")
    public ModelAndView doRegistration(@RequestParam("firstName")String firstName,@RequestParam("surName")String surName,@RequestParam("login")String login,@RequestParam("Email") String email,
                                       @RequestParam("psw")String psw){
        //LOGGER.info("DoRegistrationAction is invoked");
        ModelAndView modelAndView = new ModelAndView("redirect:/index");
        //view.setView(request.getContextPath() + "/pages/index");
            taxiServiceRegistration.doRegistration(firstName,surName,login,email,psw);
          //  view.setView(request.getContextPath() + "/pages/guest/registration?registrationMessage=" + e.getMessage());

        return modelAndView;
    }
    @PostMapping("/user/logOut")
    public String logOut(HttpSession session, SessionStatus status){
        //LOGGER.info("LogOutAction is invoked");
        status.setComplete();
        if (session != null) {
           // LOGGER.info("Invalidated user {}",session.getAttribute("Login"));
            session.removeAttribute("Login");
            session.invalidate();
        }
        return "redirect:/index";
    }
    @PostMapping("/user/doOrder")
    public ModelAndView doOrder(@SessionAttribute("Login") String login,@RequestParam("userAddress") String userAddress,@RequestParam("userDestination")String userDestination,
                                @RequestParam("numOfPas") String[]stingNumbers,@RequestParam("categories")String[] categories){
        //LOGGER.info("OrderMAction is invoked");

        ModelAndView modelAndView = new ModelAndView();

        if(stingNumbers != null && stingNumbers.length > 0&&categories != null && categories.length > 0) {
            String message;

                message = taxiServiceMakeOrder.makeOrder(stingNumbers,categories,userAddress,userDestination,login);
                modelAndView.setViewName("redirect:/order?takenTime="+message);


                //LOGGER.error(e.getMessage());

        }
        return modelAndView;
    }
    @PostMapping("/user/addMoneyM")
    public ModelAndView addMoneyAction(@RequestParam("amountM") int amount,@SessionAttribute("Login") String login){

        ModelAndView modelAndView = new ModelAndView("redirect:/index");
        User user = taxiServiceUser.findUser(login);
        taxiServiceProfile.updateProfileAddBalance(user.getUserId(),amount);
        return modelAndView;

    }
}
