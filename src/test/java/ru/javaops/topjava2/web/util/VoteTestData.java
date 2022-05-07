package ru.javaops.topjava2.web.util;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.topjava2.web.user.UserTestData.admin;
import static ru.javaops.topjava2.web.user.UserTestData.user;
import static ru.javaops.topjava2.web.util.RestaurantTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int VOTE1_ID = 100031;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.of(2022, 4, 28), restaurant1, user);
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, LocalDate.of(2022, 4, 28), restaurant1, admin);
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, LocalDate.of(2022, 4, 29), restaurant2, user);
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, LocalDate.of(2022, 4, 29), restaurant3, admin);

    public static List<Vote> votes = List.of(vote4, vote3, vote2, vote1);

}
