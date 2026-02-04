package socialbookstore.domainmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;

class BookCategoryTests {

    @InjectMocks
    private BookCategory testBookCategory;

    private UserProfile testUserProfile;
    private Book testBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testBookCategory = new BookCategory();
        testBookCategory.setCategoryId(1);
        testBookCategory.setCategoryName("Fantasy");

        // Setup UserProfile
        testUserProfile = new UserProfile();
        testUserProfile.setProfileId(101);

        // Setup Book
        testBook = new Book();
        testBook.setBookId(201);

        // Set relationships
        testBookCategory.setFavouriteUsersCategory(Arrays.asList(testUserProfile));
        testBookCategory.setBooks(Arrays.asList(testBook));
    }

    @Test
    void testGetSetCategoryId() {
        testBookCategory.setCategoryId(2);
        assertEquals(2, testBookCategory.getCategoryId());
    }

    @Test
    void testGetSetCategoryName() {
        testBookCategory.setCategoryName("Fiction");
        assertEquals("Fiction", testBookCategory.getCategoryName());
    }

    @Test
    void testGetSetFavouriteUsersCategory() {
        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setProfileId(102);
        testBookCategory.setFavouriteUsersCategory(Arrays.asList(newUserProfile));
        assertEquals(102, testBookCategory.getFavouriteUsersCategory().get(0).getProfileId());
    }

    @Test
    void testGetSetBooks() {
        Book newBook = new Book();
        newBook.setBookId(202);
        testBookCategory.setBooks(Arrays.asList(newBook));
        assertEquals(202, testBookCategory.getBooks().get(0).getBookId());
    }
}
