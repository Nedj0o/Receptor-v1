package prep.services;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import prep.model.entity.User;
import prep.model.entity.Role;
import prep.model.entity.RoleName;
import prep.repository.UserRepository;
import prep.service.RoleService;
import prep.service.UserService;
import prep.service.impl.UserServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTests {

    private User testUser;
    private UserRepository mockedUserRepository;
    private ModelMapper modelMapper;
    private RoleService roleService;

    @BeforeEach
    public void init() {
        this.testUser = new User() {{
            setId("696969699696969699696");
            setUsername("Mityo");
            setPassword("123456");
            setEmail("mityo.pishtova@abv.bg");
            setRole(new Role() {{
                setRoleName(RoleName.USER);
            }});
        }};

        this.mockedUserRepository = Mockito.mock(UserRepository.class);
    }

    @Test
    public void userService_GetUserWithCorrectUsername_ShouldReturnCorrect() {
        // Arrange
        Mockito.when(this.mockedUserRepository
                .findByUsername("Mityo"))
                .thenReturn(java.util.Optional.ofNullable(this.testUser));

        UserService userService =
                new UserServiceImpl(this.mockedUserRepository, this.modelMapper, this.roleService);
        User expected = this.testUser;

        // Act
        User actual = this.modelMapper.map(userService.findByUsername("Mityo"), User.class);

        // Assert
        Assert.assertEquals("Broken...", expected.getId(),
                actual.getId());
        Assert.assertEquals("Broken...", expected.getUsername(),
                actual.getUsername());
        Assert.assertEquals("Broken...", expected.getPassword(),
                actual.getPassword());
        Assert.assertEquals("Broken...", expected.getEmail(),
                actual.getPassword());
    }
}


