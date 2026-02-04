package socialbookstore.formsdata;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

class BookFormDataTest {

    private BookFormData formData;

    @BeforeEach
    void setUp() {
        formData = new BookFormData();
        formData.setBookId(1234);
        formData.setTitle("testBook");
        formData.setSummary("This is an example test summary.");
        formData.setCategoryName("Fantasy");
        formData.setAuthorNames(Arrays.asList("George", "testAuthor"));
        formData.setOwned(true);
        formData.setRequested(false);
    }

    @Test
    void testGetSetTitle() {
        formData.setTitle("newTestBook");
        assertEquals("newTestBook", formData.getTitle());
    }

    @Test
    void testGetSetAuthorNames() {
        formData.setAuthorNames(Arrays.asList("newAuthor1", "newAuthor2"));
        assertEquals(Arrays.asList("newAuthor1", "newAuthor2"), formData.getAuthorNames());
    }

    @Test
    void testGetSetCategoryName() {
        formData.setCategoryName("Science");
        assertEquals("Science", formData.getCategoryName());
    }

    @Test
    void testGetSetSummary() {
        formData.setSummary("New summary for the book.");
        assertEquals("New summary for the book.", formData.getSummary());
    }

    @Test
    void testGetSetBookId() {
        formData.setBookId(5678);
        assertEquals(5678, formData.getBookId());
    }

    @Test
    void testGetSetOwned() {
        formData.setOwned(false);
        assertFalse(formData.getOwned());
    }

    @Test
    void testGetSetRequested() {
        formData.setRequested(true);
        assertTrue(formData.getRequested());
    }

    @Test
    void testGetSetAllAvailableCategories() {
        assertNotNull(formData.getAllAvailableCategories());
        assertTrue(formData.getAllAvailableCategories().contains("Fantasy"));
        assertTrue(formData.getAllAvailableCategories().contains("Science"));
    }
}
