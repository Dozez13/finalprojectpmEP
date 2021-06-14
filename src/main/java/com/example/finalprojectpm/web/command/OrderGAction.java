package com.example.finalprojectpm.web.command;


import com.example.finalprojectpm.db.CarCategoryDao;
import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.entity.CarCategory;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.service.TaxiServiceCarCategory;
import com.example.finalprojectpm.db.util.ImageUtil;
import com.example.finalprojectpm.web.model.ToggleButton;
import com.example.finalprojectpm.web.view.View;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Order Action that add all needed information to request and forward to registration
 * @author Pavlo Manuilenko
 */
public class OrderGAction implements Action {
    private static final Logger LOGGER = LogManager.getLogger(OrderGAction.class);
    @Override
    public void execute(View view) throws Exception {
        LOGGER.info("OrderGAction is invoked");
        HttpServletRequest request = view.getRequest();
        DAOFactory dao =(MySQLDAOFactory)request.getServletContext().getAttribute("MySQLFactory");
        CarCategoryDao categoryDao = dao.getCarCategoryDao();
        TaxiServiceCarCategory taxiServiceCarCategory = new TaxiServiceCarCategory(categoryDao);
        List<CarCategory> carCategories = taxiServiceCarCategory.findExistingCarC();
        LOGGER.debug("Get Existing CarCategories, number Is {}",carCategories.size());
        List<ToggleButton> buttons = IntStream.range(0, carCategories.size())
                .mapToObj(i -> {
                    ToggleButton button = new ToggleButton();
                    button.setIcon("img:data:image/png;base64," + ImageUtil.getBase64String(ImageUtil.imageToByte(carCategories.get(i).getCarCategoryImage())));
                    button.setValue(carCategories.get(i).getCarCategoryName());
                    button.setSlot(i+1+"");
                    return button;
                })
                .collect(Collectors.toList());
        LOGGER.debug("Creating List of Toggle Buttons");
        ObjectMapper mapper = new ObjectMapper();
        request.setAttribute("carCategories",carCategories);
        request.setAttribute("carCategoriesButtons",mapper.writeValueAsString(buttons));
        view.setView(request.getPathInfo());
    }
}
