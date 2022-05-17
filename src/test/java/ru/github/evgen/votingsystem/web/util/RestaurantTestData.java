package ru.github.evgen.votingsystem.web.util;

import ru.github.evgen.votingsystem.model.Restaurant;
import ru.github.evgen.votingsystem.web.MatcherFactory;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);
    public static final int RESTAURANT1_ID = 100002;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Bistro");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Japanese");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "Georgian");

    public static List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);

    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Updated restaurant");
    }
}
