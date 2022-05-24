package ru.github.evgen.votingsystem.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.github.evgen.votingsystem.error.IllegalRequestDataException;
import ru.github.evgen.votingsystem.model.Restaurant;
import ru.github.evgen.votingsystem.model.Vote;
import ru.github.evgen.votingsystem.repository.VoteRepository;
import ru.github.evgen.votingsystem.util.validation.ValidationUtil;

import javax.persistence.EntityManager;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Tag(name = "Vote-rest-controller", description = "Allows you to find and vote")
@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteRestController {
    static final String REST_URL = "/api/profile/votes";
    public static LocalTime updateTime = LocalTime.of(11, 0);
    private final VoteRepository voteRepository;
    private final EntityManager em;

    public VoteRestController(VoteRepository voteRepository, EntityManager em) {
        this.voteRepository = voteRepository;
        this.em = em;
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
    @PostMapping
    public ResponseEntity<Vote> create(@RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        Restaurant restaurant = em.find(Restaurant.class, restaurantId);
        log.info("create vote for {}", restaurantId);
        LocalDate dateNow = LocalDate.now();
        Vote newVote = voteRepository.save(new Vote(dateNow, restaurant, authUser.getUser()));
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(REST_URL + "/{Id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newVote);
    }

    @Operation(
            summary = "Update",
            description = "Allows you to revote for restaurant"
    )
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam Integer restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        Restaurant restaurant = em.find(Restaurant.class, restaurantId);
        int userId = authUser.id();
        LocalDate dateNow = LocalDate.now();
        log.info("user with id={} vote for restaurant with id={}", userId, restaurantId);
        Vote vote = voteRepository.findByDateAndUser(dateNow, userId);
        if (vote == null) {
            throw new IllegalRequestDataException("Need create new vote");
        } else if (LocalTime.now().isBefore(updateTime)) {
            Integer oldId = vote.getRestaurant().getId();
            vote.setRestaurant(restaurant);
            ValidationUtil.checkIdenticalId(restaurant.id(), oldId);
            voteRepository.save(vote);
        } else throw new IllegalRequestDataException("Voting time is over");
    }
}
