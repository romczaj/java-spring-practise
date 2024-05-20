package pl.romczaj.multidomain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.romczaj.multidomain.configuration.ObjectMapperConfiguration;
import pl.romczaj.multidomain.input.api.request.AddAnnouncementRequest;
import pl.romczaj.multidomain.input.api.request.AddPersonRequest;
import pl.romczaj.multidomain.output.database.MediaFileEntity;
import pl.romczaj.multidomain.output.database.PersonEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = Main.class)
@Testcontainers
@AutoConfigureTestEntityManager
@Import({IntegrationTestConfiguration.class, ObjectMapperConfiguration.class})
@RunWith(SpringRunner.class)
class PersonTest  {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.4");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TestIntegrationRepository testIntegrationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "media_file", "announcement_person", "person", "announcement");
    }

    @Test
    void insertTest() throws Exception {
        mockMvc.perform(post("/person")
                .content("""
                    {
                       "name": "Jan",
                       "surname": "Nowak"
                    }
                    """)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        mockMvc.perform(post("/person")
                .content("""
                    {
                       "name": "Krzysztof",
                       "surname": "Kowalski"
                    }
                    """)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        assertEquals(2, testIntegrationRepository.findAllPersons().size());
    }

    @Test
    void createFullAnnouncement() throws Exception {
        mockMvc.perform(post("/person")
                .content(objectMapper.writeValueAsString(new AddPersonRequest("Jan", "Nowak")))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        mockMvc.perform(post("/person")
                .content(objectMapper.writeValueAsString(new AddPersonRequest("Krzysztof", "Kowalski")))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        MockMultipartFile file
            = new MockMultipartFile(
            "file",
            "hello.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Hello, World!".getBytes()
        );

        mockMvc.perform(multipart("/media-file")
                .file(file)
                .param("title", "file1")
                .param("description", "description1"))
            .andExpect(status().isCreated());

        mockMvc.perform(multipart("/media-file")
                .file(file)
                .param("title", "file2")
                .param("description", "description2"))
            .andExpect(status().isCreated());

        List<UUID> mediaFileIds = testIntegrationRepository.findAllMediaFiles().stream().map(MediaFileEntity::getId).toList();
        List<UUID> personIds = testIntegrationRepository.findAllPersons().stream().map(PersonEntity::getId).toList();

        mockMvc.perform(post("/announcement")
                .content(objectMapper.writeValueAsString(new AddAnnouncementRequest(
                    "announcement",
                    Instant.now(),
                    Instant.now(),
                    mediaFileIds,
                    personIds
                )))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/announcement/details"))
            .andDo(print())
            .andExpect((jsonPath("$[0].name").value("announcement")))
            .andExpect((jsonPath("$[0].mediaFiles[0].title").exists()))
            .andExpect((jsonPath("$[0].persons[0].name").exists()));
    }
}
