package ru.github.evgen.votingsystem.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.github.evgen.votingsystem.model.Role;
import ru.github.evgen.votingsystem.model.User;
import ru.github.evgen.votingsystem.repository.UserRepository;
import ru.github.evgen.votingsystem.util.UserUtil;
import ru.github.evgen.votingsystem.web.AuthUser;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Authenticating '{}'", email);
            Optional<User> optionalUser = userRepository.getByEmail(email);
            return new AuthUser(optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + email + "' was not found")));
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(UserUtil.PASSWORD_ENCODER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/profile/menus/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/profile/menus/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/profile/menus/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/profile/dishes/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/profile/dishes/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/profile/restaurants/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/profile/restaurants/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/profile/restaurants/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/profile").anonymous()
                .antMatchers("/api/**").authenticated()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
}