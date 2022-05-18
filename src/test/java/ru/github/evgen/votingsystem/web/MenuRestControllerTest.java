package ru.github.evgen.votingsystem.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.github.evgen.votingsystem.web.util.MenuTestData;
import ru.github.evgen.votingsystem.model.Menu;
import ru.github.evgen.votingsystem.repository.MenuRepository;
import ru.github.evgen.votingsystem.util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.github.evgen.votingsystem.web.user.UserTestData.USER_MAIL;

class MenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuRestController.REST_URL + '/';
    @Autowired
    protected MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MenuTestData.MENU1_ID))
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findByIdWithRestaurantAndDish(MenuTestData.MENU1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(MenuTestData.menus));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "date?date=2022-05-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(MenuTestData.menusDateTest));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void create() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)));

        Menu created = MenuTestData.MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MenuTestData.MENU_MATCHER.assertMatch(created, newMenu);
        MenuTestData.MENU_MATCHER.assertMatch(menuRepository.getById(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Menu updated = MenuTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MenuTestData.MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        MenuTestData.MENU_MATCHER.assertMatch(menuRepository.getById(MenuTestData.MENU1_ID), updated);
    }
}