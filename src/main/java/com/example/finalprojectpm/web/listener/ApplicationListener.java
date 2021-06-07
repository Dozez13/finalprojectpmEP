package com.example.finalprojectpm.web.listener;

import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.util.QueriesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import java.sql.SQLException;
import java.util.Locale;

@WebListener
public class ApplicationListener implements ServletContextListener  {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        DAOFactory dao = DAOFactory.getDAOFactory(1);
        try {
            MySQLDAOFactory.dataSourceInit();
            QueriesUtil.getQueries();
        } catch (SQLException | NamingException throwables) {
            LOGGER.error(throwables);
            ctx.setAttribute("Error",throwables.getMessage());
        }
        ctx.setAttribute("MySQLFactory",dao);
        Locale.setDefault(new Locale("ua"));
    }
}