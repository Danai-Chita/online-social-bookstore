package socialbookstore.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.BookAuthor;
import socialbookstore.formsdata.SearchFormData;
import socialbookstore.mappers.BookMapper;

class ApproximateSearchStrategyTest {

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private ApproximateSearchStrategy strategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMakeInitialListOfBooks() {
        SearchFormData searchFormData = new SearchFormData();
        searchFormData.setTitle("test");

        Book book1 = new Book();
        book1.setTitle("testBook");

        when(bookMapper.findByTitleContaining("test")).thenReturn(Arrays.asList(book1));

        List<Book> books = strategy.makeInitialListOfBooks(searchFormData);
        assertTrue(books.contains(book1));
        verify(bookMapper).findByTitleContaining("test");
    }

    @Test
    void testCheckIfAuthorsMatch() {
        SearchFormData searchFormData = new SearchFormData();
        searchFormData.setAuthorNames(Arrays.asList("Author One"));

        Book book = new Book();
        BookAuthor author = new BookAuthor();
        author.setAuthorName("Author One");
        book.setBookAuthors(Arrays.asList(author));

        assertTrue(strategy.checkIfAuthorsMatch(searchFormData, book));
    }
}
