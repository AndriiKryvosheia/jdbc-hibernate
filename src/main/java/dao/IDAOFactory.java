package dao;

/**
 * Created by Asus on 31.01.2018.
 */
public interface IDAOFactory {

    IDAO getCarDAO();

    IDAO getClientDAO();


}
