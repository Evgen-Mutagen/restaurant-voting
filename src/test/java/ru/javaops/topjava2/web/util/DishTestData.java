package ru.javaops.topjava2.web.util;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.web.MatcherFactory;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menu");
    public static final int DISH1_ID = 100010;

}
