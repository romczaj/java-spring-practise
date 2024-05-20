package com.romczaj.apicallcounter.user;

import com.romczaj.apicallcounter.BaseIntegrationTest;
import com.romczaj.apicallcounter.callcounter.CallCounterFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    CallCounterFacade callCounterFacade;

    @Test
    @DisplayName("Should return all fields in response when login is found and its account is followed")
    void loginFound() throws Exception {
        String login = "octocat";
        mockMvc.perform(get(String.format("/users/%s", login)))
            .andExpect((jsonPath("$.id").exists()))
            .andExpect((jsonPath("$.login").exists()))
            .andExpect((jsonPath("$.name").exists()))
            .andExpect((jsonPath("$.type").exists()))
            .andExpect((jsonPath("$.avatarUrl").exists()))
            .andExpect((jsonPath("$.createdAt").exists()))
            .andExpect((jsonPath("$.calculations").exists()));

        Mockito.verify(callCounterFacade, times(1)).increaseCounter(login);
    }

    @Test
    @DisplayName("Should return 404 when login is not found")
    void loginNotFound() throws Exception {
        String login = "octocat_123";
        mockMvc.perform(get(String.format("/users/%s", login)))
            .andExpect(status().isNotFound());

        Mockito.verify(callCounterFacade, times(1)).increaseCounter(login);
    }


}
