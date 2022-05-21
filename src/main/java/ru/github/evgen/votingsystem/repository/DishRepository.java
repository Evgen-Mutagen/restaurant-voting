package ru.github.evgen.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.github.evgen.votingsystem.model.Dish;
import ru.github.evgen.votingsystem.model.Menu;
import ru.github.evgen.votingsystem.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends BaseRepository<Dish> {

    @Transactional(readOnly = true)
    @Override
    Optional<Dish> findById(Integer integer);

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT d FROM Dish d WHERE d.name=:name")
    List<Dish> findByName(String name);
}
