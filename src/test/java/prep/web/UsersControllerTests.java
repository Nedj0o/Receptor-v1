package prep.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import prep.model.entity.Role;
import prep.model.entity.RoleName;
import prep.model.entity.User;
import prep.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository mockedUserRepository;

    private String USER_ID;
    private String USER_NICKNAME = "Mityo";
    private String USER_PASSWORD = "123456";
    private String USER_EMAIL = "mityo.pishtova@abv.bg";
    private Role USER_ROLE = new Role();

    @BeforeEach
    public void setUp() {
        USER_ROLE.setRoleName(RoleName.USER);
        User user = new User();
        user.setUsername(USER_NICKNAME);
        user.setPassword(USER_PASSWORD);
        user.setEmail(USER_EMAIL);
        user.setRole(USER_ROLE);
        user = this.mockedUserRepository.saveAndFlush(user);
        USER_ID = user.getId();
    }

    @AfterEach
    public void tearDown(){
        this.mockedUserRepository.deleteAll();
    }

    @Test
    public void testRegisterPageStatusCodeOk() throws Exception {
        this.mockMvc.perform(get("/users/register")).andExpect(status().isOk());
    }

    @Test void testLoginPageStatusCodeOk() throws Exception {
        this.mockMvc.perform(get("/users/login")).andExpect(status().isOk());
    }
}
