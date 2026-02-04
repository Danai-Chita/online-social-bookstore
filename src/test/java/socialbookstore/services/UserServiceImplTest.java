package socialbookstore.services;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import socialbookstore.domainmodel.Role;
import socialbookstore.domainmodel.User;
import socialbookstore.mappers.UserMapper;



public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserMapper userMapper;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setPassword("testPassword");
        when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        userService.saveUser(user);

        verify(userMapper).save(any(User.class));
    }

    
    @Test
    public void testIsUserPresent() {
        when(userMapper.findByUsername("test")).thenReturn(Optional.of(new User()));
        User newUser = new User();
        newUser.setEmail("test");
        newUser.setId(1);
        newUser.setPassword("1234");
        newUser.setUsername("test");
        newUser.setRole(Role.USER);
        userService.isUserPresent(newUser);
        verify(userMapper).findByUsername("test");
    }

    
    @Test
    public void testLoadUserByUsername_UserFound() {
        when(userMapper.findByUsername("test")).thenReturn(Optional.of(new User()));

        userService.loadUserByUsername("test");

        verify(userMapper).findByUsername("test");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userMapper.findByUsername("test")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("test");
        });

        verify(userMapper).findByUsername("test");
    }

}
