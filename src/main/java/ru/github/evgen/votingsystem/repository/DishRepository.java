package ru.github.evgen.votingsystem.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.github.evgen.votingsystem.model.Dish;

import java.util.Optional;

@Repository
public interface DishRepository extends BaseRepository<Dish> {

    @Transactional(readOnly = true)
    @Override
    Optional<Dish> findById(Integer integer);
}
