package com.example.finalprojectpm.web.listener;

import com.example.finalprojectpm.db.DAOFactory;
import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;
import com.example.finalprojectpm.db.util.QueriesUtil;
import com.example.finalprojectpm.web.command.ActionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Application context listener
 * @author Pavlo Manuilenko
 */
@WebListener
public class ApplicationListener implements ServletContextListener  {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationListener.class);

    /**
     * @param sce Servlet context where the attribute will be setted
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.debug("Context initialization is started");
        ServletContext ctx = sce.getServletContext();
        DAOFactory dao = DAOFactory.getDAOFactory(1);
        actionsInit(ctx);
        dataSourceInit(ctx);
        queriesInit(ctx);
        ctx.setAttribute("MySQLFactory", dao);
        Locale.setDefault(new Locale("ua"));
        LOGGER.debug("Context initialization is ended");
    }

    /**
     * Initializes actions holder
     * @param context Servlet context where the attribute will be setted
     */
    public void actionsInit(ServletContext context){
        ActionFactory actionFactory = ActionFactory.getInstance();
        context.setAttribute("actionFactory",actionFactory);
    }

    /**
     * Initializes pool connection
     * @param context Servlet context where the attribute will be setted
     */
    public void dataSourceInit(ServletContext context){
        try {
            MySQLDAOFactory.dataSourceInit();
        } catch (NamingException e) {
            LOGGER.error(e);
           throw new RuntimeException(e.getMessage(),e);
        }
    }

    /**
     * Loads queries from property file
     * @param context Servlet context where the attribute will be setted
     */
    public void queriesInit(ServletContext context){
        try {
            QueriesUtil.getQueries();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}