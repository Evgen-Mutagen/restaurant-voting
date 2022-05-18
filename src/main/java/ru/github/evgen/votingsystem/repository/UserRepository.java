package ru.github.evgen.votingsystem.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.github.evgen.votingsystem.model.User;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Transactional(readOnly = true)
    Optional<User> getByEmail(String email);
}