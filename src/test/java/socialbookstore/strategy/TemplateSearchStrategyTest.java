package socialbookstore.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.BookAuthor;
import socialbookstore.domainmodel.BookCategory;
import socialbookstore.formsdata.BookFormData;
import socialbookstore.formsdata.SearchFormData;
import socialbookstore.mappers.BookMapper;
import static org.junit.jupiter.api.Assertions.*;

class TemplateSearchStrategyTest {

    @Mock
    private BookMapper bookMapper;

    private TemplateSearchStrategy strategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Concrete implementation for testing
        strategy = new TemplateSearchStrategy() {
            @Override
            protected List<Book> makeInitialListOfBooks(SearchFormData searchFormData) {
                return bookMapper.findByTitleContaining(searchFormData.getTitle());
            }

            @Override
            protected boolean checkIfAuthorsMatch(SearchFormData searchFormData, Book book) {
                return searchFormData.getAuthorNames().isEmpty() || book.getBookAuthors().stream()
                        .anyMatch(author -> searchFormData.getAuthorNames().contains(author.getAuthorName()));
            }
        };
    }

    @Test
    void testSearch() {
        SearchFormData searchFormData = new SearchFormData();
        searchFormData.setTitle("Harry");
        searchFormData.setAuthorNames(Arrays.asList("J.K"));

        BookAuthor author = new BookAuthor();
        author.setAuthorName("J.K");
        BookCategory category = new BookCategory();
        category.setCategoryName("Fantasy");
        
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setBookAuthors(Arrays.asList(author));
        book.setBookCategory(category);
        book.setSummary("A young wizard...");
        book.setBookId(1);

        when(bookMapper.findByTitleContaining("Harry")).thenReturn(Arrays.asList(book));

        List<BookFormData> result = strategy.search(searchFormData, bookMapper);

        assertEquals(1, result.size());
        assertEquals("Harry Potter", result.get(0).getTitle());
        assertTrue(result.get(0).getAuthorNames().contains("J.K"));
        assertEquals("Fantasy", result.get(0).getCategoryName());
        assertEquals("A young wizard...", result.get(0).getSummary());
        assertEquals(1, result.get(0).getBookId());
    }
}
