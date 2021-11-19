import dao.AnimalDAO;
import dao.AnimalRepository;
import entities.Animal;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public SessionFactory sessionFactory;
    public AnimalRepository animalRepository;

    public Main() {
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        animalRepository = new AnimalDAO(sessionFactory);
    }

    public static void main(String[] args) {
        new Main().logic();
    }

    void logic() {
        Animal animal = new Animal(11, "Alex");
        animalRepository.add(animal);
        System.out.println(animalRepository.getAll());
        animalRepository.getById(1);
        animalRepository.removeById(1);
        sessionFactory.close();
    }
}
