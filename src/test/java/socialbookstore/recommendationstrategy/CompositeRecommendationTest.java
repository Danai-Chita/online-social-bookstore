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
import socialbookstore.domainmodel.UserProfile;
import socialbookstore.mappers.BookMapper;

class CompositeRecommendationTest {

    @Mock
    private BookMapper bookMapper;
    @Mock
    private UserProfile user;
    private BookRecommendationStrategy categoryStrategy;
    private BookRecommendationStrategy authorStrategy;

    private CompositeRecommendation strategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryStrategy = mock(BookRecommendationStrategy.class);
        authorStrategy = mock(BookRecommendationStrategy.class);
        strategy = new CompositeRecommendation(categoryStrategy, authorStrategy);
    }

    @Test
    void testRecommendBooks() {
        Book book1 = new Book();
        book1.setTitle("Category Book");
        Book book2 = new Book();
        book2.setTitle("Author Book");
        when(categoryStrategy.recommendBooks(any(), any())).thenReturn(Arrays.asList(book1));
        when(authorStrategy.recommendBooks(any(), any())).thenReturn(Arrays.asList(book2));

        List<Book> recommendations = strategy.recommendBooks(user, bookMapper);

        verify(categoryStrategy).recommendBooks(user, bookMapper);
        verify(authorStrategy).recommendBooks(user, bookMapper);
        assertTrue(recommendations.contains(book1) && recommendations.contains(book2));
        assertEquals(2, recommendations.size());
    }
}
