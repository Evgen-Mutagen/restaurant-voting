package ru.javaops.topjava2.web.util;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.web.MatcherFactory;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "menu");
    public static final Integer DISH1_ID = 100010;
    public static final int DISH2_ID = 100011;
    public static final int DISH3_ID = 100012;
    public static final int DISH4_ID = 100013;
    public static final int DISH5_ID = 100014;
    public static final int DISH6_ID = 100015;
    public static final int DISH7_ID = 100016;
    public static final int DISH8_ID = 100017;

        public static final Dish DISH1 = new Dish(DISH1_ID, "Shawarma", 230);

}
