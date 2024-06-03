package com.romczaj.apicallcounter.user;

import com.romczaj.apicallcounter.BaseIntegrationTest;
import com.romczaj.apicallcounter.callcounter.CallCounterFacade;
import com.romczaj.apicallcounter.user.githubclient.ApiGithubException;
import com.romczaj.apicallcounter.user.githubclient.GithubClient;
import com.romczaj.apicallcounter.user.githubclient.UserApiGithubResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    CallCounterFacade callCounterFacade;

    @MockBean
    GithubClient githubClient;
    private static final String FOUND_LOGIN = "octocat";

    @BeforeEach
    void setUp(){
        Mockito.when(githubClient.findUser(FOUND_LOGIN)).thenReturn(new UserApiGithubResponse(
                "octocat",
                111111,
                "The Octocat",
                "user",
                "https://mock.com/avatar/octocat",
                Instant.now(),
                9,
                8));

    }

    @Test
    @DisplayName("Should return all fields in response when login is found and its account is followed")
    void loginFound() throws Exception {
        mockMvc.perform(get(String.format("/users/%s", FOUND_LOGIN)))
            .andExpect((jsonPath("$.id").exists()))
            .andExpect((jsonPath("$.login").exists()))
            .andExpect((jsonPath("$.name").exists()))
            .andExpect((jsonPath("$.type").exists()))
            .andExpect((jsonPath("$.avatarUrl").exists()))
            .andExpect((jsonPath("$.createdAt").exists()));

        Mockito.verify(callCounterFacade, times(1)).increaseCounter(FOUND_LOGIN);
    }

}
