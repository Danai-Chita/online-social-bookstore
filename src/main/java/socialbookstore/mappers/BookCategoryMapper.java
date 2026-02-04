package socialbookstore.mappers;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import socialbookstore.domainmodel.BookCategory;

@Repository
public interface BookCategoryMapper extends JpaRepository<BookCategory, Integer>{
	Optional<BookCategory> findByCategoryName(String categoryName);
}
