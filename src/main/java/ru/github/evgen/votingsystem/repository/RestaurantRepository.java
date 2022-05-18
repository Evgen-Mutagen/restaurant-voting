package ru.github.evgen.votingsystem.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.github.evgen.votingsystem.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Transactional(readOnly = true)
    @Override
    List<Restaurant> findAll();

    @Transactional(readOnly = true)
    @Override
    Optional<Restaurant> findById(Integer integer);
}
