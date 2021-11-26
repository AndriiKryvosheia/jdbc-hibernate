import dao.DAO;
import dao.Repository;
import entities.Client;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public SessionFactory sessionFactory;

    public Main() {
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
    }

    public static void main(String[] args) {
        new Main().logic();
    }

    void logic() {
        Repository<Client> clientRepository = new DAO<>(sessionFactory, Client.class);
        Client client = new Client("Andrey", 35, "096-34346546");
        clientRepository.add(client);
        List<Client> clients = clientRepository.getAll();
        System.out.println(clients);
        System.out.println(clientRepository.get(2));
        Client newClient = new Client("Vasya", 31, "097-56867687");
        clientRepository.update(2, newClient);
        System.out.println(clientRepository.get(2));
        clientRepository.remove(1);
    }
}
