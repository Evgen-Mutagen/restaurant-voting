package ru.github.evgen.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.github.evgen.votingsystem.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.date=:date AND v.user.id =:id")
    Vote findByDateAndUser(LocalDate date, int id);

    @Transactional(readOnly = true)
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant r WHERE v.date=:date ORDER BY v.id")
    List<Vote> findByDate(LocalDate date);

    @Transactional(readOnly = true)
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant m WHERE v.user.id=:id ORDER BY v.id")
    List<Vote> findByUser(int id);
}
