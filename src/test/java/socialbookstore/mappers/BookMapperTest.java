package socialbookstore.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.BookAuthor;
import socialbookstore.domainmodel.BookCategory;
import socialbookstore.domainmodel.UserProfile;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookMapperTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookMapper bookMapper;

    private UserProfile owner;
    private BookCategory category;
    private BookAuthor author;
    private Book book;
    @BeforeEach
    void setUp() {
        // Set up UserProfile
        owner = new UserProfile();
        owner.setFullName("John");
        entityManager.persist(owner);

        // Set up BookCategory
        category = new BookCategory();
        category.setCategoryName("Fantasy");
        entityManager.persist(category);

        // Set up BookAuthor
        author = new BookAuthor();
        author.setAuthorName("Tolkien");
        entityManager.persist(author);

        // Set up Book
        book = new Book();
        book.setTitle("The Hobbit");
        book.setSummary("Summary of The Hobbit");
        book.setOwner(owner);
        book.setBookCategory(category);
        book.setBookAuthors(Arrays.asList(author));
    }
    @Test
    void testFindByTitle() {
        entityManager.persist(book);
        entityManager.flush();

        List<Book> foundBooks = bookMapper.findByTitle("The Hobbit");
        assertFalse(foundBooks.isEmpty());
        assertEquals("The Hobbit", foundBooks.get(0).getTitle());
    }

    @Test
    void testFindByTitleContaining() {
        entityManager.persist(book);
        entityManager.flush();

        List<Book> foundBooks = bookMapper.findByTitleContaining("Hob");
        assertFalse(foundBooks.isEmpty());
        assertTrue(foundBooks.get(0).getTitle().contains("Hob"));
    }

    @Test
    void testFindByOwner() {
        entityManager.persist(book);
        entityManager.flush();

        List<Book> foundBooks = bookMapper.findByOwner(owner);
        assertFalse(foundBooks.isEmpty());
        assertEquals(owner, foundBooks.get(0).getOwner());
    }

    @Test
    void testFindByBookId() {
        entityManager.persist(book);
        entityManager.flush();

        Book foundBook = bookMapper.findByBookId(book.getBookId());
        assertNotNull(foundBook);
        assertEquals("The Hobbit", foundBook.getTitle());
    }

    @Test
    void testFindByBookCategory() {
        entityManager.persist(book);
        entityManager.flush();

        List<Book> foundBooks = bookMapper.findByBookCategory(category);
        assertFalse(foundBooks.isEmpty());
        assertEquals(category.getCategoryId(), foundBooks.get(0).getBookCategory().getCategoryId());
    }

    @Test
    void testFindByBookAuthorsContains() {
        entityManager.persist(book);
        entityManager.flush();

        List<Book> foundBooks = bookMapper.findByBookAuthorsContains(author);
        assertFalse(foundBooks.isEmpty());
        assertTrue(foundBooks.stream().anyMatch(b -> b.getBookAuthors().contains(author)));
    }
}
