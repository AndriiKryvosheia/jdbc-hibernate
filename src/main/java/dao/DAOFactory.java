package dao;

/**
 * Created by Asus on 31.01.2018.
 */
public class DAOFactory implements IDAOFactory {

    private static IDAOFactory factory;

    private DAOFactory() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized IDAOFactory getInstance() {
        if (factory == null) {
            factory = new DAOFactory();
        }

        return factory;
    }

    @Override
    public IDAO getCarDAO() {
        return new CarJDBCDao();
    }

    @Override
    public IDAO getClientDAO() {
        return new ClientJDBCDAO();
    }

}
