package ru.javaops.topjava2.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.to.DishTo;

import javax.validation.Valid;
import java.net.URI;

import static ru.javaops.topjava2.util.validation.ValidationUtil.*;

@Tag(name = "Dish-rest-controller", description = "Allows you to find, delete, create and edit dishes")
@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DishRestController {
    static final String REST_URL = "/api/profile/dishes";
    private final DishRepository dishRepository;

    public DishRestController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Operation(
            summary = "Delete dish",
            description = "Allows you to delete dish"
    )
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id={}", id);
        dishRepository.delete(id);
    }

    @Operation(
            summary = "Get dish",
            description = "Allows you to find dish by id"
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable int id) {
        log.info("get dish with id={}", id);
        return dishRepository.findByDishId(id).orElseThrow();
    }

    @Operation(
            summary = "Create dish",
            description = "Allows you to create new dish"
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo) {
        log.info("create {}", dishTo);
        checkNew(dishTo);
        Dish newDish = dishRepository.save(new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice()));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newDish);
    }

    @Operation(
            summary = "Update dish",
            description = "Allows you to edit price and name for dish"
    )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update {} with id={}", dishTo, id);
        assureIdConsistent(dishTo, id);
        Dish dish = dishRepository.findByDishId(id).orElseThrow();
        checkNotFound(dish != null, "id for dish not null");
        Dish dishNew = new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice());
        dishNew.setName(dishTo.getName());
        dishNew.setPrice(dishTo.getPrice());
        dishRepository.save(dishNew);
    }
}
