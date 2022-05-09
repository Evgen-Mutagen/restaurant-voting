package ru.javaops.topjava2.repository;

import org.springframework.stereotype.Repository;
import ru.javaops.topjava2.model.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Override
    int delete(int id);

    @Override
    List<Restaurant> findAll();

    @Override
    Restaurant getById(Integer integer);
}
