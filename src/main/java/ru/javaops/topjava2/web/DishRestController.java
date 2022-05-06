package ru.javaops.topjava2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.MenuRepository;
import ru.javaops.topjava2.to.DishTo;

import javax.validation.Valid;
import java.net.URI;

import static ru.javaops.topjava2.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DishRestController {
    static final String REST_URL = "/api/profile/dishes";
    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;

    public DishRestController(DishRepository dishRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id={}", id);
        dishRepository.delete(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable int id) {
        log.info("get dish with id={}", id);
        return dishRepository.findByIdMenu(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo) {
        log.info("create {}", dishTo);
        checkNew(dishTo);
        int menuId = dishTo.getMenuId();
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        Dish newDish = dishRepository.save(new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), menu));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newDish);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update {} with id={}", dishTo, id);
        assureIdConsistent(dishTo, id);
        Dish dish = dishRepository.findByIdMenu(id);
        checkNotFound(dish != null, "id for dish not null");
        Menu menu = dish.getMenu();
        Integer menuId = menu.getId();
        int newMenuId = dishTo.getMenuId();
        if (newMenuId != menuId) {
            Menu newMenu = menuRepository.findById(newMenuId).orElseThrow();
            menu = newMenu;
        }
        dishRepository.save(new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), menu));
    }
}
