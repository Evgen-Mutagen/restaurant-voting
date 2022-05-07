package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Override
    int delete(int id);

    @Override
    List<Restaurant> findAll();


    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.votes v LEFT JOIN FETCH v.user ORDER BY r.id")
    List<Restaurant> findAllVotes();
}
