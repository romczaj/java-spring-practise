package pl.romczaj.http.githubclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.TestSocketUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;

//@WireMockTest(httpPort = 0, proxyMode = true)
//@SpringBootTest
class RestGithubClientTest {

    private final GithubFeignInterface githubFeignInterface;
    private final GithubClient githubClient;
    private final String scenario = "testCircuitBreaker";
    private final String second500 = "second500";
    private final String third500 = "third500";
    private final String success200 = "fourth200";

    private final String user = "octocat";

    public RestGithubClientTest() {
        int availableTcpPort = TestSocketUtils.findAvailableTcpPort();
        WireMockServer wireMockServer = new WireMockServer(availableTcpPort);
        wireMockServer.start();
        configureFor("localhost", availableTcpPort);

        GithubOkHttpConfiguration githubOkHttpConfiguration = new GithubOkHttpConfiguration();
        ObjectMapper objectMapper = githubOkHttpConfiguration.objectMapper();
        this.githubFeignInterface = githubOkHttpConfiguration.githubFeignClient(
                objectMapper,
                new GithubApiProperties(wireMockServer.baseUrl()));

        this.githubClient = new GithubClient(githubFeignInterface);
    }

    @Test
    void shouldReturnResponse() {
        //given
        wiremockResponse(200);

        //when
        UserApiGithubResponse userApiGithubResponse = githubClient.findUser(user);

        //then
        Assertions.assertNotNull(userApiGithubResponse);
    }

    @Test
    void shouldCheckCircuitBreaker() {
        //given
        wiremockResponse(500, STARTED, second500);
        wiremockResponse(500, second500, third500);
        wiremockResponse(500, third500, success200);
        wiremockResponse(200, success200, success200);

        //when then
        UserApiGithubResponse userApiGithubResponse = githubClient.findUser(user);
        Assertions.assertNotNull(userApiGithubResponse);
    }



    private void wiremockResponse(Integer statusCode){
        stubFor(get(urlEqualTo("/" + user))
                .willReturn(aResponse().withStatus(statusCode).withBody(OCTOCAT_RESPONSE_BODY)));

    }
    private void wiremockResponse(Integer statusCode, String scenarioStep, String nextScenarioStep){
        stubFor(get(urlEqualTo("/" + user))
                .inScenario(scenario)
                .whenScenarioStateIs(scenarioStep)
                .willSetStateTo(nextScenarioStep)
                .willReturn(aResponse().withStatus(statusCode).withBody(OCTOCAT_RESPONSE_BODY)));

    }

    private static final String OCTOCAT_RESPONSE_BODY = """
            {
              "login": "octocat",
              "id": 583231,
              "node_id": "MDQ6VXNlcjU4MzIzMQ==",
              "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4",
              "gravatar_id": "",
              "url": "https://api.github.com/users/octocat",
              "html_url": "https://github.com/octocat",
              "followers_url": "https://api.github.com/users/octocat/followers",
              "following_url": "https://api.github.com/users/octocat/following{/other_user}",
              "gists_url": "https://api.github.com/users/octocat/gists{/gist_id}",
              "starred_url": "https://api.github.com/users/octocat/starred{/owner}{/repo}",
              "subscriptions_url": "https://api.github.com/users/octocat/subscriptions",
              "organizations_url": "https://api.github.com/users/octocat/orgs",
              "repos_url": "https://api.github.com/users/octocat/repos",
              "events_url": "https://api.github.com/users/octocat/events{/privacy}",
              "received_events_url": "https://api.github.com/users/octocat/received_events",
              "type": "User",
              "site_admin": false,
              "name": "The Octocat",
              "company": "@github",
              "blog": "https://github.blog",
              "location": "San Francisco",
              "email": null,
              "hireable": null,
              "bio": null,
              "twitter_username": null,
              "public_repos": 8,
              "public_gists": 8,
              "followers": 12592,
              "following": 9,
              "created_at": "2011-01-25T18:44:36Z",
              "updated_at": "2024-02-22T12:23:33Z"
            }
            """;
}
