package socialbookstore.domainmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

class UserTests {

    @InjectMocks
    private User testUser;

    public UserTests() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testUser");
        testUser.setPassword("password");
        testUser.setEmail("test@example.com");
        testUser.setRole(Role.USER); // Assuming Role is an enum you have defined somewhere
    }

    @Test
    void testGetId() {
        assertEquals(1, testUser.getId());
    }

    @Test
    void testSetAndGetUsername() {
        testUser.setUsername("newUser");
        assertEquals("newUser", testUser.getUsername());
    }

    @Test
    void testSetAndGetPassword() {
        testUser.setPassword("newPassword");
        assertEquals("newPassword", testUser.getPassword());
    }

    @Test
    void testSetAndGetEmail() {
        testUser.setEmail("new@example.com");
        assertEquals("new@example.com", testUser.getEmail());
    }

    @Test
    void testSetAndGetRole() {
        testUser.setRole(Role.USER);
        assertEquals(Role.USER, testUser.getRole());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = testUser.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(testUser.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(testUser.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(testUser.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(testUser.isEnabled());
    }
}
