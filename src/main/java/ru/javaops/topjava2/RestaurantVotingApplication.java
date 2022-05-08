package ru.javaops.topjava2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
public class RestaurantVotingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }
}
