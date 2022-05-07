package ru.javaops.topjava2.web.util;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Menu;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javaops.topjava2.web.util.DishTestData.*;
import static ru.javaops.topjava2.web.util.RestaurantTestData.*;

public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingEqualsComparator(Menu.class);
    public static final int MENU1_ID = 100016;

    public static final Menu menu1 = new Menu(MENU1_ID , LocalDate.of(2022, 4, 28), restaurant1, dish1);
    public static final Menu menu2 = new Menu(MENU1_ID + 1 , LocalDate.of(2022, 4, 28), restaurant2, dish2);
    public static final Menu menu3 = new Menu(MENU1_ID  + 2, LocalDate.of(2022, 4, 28), restaurant3, dish3);

   public static List<Menu> menus = List.of(menu3, menu2, menu1);

    public static Menu getNew() {
        return new Menu (null,LocalDate.of(2022, 5, 7), restaurant2, dish2);
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, menu1.getDate().plus(2, ChronoUnit.MINUTES), restaurant2, dish2);
    }
}
