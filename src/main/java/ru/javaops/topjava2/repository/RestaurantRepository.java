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

    @Transactional
    Restaurant save(Restaurant restaurant);

    @Override
    Optional<Restaurant> findById(Integer integer);

    @Override
    List<Restaurant> findAll();

    @Transactional
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m LEFT JOIN FETCH m.dishes d WHERE r.id=:id ORDER BY r.name")
    Restaurant findByIdMenus(int id);

    @Transactional
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.votes v LEFT JOIN FETCH v.user WHERE r.id=:id ORDER BY r.name")
    Restaurant findByIdVotes(int id);

    @Transactional
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.votes v LEFT JOIN FETCH v.user ORDER BY r.name")
    List<Restaurant> findAllVotes();


}
