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
    Optional<Restaurant> findById(Integer integer);

    @Override
    List<Restaurant> findAll();

    @Transactional
    @Query(nativeQuery = true, value = "SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m LEFT JOIN FETCH m.dishes d WHERE r.id=:id ORDER BY r.id")
    Restaurant findByIdMenus(int id);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT r FROM Restaurant r LEFT JOIN FETCH r.votes v LEFT JOIN FETCH v.user WHERE r.id=:id ORDER BY r.id")
    Restaurant findByIdVotes(int id);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.votes v LEFT JOIN FETCH v.user ORDER BY r.id")
    List<Restaurant> findAllVotes();
}
