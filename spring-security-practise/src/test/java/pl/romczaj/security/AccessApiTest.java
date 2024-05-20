package pl.romczaj.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pl.romczaj.security.security.jwt.JwtUtils;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.romczaj.security.security.ApplicationRole.ADMIN;
import static pl.romczaj.security.security.ApplicationRole.USER;

@SpringBootTest
@AutoConfigureMockMvc
class AccessApiTest {

    private static final String ADMIN_USERNAME = "ADMIN_USERNAME";
    private static final String ADMIN_PASSWORD = "ADMIN_PASSWORD";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @DisplayName("/user/** endpoints without any authorization")
    @Test
    void shouldReturn401WhenRequestWithoutAuthorization() throws Exception {
        mockMvc.perform(get("/user/cars"))
            .andExpect(status().isUnauthorized());
    }

    @DisplayName("/user/** endpoints with correct admin basic authorization")
    @Test
    void shouldReturn401WhenRequestWithInCorrectUserBasicAuthorization() throws Exception {
        mockMvc.perform(
                get("/user/cars").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
            .andExpect(status().isUnauthorized());
    }

    @DisplayName("/user/** endpoints with correct USER bearer token")
    @Test
    void shouldReturn200WhenCorrectBearerToken() throws Exception {
        String bearerToken = jwtUtils.generateTokenForUser(1L, USER);
        mockMvc.perform(
                get("/user/cars")
                    .header("authorization", "Bearer " + bearerToken))
            .andExpect(status().isOk());
    }

    @DisplayName("/user/** endpoints with correct not USER bearer token")
    @Test
    void shouldReturn401WhenIncorrectBearerToken() throws Exception {
        String bearerToken = jwtUtils.generateTokenForUser(2L, ADMIN);
        mockMvc.perform(
                get("/user/cars")
                    .header("authorization", "Bearer " + bearerToken))
            .andExpect(status().isForbidden());
    }


    @DisplayName("/admin/** endpoints with correct admin basic authorization")
    @Test
    void shouldReturn200WhenRequestWithCorrectAdminBasicAuthorization() throws Exception {
        mockMvc.perform(
                get("/admin/cars").with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
            .andExpect(status().isOk());
    }

}
