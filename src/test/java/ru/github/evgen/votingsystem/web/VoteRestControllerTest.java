package ru.github.evgen.votingsystem.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.github.evgen.votingsystem.web.util.RestaurantTestData;
import ru.github.evgen.votingsystem.web.util.VoteTestData;
import ru.github.evgen.votingsystem.repository.VoteRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.github.evgen.votingsystem.web.user.UserTestData.USER_MAIL;

class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + '/';
    @Autowired
    protected VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.vote1, VoteTestData.vote3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "date?date=2022-05-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.votesDateTest));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void save() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + "?restaurantId=" + RestaurantTestData.RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}