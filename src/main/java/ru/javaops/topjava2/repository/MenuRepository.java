package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.javaops.topjava2.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends BaseRepository<Menu> {
    @Override
    int delete(int id);

    @Override
    @EntityGraph(attributePaths = {"restaurant", "dish"})
    List<Menu> findAll();

    @EntityGraph(attributePaths = {"restaurant", "dish"})
    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant LEFT JOIN FETCH m.dish WHERE m.id=:id")
    Optional<Menu> findByIdRestaurantAndDish(int id);

    @Query("SELECT DISTINCT m FROM Menu m JOIN FETCH m.restaurant r LEFT JOIN FETCH m.dish WHERE m.date=:date ORDER BY r.name")
    List<Menu> findAllByDate(LocalDate date);
}
