package socialbookstore.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import socialbookstore.domainmodel.Role;
import socialbookstore.domainmodel.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindByUsername() {
        // Setup data
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setPassword("password");
        newUser.setEmail("user@example.com");
        newUser.setRole(Role.USER); // Assuming Role is an enum defined somewhere
        entityManager.persist(newUser);
        entityManager.flush();

        // Test finding by username
        Optional<User> foundUser = userMapper.findByUsername("testUser");
        assertTrue(foundUser.isPresent(), "User should be found with username 'testUser'");
        assertEquals("testUser", foundUser.get().getUsername(), "Username should match");
    }

    @Test
    public void testFindByEmail() {
        // Setup data
        User newUser = new User();
        newUser.setUsername("testEmailUser");
        newUser.setPassword("password");
        newUser.setEmail("test@example.com");
        newUser.setRole(Role.USER);
        entityManager.persist(newUser);
        entityManager.flush();

        // Test finding by email
        Optional<User> foundUser = userMapper.findByEmail("test@example.com");
        assertTrue(foundUser.isPresent(), "User should be found with email 'test@example.com'");
        assertEquals("test@example.com", foundUser.get().getEmail(), "Email should match");
    }

    @Test
    public void testDeleteById() {
        // Setup data
        User newUser = new User();
        newUser.setUsername("deleteUser");
        newUser.setPassword("password");
        newUser.setEmail("delete@example.com");
        newUser.setRole(Role.USER);
        User savedUser = entityManager.persistFlushFind(newUser);

        // Test deletion
        userMapper.deleteById(savedUser.getId());
        entityManager.flush();
        Optional<User> deletedUser = userMapper.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent(), "User should not be found after deletion");
    }
}
