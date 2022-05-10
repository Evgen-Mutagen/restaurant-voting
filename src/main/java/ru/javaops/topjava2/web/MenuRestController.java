package ru.javaops.topjava2.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.MenuTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@Tag(name = "Menu-rest-controller", description = "Allows you to find, delete, create and edit menus")
@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "menus")
public class MenuRestController {
    static final String REST_URL = "/api/profile/menus";
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    public MenuRestController(MenuRepository menuRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @Operation(
            summary = "Delete menu",
            description = "Allows you to delete menu"
    )
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete menu with id={}", id);
        menuRepository.delete(id);
    }

    @Operation(
            summary = "Get all menus",
            description = "Allows you to see all menus"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable
    public List<Menu> getAll() {
        log.info("get all menus");
        return menuRepository.findAll();
    }

    @Operation(
            summary = "Get menu",
            description = "Allows you to find menu by id"
    )
    @GetMapping(value = "/{id}")
    @Cacheable
    public Optional<Menu> getRestaurantById(@PathVariable int id) {
        log.info("get menu id {}", id);
        return menuRepository.findByIdRestaurantAndDish(id);
    }

    @Operation(
            summary = "Get menu by date",
            description = "Allows you to find menu by id"
    )
    @GetMapping(path = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable
    public List<Menu> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get menus by date {}", date);
        return menuRepository.findAllByDate(date);
    }

    @Operation(
            summary = "Create menu",
            description = "Allows you to create new menu"
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @Transactional(readOnly = true)
    public ResponseEntity<Menu> create(@Valid @RequestBody MenuTo menuTo) {
        log.info("create {}", menuTo);
        checkNew(menuTo);
        int restaurantId = menuTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findByRestId(restaurantId).orElse(null);
        Menu newMenu = menuRepository.save(new Menu(menuTo.getId(), menuTo.getDate(), restaurant));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newMenu.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMenu);
    }

    @Operation(
            summary = "Update menu",
            description = "Allows you to edit menu"
    )
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional(readOnly = true)
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int id) {
        log.info("update {} with id={}", menuTo, id);
        assureIdConsistent(menuTo, id);
        int restaurantId = menuTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findByRestId(restaurantId).orElse(null);
        int dishId = menuTo.getDishId();
        Dish dish = dishRepository.findByDishId(dishId).orElse(null);
        Menu newMenu = new Menu(menuTo.getId(), menuTo.getDate(), restaurant);
        newMenu.setDate(menuTo.getDate());
        newMenu.setRestaurant(restaurant);
        newMenu.setDish(dish);
        menuRepository.save(newMenu);
    }
}
