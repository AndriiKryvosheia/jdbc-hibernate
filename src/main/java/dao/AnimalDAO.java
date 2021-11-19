package dao;

import entities.Animal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class AnimalDAO implements AnimalRepository {
    private final SessionFactory sessionFactory;

    public AnimalDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Animal animal) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.save(animal);
        session.flush();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Animal> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Animal> list = session.createQuery("FROM Animal", Animal.class).list();

        session.getTransaction().commit();
        session.close();

        return list;
    }

    @Override
    public Animal getById(long id) {
        Animal animal = sessionFactory.openSession().get(Animal.class, id);

        return animal;
    }

    @Override
    public void updateById(long id, Animal animal) {
        if (sessionFactory.openSession().get(Animal.class, id) != null) {
            Session session = sessionFactory.openSession();

            session.beginTransaction();

            animal.setId(id);

            session.update(animal);
            session.flush();
            session.getTransaction().commit();

            session.close();
        }
    }

    @Override
    public void removeById(long id) {
        Animal animal = sessionFactory.openSession().get(Animal.class, id);

        if (animal != null) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.delete(animal);
            session.flush();
            session.getTransaction().commit();

            session.close();
        }
    }
}
