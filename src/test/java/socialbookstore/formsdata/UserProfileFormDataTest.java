package socialbookstore.formsdata;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import socialbookstore.domainmodel.BookAuthor;
import socialbookstore.domainmodel.BookCategory;

class UserProfileFormDataTests {

    private UserProfileFormData formData;
    private UserProfileFormData formData2;
    
    @BeforeEach
    void setUp() {
        formData = new UserProfileFormData(1, "Test User", "1234 Ioannina", 30, "1234", 100);
        formData2 = new UserProfileFormData();
        // Setup favourite categories and authors
        BookCategory category = new BookCategory();
        category.setCategoryId(1);
        category.setCategoryName("Fiction");

        BookAuthor author = new BookAuthor();
        author.setAuthorId(1);
        author.setAuthorName("J.K");

        formData.setFavouriteCategory(Arrays.asList(category));
        formData.setFavouriteAuthors(Arrays.asList(author));
    }

    @Test
    void testConstructor() {
        assertEquals(1, formData.getProfileId());
        assertEquals("Test User", formData.getFullName());
        assertEquals("1234 Ioannina", formData.getAddress());
        assertEquals(30, formData.getAge());
        assertEquals("1234", formData.getPhoneNumber());
        assertEquals(100, formData.getUserId());
    }

    @Test
    void testGetSetProfileId() {
        formData.setProfileId(2);
        assertEquals(2, formData.getProfileId());
    }

    @Test
    void testGetSetFullName() {
    	formData2.setFullName("George");
        assertEquals("George", formData2.getFullName());
    }

    @Test
    void testGetSetAddress() {
        formData.setAddress("4321 Ioannina");
        assertEquals("4321 Ioannina", formData.getAddress());
    }

    @Test
    void testGetSetAge() {
        formData.setAge(25);
        assertEquals(25, formData.getAge());
    }

    @Test
    void testGetSetPhoneNumber() {
        formData.setPhoneNumber("5678901234");
        assertEquals("5678901234", formData.getPhoneNumber());
    }

    @Test
    void testGetSetUserId() {
        formData.setUserId(200);
        assertEquals(200, formData.getUserId());
    }

    @Test
    void testGetSetFavouriteCategory() {
        BookCategory newCategory = new BookCategory();
        newCategory.setCategoryId(2);
        newCategory.setCategoryName("Fiction");
        formData.setFavouriteCategory(Arrays.asList(newCategory));
        assertEquals(2, formData.getFavouriteCategory().get(0).getCategoryId());
        assertEquals("Fiction", formData.getFavouriteCategory().get(0).getCategoryName());
    }

    @Test
    void testGetSetFavouriteAuthors() {
        BookAuthor newAuthor = new BookAuthor();
        newAuthor.setAuthorId(2);
        newAuthor.setAuthorName("Tolkien");
        formData.setFavouriteAuthors(Arrays.asList(newAuthor));
        assertEquals(2, formData.getFavouriteAuthors().get(0).getAuthorId());
        assertEquals("Tolkien", formData.getFavouriteAuthors().get(0).getAuthorName());
    }

    @Test
    void testListManagement() {
        // Test to ensure lists are managed correctly even when null
        formData.setFavouriteCategory(null);
        formData.setFavouriteAuthors(null);
        assertNotNull(formData.getFavouriteCategory());
        assertNotNull(formData.getFavouriteAuthors());
        assertTrue(formData.getFavouriteCategory().isEmpty());
        assertTrue(formData.getFavouriteAuthors().isEmpty());
    }
}
