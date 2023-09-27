package cl.listplus.api.user.service.impl;

import cl.listplus.api.user.model.User;
import cl.listplus.api.user.repository.UserMapper;
import cl.listplus.api.user.repository.UserRepository;
import cl.listplus.api.user.utils.UserServiceTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Stream;

import static cl.listplus.api.user.repository.UserSpecifications.hasEmail;
import static cl.listplus.api.user.repository.UserSpecifications.hasUsername;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private static final String RETRIEVE_USER_RESPONSE_FILE_PATH = "responses/retrieve_user_response.json";

    private static final Specification<User> USERNAME_SPECIFICATION = hasUsername("juanito");
    private static final Specification<User> EMAIL_SPECIFICATION = hasEmail("juanito@listplus.cl");

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("specs")
    public void whenRetrievingUserBySpecificationThenUserReturnedOK(Specification<User> specification) throws Exception {
        User expectedUser = UserServiceTestUtils.responseObject(RETRIEVE_USER_RESPONSE_FILE_PATH, User.class);
        when(userRepository.findAll(specification)).thenReturn(List.of(expectedUser));
        User retrievedUser = userService.retrieveUser(specification);
        verify(userRepository).findAll(specification);
        assertEquals(retrievedUser, expectedUser);
    }

    private static Stream<Arguments> specs() {
        return Stream.of(arguments(USERNAME_SPECIFICATION), arguments(EMAIL_SPECIFICATION));
    }
}