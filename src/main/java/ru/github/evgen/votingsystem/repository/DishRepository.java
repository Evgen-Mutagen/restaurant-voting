package ru.github.evgen.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.github.evgen.votingsystem.model.Dish;

import java.util.Optional;

@Repository
public interface DishRepository extends BaseRepository<Dish> {
    @Override
    int delete(int id);

    @Query("SELECT d FROM Dish d WHERE d.id=:id")
    Optional<Dish> findByDishId(Integer id);
}
