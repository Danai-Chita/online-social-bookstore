package socialbookstore.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import socialbookstore.domainmodel.UserProfile;

@Repository
public interface UserProfileMapper extends JpaRepository<UserProfile, Integer> {

}
