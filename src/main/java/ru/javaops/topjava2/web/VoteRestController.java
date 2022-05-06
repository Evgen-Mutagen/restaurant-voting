package ru.javaops.topjava2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteRestController {
    static final String REST_URL = "/api/profile/votes";
    public static final LocalTime UPDATE_TIME = LocalTime.of(11, 0);
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteRestController(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByUser(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get vote for user id={}", userId);
        return voteRepository.findByUser(userId);
    }

    @GetMapping(path = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByDate(LocalDate date) {
        log.info("get vote for date={}", date);
        return voteRepository.findByDate(date);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void save(@RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        LocalDate dateNow = LocalDate.now();
        log.info("user with id={} vote for restaurant with id={}", userId, restaurantId);
        Vote vote = voteRepository.findByDateAndUser(dateNow, userId);
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        if (vote == null) {
            voteRepository.save(new Vote(null, dateNow, restaurant, authUser.getUser()));
        } else if (LocalTime.now().isBefore(UPDATE_TIME)) {
            vote.setRestaurant(restaurant);
        } else throw new IllegalRequestDataException("Voting time is over");
    }
}
