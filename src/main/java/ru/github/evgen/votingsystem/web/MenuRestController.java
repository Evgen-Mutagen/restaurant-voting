package ru.github.evgen.votingsystem.web;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.github.evgen.votingsystem.model.Dish;
import ru.github.evgen.votingsystem.model.Menu;
import ru.github.evgen.votingsystem.model.Restaurant;
import ru.github.evgen.votingsystem.repository.MenuRepository;
import ru.github.evgen.votingsystem.to.MenuTo;
import ru.github.evgen.votingsystem.util.validation.ValidationUtil;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Tag(name = "Menu-rest-controller", description = "Allows you to find, delete, create and edit menus")
@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "menus")
public class MenuRestController {
    static final String REST_URL = "/api/profile/menus";
    private final MenuRepository menuRepository;
    private final EntityManager em;

    public MenuRestController(MenuRepository menuRepository, EntityManager em) {
        this.menuRepository = menuRepository;
        this.em = em;
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
            summary = "Get menu",
            description = "Allows you to find menu by id"
    )
    @GetMapping(value = "/{id}")
    @Cacheable
    public Optional<Menu> getRestaurantById(@PathVariable int id) {
        log.info("get menu id {}", id);
        return menuRepository.findByIdWithRestaurantAndDish(id);
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
            summary = "Get menu by today",
            description = "Allows you to find menu by today"
    )

    @GetMapping(path = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable
    public List<Menu> getByToday() {
        LocalDate today = LocalDate.now();
        return menuRepository.findAllByDate(today);
    }

    @Operation(
            summary = "Create menu",
            description = "Allows you to create new menu"
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Menu> create(@Valid @RequestBody MenuTo menuTo) {
        log.info("create {}", menuTo);
        ValidationUtil.checkNew(menuTo);
        Restaurant restaurant = em.find(Restaurant.class, menuTo.getRestaurantId());
        Dish dish = em.find(Dish.class, menuTo.getDishId());
        Menu newMenu = menuRepository.save(new Menu(menuTo.getId(), menuTo.getDate(), restaurant, dish));
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
    public void update(@Valid @RequestBody MenuTo menuTo, @PathVariable int id) {
        log.info("update {} with id={}", menuTo, id);
        ValidationUtil.assureIdConsistent(menuTo, id);
        Restaurant restaurant = em.find(Restaurant.class, menuTo.getRestaurantId());
        Dish dish = em.find(Dish.class, menuTo.getDishId());
        Menu newMenu = new Menu(menuTo.getId(), menuTo.getDate(), restaurant, dish);
        newMenu.setDate(menuTo.getDate());
        newMenu.setRestaurant(restaurant);
        newMenu.setDish(dish);
        menuRepository.save(newMenu);
    }
}
