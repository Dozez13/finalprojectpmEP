package com.example.finalprojectpm.db;

import com.example.finalprojectpm.db.mysql.MySQLDAOFactory;

/**
 * Class that is Abstract factory to all resource factories
 */
public abstract class DAOFactory {
    public static final int MYSQL = 1;
    public abstract CarDao getCarDao();
    public abstract UserDao getUserDao();
    public abstract OrderDao getOrderDao();
    public abstract CarCategoryDao getCarCategoryDao();
    public abstract ProfileDao getProfileDao();

    /**
     * Returns factory by its number
     * @param whichFactory number of factory
     * @return factory by number
     */
    public static DAOFactory getDAOFactory(
            int whichFactory) {
        switch (whichFactory) {
            case MYSQL:
                return new MySQLDAOFactory();
            default :
                return null;
        }
    }
}
