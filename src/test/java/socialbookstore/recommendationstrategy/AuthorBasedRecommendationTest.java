package socialbookstore.recommendationstrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.BookAuthor;
import socialbookstore.domainmodel.UserProfile;
import socialbookstore.mappers.BookMapper;

class AuthorBasedRecommendationTest {

    @Mock
    private BookMapper bookMapper;
    @Mock
    private UserProfile user;

    private AuthorBasedRecommendation strategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        strategy = new AuthorBasedRecommendation();
    }

    @Test
    void testRecommendBooks() {
        BookAuthor author = new BookAuthor();
        author.setAuthorName("Author One");
        Book book1 = new Book();
        book1.setTitle("Book One");
        Book book2 = new Book();
        book2.setTitle("Book Two");

        when(user.getFavouriteBookAuthors()).thenReturn(Arrays.asList(author));
        when(bookMapper.findByBookAuthorsContains(any(BookAuthor.class))).thenReturn(Arrays.asList(book1, book2));

        List<Book> recommendations = strategy.recommendBooks(user, bookMapper);

        verify(bookMapper).findByBookAuthorsContains(author);
        assertTrue(recommendations.contains(book1) && recommendations.contains(book2));
        assertEquals(2, recommendations.size());
    }
}
