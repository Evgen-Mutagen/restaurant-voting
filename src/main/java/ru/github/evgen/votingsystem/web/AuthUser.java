package ru.github.evgen.votingsystem.web;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import ru.github.evgen.votingsystem.model.User;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}