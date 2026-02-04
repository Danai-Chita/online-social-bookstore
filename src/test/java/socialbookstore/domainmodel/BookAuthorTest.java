package socialbookstore.domainmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;

class BookAuthorTests {

    @InjectMocks
    private BookAuthor testBookAuthor;

    private UserProfile testUserProfile;
    private Book testBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testBookAuthor = new BookAuthor();
        testBookAuthor.setAuthorId(1);
        testBookAuthor.setAuthorName("J.K. Rowling");

        // Setup UserProfile
        testUserProfile = new UserProfile();
        testUserProfile.setProfileId(101);

        // Setup Book
        testBook = new Book();
        testBook.setBookId(201);
        
        // Set relationships
        testBookAuthor.setSelectedProfileUsers(Arrays.asList(testUserProfile));
        testBookAuthor.setBooks(Arrays.asList(testBook));
    }

    @Test
    void testGetSetAuthorId() {
        testBookAuthor.setAuthorId(2);
        assertEquals(2, testBookAuthor.getAuthorId());
    }

    @Test
    void testGetSetAuthorName() {
        testBookAuthor.setAuthorName("George");
        assertEquals("George", testBookAuthor.getAuthorName());
    }

    @Test
    void testGetSetSelectedProfileUsers() {
        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setProfileId(102);
        testBookAuthor.setSelectedProfileUsers(Arrays.asList(newUserProfile));
        assertEquals(102, testBookAuthor.getSelectedProfileUsers().get(0).getProfileId());
    }

    @Test
    void testGetSetBooks() {
        Book newBook = new Book();
        newBook.setBookId(202);
        testBookAuthor.setBooks(Arrays.asList(newBook));
        assertEquals(202, testBookAuthor.getBooks().get(0).getBookId());
    }
}
