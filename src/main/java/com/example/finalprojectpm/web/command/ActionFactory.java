package com.example.finalprojectpm.web.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Holder for all actions
 * @author Pavlo Manuilenko
 */
public class ActionFactory {
    private static ActionFactory instance;
    private Map<String, Action> actions;
    public static synchronized ActionFactory getInstance() {
        if (instance == null) {
            instance =new ActionFactory();
        }
        return instance;
    }
    private ActionFactory() {
        actions = new HashMap<>();
        actions.put("GET/index", new HomeAction());
        actions.put("POST/guest/login", new LoginAction());
        actions.put("GET/guest/registration", new RegistrationAction());
        actions.put("POST/guest/doRegistration", new DoRegistrationAction());
        actions.put("POST/user/logOut", new LogOutAction());
        actions.put("GET/user/order", new OrderGAction());
        actions.put("POST/user/doOrder", new OrderMAction());
        actions.put("GET/admin/orders", new OrdersCountAction());
        actions.put("GET/admin/ordersJson", new OrdersGetAction());
        actions.put("noSuchPage", new NoSuchAction());
        actions.put("GET/user/profile",new ProfileAction());
        actions.put("GET/user/addMoney",new AddMoneyAction());
        actions.put("POST/user/addMoneyM",new AddMoneyMAction());
        actions.put("GET/user/myOrders",new MyOrders());
    }
    public Action getAction(HttpServletRequest request) {
        Action action;
        if((action = actions.get(request.getMethod() + request.getPathInfo()))!=null){
            return action;
        }
        return actions.get("noSuchPage");
    }
}

