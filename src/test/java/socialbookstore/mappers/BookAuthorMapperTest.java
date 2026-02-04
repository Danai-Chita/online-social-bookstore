package socialbookstore.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import socialbookstore.domainmodel.BookAuthor;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookAuthorMapperTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookAuthorMapper bookAuthorMapper;

    @Test
    public void testFindByAuthorName() {
        BookAuthor author = new BookAuthor();
        author.setAuthorName("Efi");
        entityManager.persist(author);
        entityManager.flush();

        Optional<BookAuthor> foundAuthor = bookAuthorMapper.findByAuthorName("Efi");
        assertTrue(foundAuthor.isPresent());
        assertEquals("Efi", foundAuthor.get().getAuthorName());
    }
}
