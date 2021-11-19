package dao;

import entities.Animal;
import java.util.List;

public interface AnimalRepository {

    void add(Animal animal);

    List<Animal> getAll();

    Animal getById(long id);

    void updateById(long id, Animal animal);

    void removeById(long id);
}
