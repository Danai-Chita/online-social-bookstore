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
import socialbookstore.domainmodel.BookCategory;
import socialbookstore.domainmodel.UserProfile;
import socialbookstore.mappers.BookMapper;

class CategoryBasedRecommendationTest {

    @Mock
    private BookMapper bookMapper;
    @Mock
    private UserProfile user;

    private CategoryBasedRecommendation strategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        strategy = new CategoryBasedRecommendation();
    }

    @Test
    void testRecommendBooks() {
        BookCategory category = new BookCategory();
        category.setCategoryName("Fantasy");
        Book book1 = new Book();
        book1.setTitle("Fantasy Book One");
        Book book2 = new Book();
        book2.setTitle("Fantasy Book Two");

        when(user.getFavouriteBookCategories()).thenReturn(Arrays.asList(category));
        when(bookMapper.findByBookCategory(any(BookCategory.class))).thenReturn(Arrays.asList(book1, book2));

        List<Book> recommendations = strategy.recommendBooks(user, bookMapper);

        verify(bookMapper).findByBookCategory(category);
        assertTrue(recommendations.contains(book1) && recommendations.contains(book2));
        assertEquals(2, recommendations.size());
    }
}
