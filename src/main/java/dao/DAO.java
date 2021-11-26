package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Field;
import java.util.List;

public class DAO<E> implements Repository<E> {
    private final Class entityClass;
    private final SessionFactory sessionFactory;

    public DAO(SessionFactory sessionFactory, Class entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    @Override
    public void add(E element) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(element);
        session.flush();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<E> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<E> list = (List<E>) session.createQuery("FROM " + entityClass.getName(), entityClass).list();
        session.flush();
        session.getTransaction().commit();
        session.close();

        return list;
    }

    @Override
    public E get(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        E element = (E) session.get(entityClass, id);
        session.flush();
        session.getTransaction().commit();
        session.close();

        return element;
    }

    @Override
    public void update(long id, E element) {
        if (sessionFactory.openSession().get(entityClass, id) != null) {
            Field field = null;
            try {
                field = entityClass.getDeclaredField("id");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            field.setAccessible(true);
            try {
                field.setLong(element, id);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(element);
            session.flush();
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void remove(long id) {
        E element = (E) sessionFactory.openSession().get(entityClass, id);
        if (element != null) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(element);
            session.flush();
            session.getTransaction().commit();
            session.close();
        }
    }
}
