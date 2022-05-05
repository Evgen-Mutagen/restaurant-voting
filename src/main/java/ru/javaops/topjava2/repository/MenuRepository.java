package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuRepository extends BaseRepository<Menu> {
    @Override
    int delete(int id);

    @Override
    List<Menu> findAll();

    @Query("SELECT m FROM Menu m JOIN FETCH m.dishes WHERE m.id =:id")
    Menu findByIdDish(int id);

    @Transactional
    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant LEFT JOIN FETCH m.dishes WHERE m.id=:id")
    Menu findByIdRestaurantAndDish(int id);

    @Transactional
    @Query("SELECT DISTINCT m FROM Menu m JOIN FETCH m.restaurant r LEFT JOIN FETCH m.dishes WHERE m.date=:date ORDER BY r.name")
    List<Menu> findAllByDate(LocalDate date);
}
