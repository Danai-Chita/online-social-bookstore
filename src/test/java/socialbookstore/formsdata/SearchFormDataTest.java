package socialbookstore.formsdata;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

class SearchFormDataTest {

    private SearchFormData formData;

    @BeforeEach
    void setUp() {
        formData = new SearchFormData();
        formData.setTitle("testBook");
        formData.setAuthorNames(Arrays.asList("George", "testAuthor"));
        formData.setExactSearch(false);
    }

    @Test
    void testGetSetTitle() {
        formData.setTitle("newTestBook");
        assertEquals("newTestBook", formData.getTitle());
    }

    @Test
    void testGetSetAuthorNames() {
        List<String> newAuthors = Arrays.asList("newAuthor1", "newAuthor2");
        formData.setAuthorNames(newAuthors);
        assertEquals(newAuthors, formData.getAuthorNames());
    }

    @Test
    void testGetSetExactSearch() {
        formData.setExactSearch(true);
        assertTrue(formData.isExactSearch());
    }
}
