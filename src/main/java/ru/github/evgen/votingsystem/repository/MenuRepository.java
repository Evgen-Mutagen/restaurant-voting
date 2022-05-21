package ru.github.evgen.votingsystem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.github.evgen.votingsystem.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends BaseRepository<Menu> {

    @Transactional(readOnly = true)
    @EntityGraph(attributePaths = {"restaurant", "dish"})
    @Query("SELECT m FROM Menu m WHERE m.id=:id")
    Optional<Menu> findByIdWithRestaurantAndDish(int id);

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT m FROM Menu m JOIN FETCH m.restaurant r LEFT JOIN FETCH m.dish WHERE m.date=:date ORDER BY r.name")
    List<Menu> findAllByDate(LocalDate date);
}
