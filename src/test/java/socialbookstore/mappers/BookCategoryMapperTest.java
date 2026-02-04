package socialbookstore.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import socialbookstore.domainmodel.BookCategory;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookCategoryMapperTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookCategoryMapper bookCategoryMapper;

    @Test
    public void testFindByCategoryName_Unique() {
        // Setup - ensuring unique category names for the test
        BookCategory category = new BookCategory();
        category.setCategoryName("UniqueFiction");
        entityManager.persist(category);
        entityManager.flush();

        // Execution
        Optional<BookCategory> foundCategory = bookCategoryMapper.findByCategoryName("UniqueFiction");

        // Assertion
        assertTrue(foundCategory.isPresent());
        assertEquals("UniqueFiction", foundCategory.get().getCategoryName());
    }
}
