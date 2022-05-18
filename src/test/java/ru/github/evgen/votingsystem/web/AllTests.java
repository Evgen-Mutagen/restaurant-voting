package ru.github.evgen.votingsystem.web;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"ru.github.evgen.votingsystem.web.user"})
@SelectClasses({
        DishRestControllerTest.class,
        MenuRestControllerTest.class,
        RestaurantControllerTest.class,
        VoteRestControllerTest.class
})
public class AllTests {
}
