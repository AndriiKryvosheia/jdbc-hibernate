import dao.IDAO;
import dao.DAOFactory;
import dao.IDAOFactory;
import entity.Car;
import entity.Client;

import java.util.List;
import java.util.Optional;

/**
 * Created by Asus on 31.01.2018.
 */
public class Main {
    private final static IDAOFactory factory = DAOFactory.getInstance();
    private final static IDAO carDAO = factory.getCarDAO();
    private final static IDAO clientDAO = factory.getClientDAO();

    public static void main(String[] args) {
//        Car car = new Car("Tesla", "model X", 100000);
//        carDAO.add(car);
//
//        List<Car> carList = carDAO.getAll();
//        System.out.println(carList);
//
//        Optional<Car> optional = carDAO.get(2);
//        optional.ifPresent(System.out::println);
//
//        Car newCar = new Car("Lada", "SYidan", 1111111);
//        carDAO.update(2, newCar);
//
//        carDAO.remove(1);

        Client client = new Client("Andrey", 35, "096-34346546");
        clientDAO.add(client);

        List<Client> clients = clientDAO.getAll();
        System.out.println(clients);

        Optional<Client> optional = clientDAO.get(2);
        optional.ifPresent(System.out::println);

        Client newClient = new Client("Vasya", 31, "097-56867687");
        clientDAO.update(2, newClient);

        clientDAO.remove(1);
    }

}
