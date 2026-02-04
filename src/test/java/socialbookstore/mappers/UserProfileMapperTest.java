package socialbookstore.mappers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import socialbookstore.domainmodel.UserProfile;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserProfileMapperTest {

    @Autowired
    private UserProfileMapper profileMapper;

    @Test
    public void testUserProfilePersistence() {
        UserProfile userProfile = new UserProfile();
        userProfile.setFullName("Test");
        userProfile = profileMapper.save(userProfile);
        assertNotNull(profileMapper.findById(userProfile.getProfileId()));
    }
}
