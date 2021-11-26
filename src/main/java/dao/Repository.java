package dao;

import java.util.List;

public interface Repository<E> {

    void add(E element);

    E get(long id);

    List<E> getAll();

    void update(long id, E element);

    void remove(long id);
}
