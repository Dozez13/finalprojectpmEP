package com.example.finalprojectpm.web;

import com.example.finalprojectpm.db.*;
import com.example.finalprojectpm.db.entity.*;
import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import com.example.finalprojectpm.db.service.*;
import com.example.finalprojectpm.db.util.ImageUtil;
import com.example.finalprojectpm.web.model.ToggleButton;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @GetMapping({"/index","/"})
    public ModelAndView homePage(){
//        LOGGER.info("HomeAction is invoked");
        ModelAndView modelAndView = new ModelAndView("index");
        DAOFactory dao =DAOFactory.getDAOFactory(1);
        CarDao carDao =  dao.getCarDao();
        CarCategoryDao categoryDao = dao.getCarCategoryDao();
        TaxiServiceCar taxiServiceCar = new TaxiServiceCar(carDao);
        TaxiServiceCarCategory taxiServiceCarCategory = new TaxiServiceCarCategory(categoryDao);
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
        DAOFactory dao =DAOFactory.getDAOFactory(1);
        CarCategoryDao categoryDao = dao.getCarCategoryDao();
        TaxiServiceCarCategory taxiServiceCarCategory = new TaxiServiceCarCategory(categoryDao);
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
        DAOFactory dao =DAOFactory.getDAOFactory(1);
        OrderDao daoOrder = dao.getOrderDao();
        TaxiServiceOrder taxiServiceOrder = new TaxiServiceOrder(daoOrder);
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
        DAOFactory dao =DAOFactory.getDAOFactory(1);
        ObjectMapper mapper = new ObjectMapper();
        OrderDao daoOrder = dao.getOrderDao();
        TaxiServiceOrder taxiServiceOrder = new TaxiServiceOrder(daoOrder);
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
        DAOFactory dao =DAOFactory.getDAOFactory(1);
        ProfileDao profileDao = dao.getProfileDao();
        UserDao userDao = dao.getUserDao();
        TaxiServiceProfile taxiServiceProfile = new TaxiServiceProfile(profileDao);
        TaxiServiceUser taxiServiceUser = new TaxiServiceUser(userDao);
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
    public ModelAndView myOrdersPage(@RequestParam("startRow") String startRowSTR,@RequestParam("currentPage") String currentPageSTR,
                                     @SessionAttribute("userId") int userId){
        //LOGGER.info("MyOrders Action is invoked");
        //HttpServletRequest request = view.getRequest();
        ModelAndView modelAndView = new ModelAndView("myOrders");
        DAOFactory dao =DAOFactory.getDAOFactory(1);
        OrderDao orderDao = dao.getOrderDao();
        TaxiServiceOrder taxiServiceOrder = new TaxiServiceOrder(orderDao);
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
        DAOFactory dao =DAOFactory.getDAOFactory(1);
        UserDao userDao = dao.getUserDao();
        TaxiServiceUser taxiServiceUser = new TaxiServiceUser(userDao);

        User user = taxiServiceUser.findUser(login);
        if(user!=null&&taxiServiceUser.validateUser(login,password)) {
           // LOGGER.debug("Get Login from user, Login Is {}",login);
            //LOGGER.debug("Get Password from user, Password Is {}",psw);
            modelAndView.addObject("Login",login);
            modelAndView.addObject("userType",user.getUserType());
            modelAndView.addObject("userId",user.getUserId());
            modelAndView.setViewName("index");

            //view.setView(request.getContextPath()+"/pages/index");
        } else{
            String error= "Your login or password is wrong";
            modelAndView.setViewName("index");
            modelAndView.addObject("errorMessage",error);
            //view.setView(request.getContextPath()+"/pages/index?errorMessage="+error);
        }
        return modelAndView;
    }
    @PostMapping("/guest/doRegistration")
    public ModelAndView doRegistration(@RequestParam("firstName")String firstName,@RequestParam("surName")String surName,@RequestParam("login")String login,@RequestParam("Email") String email,
                                       @RequestParam("psw")String psw){
        //LOGGER.info("DoRegistrationAction is invoked");

        ModelAndView modelAndView = new ModelAndView("index");
        DAOFactory factory =DAOFactory.getDAOFactory(1);
        UserDao userDao =  factory.getUserDao();
        ProfileDao profileDao = factory.getProfileDao();
        TaxiServiceRegistration taxiServiceRegistration = new TaxiServiceRegistration(userDao,profileDao);
        //view.setView(request.getContextPath() + "/pages/index");
            taxiServiceRegistration.doRegistration(firstName,surName,login,email,psw);
          //  view.setView(request.getContextPath() + "/pages/guest/registration?registrationMessage=" + e.getMessage());

        return modelAndView;
    }
    @PostMapping("/user/logOut")
    public String logOut(HttpSession session, SessionStatus status){
        //LOGGER.info("LogOutAction is invoked");
        System.out.println("I'm here");
        System.out.println(session.getAttribute("Login"));
        status.setComplete();
        System.out.println(session.getAttribute("Login"));
        if (session != null) {
           // LOGGER.info("Invalidated user {}",session.getAttribute("Login"));
            System.out.println(session.getAttribute("Login"));
            session.removeAttribute("Login");
            session.invalidate();

        }
        return "index";
    }
    @PostMapping("/user/doOrder")
    public ModelAndView doOrder(@SessionAttribute("Login") String login,@RequestParam("userAddress") String userAddress,@RequestParam("userDestination")String userDestination,
                                @RequestParam("numOfPas") String[]stingNumbers,@RequestParam("categories")String[] categories){
        //LOGGER.info("OrderMAction is invoked");

        ModelAndView modelAndView = new ModelAndView("order");
        DAOFactory daoFactory =DAOFactory.getDAOFactory(1);
        if(stingNumbers != null && stingNumbers.length > 0&&categories != null && categories.length > 0) {
            String message;
            TaxiServiceMakeOrder orderService = new TaxiServiceMakeOrder(daoFactory.getCarDao(),daoFactory.getOrderDao(),daoFactory.getCarCategoryDao(),daoFactory.getUserDao(),daoFactory.getProfileDao());

                message = orderService.makeOrder(stingNumbers,categories,userAddress,userDestination,login);
                modelAndView.addObject("takenTime",message);

                //LOGGER.error(e.getMessage());

        }
        return modelAndView;
    }
    @PostMapping("/user/addMoneyM")
    public ModelAndView addMoneyAction(@RequestParam("amountM") int amount,@SessionAttribute("Login") String login){

        ModelAndView modelAndView = new ModelAndView("index");
        DAOFactory factory =DAOFactory.getDAOFactory(1);
        UserDao userDao =  factory.getUserDao();
        ProfileDao profileDao = factory.getProfileDao();
        TaxiServiceUser taxiServiceUser = new TaxiServiceUser(userDao);
        TaxiServiceProfile taxiServiceProfile = new TaxiServiceProfile(profileDao);
        User user = taxiServiceUser.findUser(login);
        taxiServiceProfile.updateProfileAddBalance(user.getUserId(),amount);
        return modelAndView;

    }
}
