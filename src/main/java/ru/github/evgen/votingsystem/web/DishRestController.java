package ru.github.evgen.votingsystem.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.github.evgen.votingsystem.model.Dish;
import ru.github.evgen.votingsystem.repository.DishRepository;
import ru.github.evgen.votingsystem.to.DishTo;
import ru.github.evgen.votingsystem.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
            summary = "Get dish",
            description = "Allows you to find dish by id"
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable int id) {
        log.info("get dish with id={}", id);
        return dishRepository.findById(id).orElseThrow();
    }

    @Operation(
            summary = "Get dishes by name",
            description = "Allows you to find dishes by name"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getByName(@RequestParam(value="name") String name) {
        log.info("get dish with id={}", name);
        return dishRepository.findByName(name);
    }

    @Operation(
            summary = "Create dish",
            description = "Allows you to create new dish"
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo) {
        log.info("create {}", dishTo);
        ValidationUtil.checkNew(dishTo);
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
        ValidationUtil.assureIdConsistent(dishTo, id);
        ValidationUtil.checkNotFound(true, "This dish doesn't exist");
        Dish dishNew = new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice());
        dishNew.setName(dishTo.getName());
        dishNew.setPrice(dishTo.getPrice());
        dishRepository.save(dishNew);
    }
}
