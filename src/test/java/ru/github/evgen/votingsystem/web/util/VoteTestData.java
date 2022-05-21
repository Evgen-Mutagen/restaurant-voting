package ru.github.evgen.votingsystem.web.util;

import ru.github.evgen.votingsystem.model.Vote;
import ru.github.evgen.votingsystem.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static ru.github.evgen.votingsystem.web.user.UserTestData.admin;
import static ru.github.evgen.votingsystem.web.user.UserTestData.user;
import static ru.github.evgen.votingsystem.web.util.RestaurantTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    public static final int VOTE1_ID = 100031;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.of(2022, 4, 28), restaurant1, user);
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, LocalDate.of(2022, 4, 28), restaurant1, admin);
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, LocalDate.of(2022, 5, 1), restaurant2, user);
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, LocalDate.of(2022, 5, 1), restaurant3, admin);

    public static List<Vote> votes = List.of(vote1, vote2, vote3, vote4);
    public static List<Vote> votesDateTest = List.of(vote3, vote4);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), restaurant1, null);
    }
}
