package socialbookstore.domainmodel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;


class UserProfileTests {

    @InjectMocks
    private UserProfile testUserProfile;

    private User testUser;
    private Book testBook;
    private BookAuthor testAuthor;
    private BookCategory testCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUserProfile = new UserProfile();
        
        // Setup User
        testUser = new User();
        testUser.setId(1);
        
        // Setup Book
        testBook = new Book();
        testBook.setBookId(101);
        
        // Setup BookAuthor
        testAuthor = new BookAuthor();
        testAuthor.setAuthorId(201);
        
        // Setup BookCategory
        testCategory = new BookCategory();
        testCategory.setCategoryId(301);
        
        testUserProfile.setUser(testUser);
        testUserProfile.setBookOffers(Arrays.asList(testBook));
        testUserProfile.setRequestedBooks(Arrays.asList(testBook));
        testUserProfile.setFavouriteBookAuthors(Arrays.asList(testAuthor));
        testUserProfile.setFavouriteBookCategories(Arrays.asList(testCategory));
    }

    @Test
    void testGetSetProfileId() {
        testUserProfile.setProfileId(999);
        assertEquals(999, testUserProfile.getProfileId());
    }

    @Test
    void testGetSetFullName() {
        testUserProfile.setFullName("Test User");
        assertEquals("Test User", testUserProfile.getFullName());
    }

    @Test
    void testGetSetAddress() {
        testUserProfile.setAddress("1234 Ioannina");
        assertEquals("1234 Ioannina", testUserProfile.getAddress());
    }

    @Test
    void testGetSetAge() {
        testUserProfile.setAge(30);
        assertEquals(30, testUserProfile.getAge());
    }

    @Test
    void testGetSetPhoneNumber() {
        testUserProfile.setPhoneNumber("6876574");
        assertEquals("6876574", testUserProfile.getPhoneNumber());
    }

    @Test
    void testGetSetUser() {
        User newUser = new User();
        newUser.setId(2);
        testUserProfile.setUser(newUser);
        assertEquals(2, testUserProfile.getUser().getId());
    }

    @Test
    void testGetSetBookOffers() {
        Book newBook = new Book();
        newBook.setBookId(102);
        testUserProfile.setBookOffers(Arrays.asList(newBook));
        assertEquals(102, testUserProfile.getBookOffers().get(0).getBookId());
    }

    @Test
    void testGetSetRequestedBooks() {
        Book newBook = new Book();
        newBook.setBookId(103);
        testUserProfile.setRequestedBooks(Arrays.asList(newBook));
        assertEquals(103, testUserProfile.getRequestedBooks().get(0).getBookId());
    }

    @Test
    void testGetSetFavouriteBookAuthors() {
        BookAuthor newAuthor = new BookAuthor();
        newAuthor.setAuthorId(202);
        testUserProfile.setFavouriteBookAuthors(Arrays.asList(newAuthor));
        assertEquals(202, testUserProfile.getFavouriteBookAuthors().get(0).getAuthorId());
    }

    @Test
    void testGetSetFavouriteBookCategories() {
        BookCategory newCategory = new BookCategory();
        newCategory.setCategoryId(302);
        testUserProfile.setFavouriteBookCategories(Arrays.asList(newCategory));
        assertEquals(302, testUserProfile.getFavouriteBookCategories().get(0).getCategoryId());
    }
}
