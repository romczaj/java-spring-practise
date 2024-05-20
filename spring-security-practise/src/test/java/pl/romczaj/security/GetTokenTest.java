package pl.romczaj.security;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.romczaj.security.api.ApiResponse;
import pl.romczaj.security.login.GetTokenResponse;
import pl.romczaj.security.security.jwt.JwtUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetTokenTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void shouldReturnTokenWhenCorrectUserCredentials() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/authorize/get-token")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                        "username": "USER_USERNAME",
                        "password": "USER_PASSWORD"
                    }
                    """))
            .andExpect(status().isOk())
            .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        JavaType type = objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, GetTokenResponse.class);
        ApiResponse<GetTokenResponse> getTokenResponse = objectMapper.readValue(contentAsString, type);

        assertNotNull(jwtUtils.getJwtClaimsResult(getTokenResponse.response().bearerToken()));
    }
}
