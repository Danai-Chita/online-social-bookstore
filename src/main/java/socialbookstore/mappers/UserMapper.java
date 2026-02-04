package socialbookstore.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import socialbookstore.domainmodel.User;

@Repository
public interface UserMapper extends JpaRepository<User, Integer>{
	
	User deleteById(int id);
	Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
