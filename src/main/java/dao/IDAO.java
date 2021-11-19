package dao;

import entity.Car;

import java.util.List;
import java.util.Optional;

/**
 * Created by Asus on 31.01.2018.
 */
public interface IDAO<E> {
    
    void add(E element);
    
    List<E> getAll();

    Optional<E> get(long id);
    
    void update(int id, E element);

    void remove(long Id);
    
}