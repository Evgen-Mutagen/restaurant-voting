package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.javaops.topjava2.model.Dish;

@Repository
public interface DishRepository extends BaseRepository<Dish> {
    @Override
    int delete(int id);

    @Query("SELECT d FROM Dish d WHERE d.id=:id")
    Dish findByDishId(Integer id);
}
