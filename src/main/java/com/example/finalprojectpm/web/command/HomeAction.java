package com.example.finalprojectpm.web.command;

import com.example.finalprojectpm.db.CarCategoryDao;
import com.example.finalprojectpm.db.CarDao;
import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.entity.Car;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceCar;
import com.example.finalprojectpm.db.service.TaxiServiceCarCategory;
import com.example.finalprojectpm.web.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class HomeAction implements Action{
    private static final Logger LOGGER = LogManager.getLogger(HomeAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("HomeAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory dao =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        CarDao carDao =  dao.getCarDao();
        CarCategoryDao categoryDao = dao.getCarCategoryDao();
        TaxiServiceCar taxiServiceCar = new TaxiServiceCar(carDao);
        TaxiServiceCarCategory taxiServiceCarCategory = new TaxiServiceCarCategory(categoryDao);
        List<CarCategory> carCategories = taxiServiceCarCategory.findExistingCarC();
        LOGGER.debug("Get Existing CarCategories, number Is {}",carCategories.size());
        List<Car> cars = taxiServiceCar.findAllCars();
        LOGGER.debug("Get All Cars , Car number Is {}",cars.size());
        List<Integer> counts = taxiServiceCar.findNumberCarByCat();
        request.setAttribute("cars",cars);
        request.setAttribute("categories",carCategories);
        request.setAttribute("counts",counts);
        view.setView(request.getPathInfo());
    }
}
