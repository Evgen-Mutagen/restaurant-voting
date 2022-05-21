package ru.github.evgen.votingsystem.web.util;

import ru.github.evgen.votingsystem.model.Menu;
import ru.github.evgen.votingsystem.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.github.evgen.votingsystem.web.util.DishTestData.*;
import static ru.github.evgen.votingsystem.web.util.RestaurantTestData.*;

public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingEqualsComparator(Menu.class);
    public static final int MENU1_ID = 100016;

    public static final Menu menu1 = new Menu(MENU1_ID, LocalDate.of(2022, 4, 28), restaurant1, dish1);
    public static final Menu menu2 = new Menu(MENU1_ID + 1, LocalDate.of(2022, 4, 28), restaurant1, dish2);
    public static final Menu menu3 = new Menu(MENU1_ID + 2, LocalDate.of(2022, 4, 28), restaurant1, dish3);
    public static final Menu menu4 = new Menu(MENU1_ID + 3, LocalDate.of(2022, 4, 28), restaurant2, dish4);
    public static final Menu menu5 = new Menu(MENU1_ID + 4, LocalDate.of(2022, 4, 28), restaurant2, dish5);
    public static final Menu menu6 = new Menu(MENU1_ID + 5, LocalDate.of(2022, 4, 28), restaurant2, dish6);
    public static final Menu menu7 = new Menu(MENU1_ID + 6, LocalDate.of(2022, 4, 28), restaurant3, dish7);
    public static final Menu menu8 = new Menu(MENU1_ID + 7, LocalDate.of(2022, 4, 28), restaurant3, dish8);
    public static final Menu menu9 = new Menu(MENU1_ID + 8, LocalDate.of(2022, 4, 28), restaurant3, dish9);
    public static final Menu menu10 = new Menu(MENU1_ID + 9, LocalDate.of(2022, 5, 1), restaurant1, dish2);
    public static final Menu menu11 = new Menu(MENU1_ID + 10, LocalDate.of(2022, 5, 1), restaurant1, dish5);
    public static final Menu menu12 = new Menu(MENU1_ID + 11, LocalDate.of(2022, 5, 1), restaurant1, dish7);
    public static final Menu menu13 = new Menu(MENU1_ID + 12, LocalDate.of(2022, 5, 1), restaurant3, dish11);
    public static final Menu menu14 = new Menu(MENU1_ID + 13, LocalDate.of(2022, 5, 1), restaurant3, dish5);
    public static final Menu menu15 = new Menu(MENU1_ID + 14, LocalDate.of(2022, 5, 1), restaurant3, dish10);

    public static List<Menu> menus = List.of(menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8, menu9, menu10, menu11,
            menu12, menu13, menu14, menu15);

    public static List<Menu> menusDateTest = List.of(menu10, menu11, menu12, menu13, menu14, menu15);

    public static Menu getNew() {
        return new Menu(null, LocalDate.now(), null, null);
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, LocalDate.now(), restaurant2, dish2);
    }
}
