package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {
    @Override
    int delete(int id);

    @Query("SELECT v FROM Vote v WHERE v.date=:date AND v.user.id =:id")
    Vote findByDateAndUser(LocalDate date, int id);

    @Transactional
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant r WHERE v.date=:date ORDER BY v.id")
    List<Vote> findByDate(LocalDate date);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant m WHERE v.user.id=:id ORDER BY v.id")
    List<Vote> findByUser(int id);
}
