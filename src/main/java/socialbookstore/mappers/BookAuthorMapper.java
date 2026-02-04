package socialbookstore.mappers;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import socialbookstore.domainmodel.BookAuthor;

@Repository
public interface BookAuthorMapper extends JpaRepository<BookAuthor, Integer> {
	Optional <BookAuthor> findByAuthorName(String authorName);
}
