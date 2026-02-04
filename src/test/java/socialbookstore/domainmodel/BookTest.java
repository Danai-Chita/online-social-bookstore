package socialbookstore.domainmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;

class BookTests {

    @InjectMocks
    private Book testBook;

    private UserProfile testUserProfile;
    private BookAuthor testAuthor;
    private BookCategory testCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testBook = new Book();
        testBook.setBookId(1);
        testBook.setTitle("Harry Potter");
        testBook.setSummary("A young wizard adventures at Hogwarts.");

        // Setup UserProfile
        testUserProfile = new UserProfile();
        testUserProfile.setProfileId(101);
        testBook.setOwner(testUserProfile);

        // Setup BookAuthor
        testAuthor = new BookAuthor();
        testAuthor.setAuthorId(201);
        testBook.setBookAuthors(Arrays.asList(testAuthor));

        // Setup BookCategory
        testCategory = new BookCategory();
        testCategory.setCategoryId(301);
        testBook.setBookCategory(testCategory);

        // Setup Requesting Users
        UserProfile requestingUser = new UserProfile();
        requestingUser.setProfileId(102);
        testBook.setRequestingUsers(Arrays.asList(requestingUser));
    }

    @Test
    void testGetSetBookId() {
        testBook.setBookId(2);
        assertEquals(2, testBook.getBookId());
    }

    @Test
    void testGetSetTitle() {
        testBook.setTitle("The Hobbit");
        assertEquals("The Hobbit", testBook.getTitle());
    }

    @Test
    void testGetSetSummary() {
        testBook.setSummary("An unexpected journey of a hobbit.");
        assertEquals("An unexpected journey of a hobbit.", testBook.getSummary());
    }

    @Test
    void testGetSetOwner() {
        UserProfile newOwner = new UserProfile();
        newOwner.setProfileId(103);
        testBook.setOwner(newOwner);
        assertEquals(103, testBook.getOwner().getProfileId());
    }

    @Test
    void testGetSetBookAuthors() {
        BookAuthor newAuthor = new BookAuthor();
        newAuthor.setAuthorId(202);
        testBook.setBookAuthors(Arrays.asList(newAuthor));
        assertEquals(202, testBook.getBookAuthors().get(0).getAuthorId());
    }

    @Test
    void testGetSetBookCategory() {
        BookCategory newCategory = new BookCategory();
        newCategory.setCategoryId(302);
        testBook.setBookCategory(newCategory);
        assertEquals(302, testBook.getBookCategory().getCategoryId());
    }

    @Test
    void testGetSetRequestingUsers() {
        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setProfileId(104);
        testBook.setRequestingUsers(Arrays.asList(newUserProfile));
        assertEquals(104, testBook.getRequestingUsers().get(0).getProfileId());
    }
}
