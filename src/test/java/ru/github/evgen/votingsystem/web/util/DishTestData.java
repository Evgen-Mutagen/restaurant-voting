package ru.github.evgen.votingsystem.web.util;

import ru.github.evgen.votingsystem.model.Dish;
import ru.github.evgen.votingsystem.web.MatcherFactory;

import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingEqualsComparator(Dish.class);
    public static final int DISH1_ID = 100005;

    public static final Dish dish1 = new Dish(DISH1_ID, "Shawarma", 230);
    public static final Dish dish2 = new Dish(DISH1_ID + 1, "Pita", 180);
    public static final Dish dish3 = new Dish(DISH1_ID + 2, "Lemonade", 90);
    public static final Dish dish4 = new Dish(DISH1_ID + 3, "Sushi", 320);
    public static final Dish dish5 = new Dish(DISH1_ID + 4, "Ramen", 280);
    public static final Dish dish6 = new Dish(DISH1_ID + 5, "Wok", 335);
    public static final Dish dish7 = new Dish(DISH1_ID + 6, "khinkali", 280);
    public static final Dish dish8 = new Dish(DISH1_ID + 7, "Khachapuri", 230);
    public static final Dish dish9 = new Dish(DISH1_ID + 8, "Lobio", 290);
    public static final Dish dish10 = new Dish(DISH1_ID + 8, "Ramen", 310);
    public static final Dish dish11 = new Dish(DISH1_ID + 8, "Khachapuri", 250);

    List<Dish> dishes = List.of(dish6, dish5, dish4, dish3, dish2, dish1);

    public static Dish getNew() {
        return new Dish(null, "newDIsh", 0);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "updated price", 500);
    }
}
