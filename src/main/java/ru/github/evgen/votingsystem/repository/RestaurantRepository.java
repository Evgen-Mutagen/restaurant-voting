package ru.github.evgen.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.github.evgen.votingsystem.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Override
    int delete(int id);

    @Override
    List<Restaurant> findAll();

    @Query("select r from Restaurant r where r.id =:id")
    Optional<Restaurant> findByRestId(Integer id);
}
