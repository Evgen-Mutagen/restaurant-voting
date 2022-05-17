package ru.github.evgen.votingsystem.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.github.evgen.votingsystem.error.IllegalRequestDataException;
import ru.github.evgen.votingsystem.model.Restaurant;
import ru.github.evgen.votingsystem.model.Vote;
import ru.github.evgen.votingsystem.repository.RestaurantRepository;
import ru.github.evgen.votingsystem.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Tag(name = "Vote-rest-controller", description = "Allows you to find and vote")
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

    @Operation(
            summary = "Get vote by user",
            description = "Allows you to find vote by id"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByUser(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("get vote for user id={}", userId);
        return voteRepository.findByUser(userId);
    }

    @Operation(
            summary = "Get vote by date",
            description = "Allows you to find vote by date"
    )
    @GetMapping(path = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get vote for date={}", date);
        return voteRepository.findByDate(date);
    }

    @Operation(
            summary = "Save",
            description = "Allows you to vote for restaurant"
    )
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void save(@RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        int userId = authUser.id();
        LocalDate dateNow = LocalDate.now();
        log.info("user with id={} vote for restaurant with id={}", userId, restaurantId);
        Vote vote = voteRepository.findByDateAndUser(dateNow, userId);
        if (vote == null) {
            voteRepository.save(new Vote(dateNow, restaurant, authUser.getUser()));
        } else if (LocalTime.now().isBefore(UPDATE_TIME)) {
            vote.setRestaurant(restaurant);
            voteRepository.save(vote);
        } else throw new IllegalRequestDataException("Voting time is over");
    }
}
