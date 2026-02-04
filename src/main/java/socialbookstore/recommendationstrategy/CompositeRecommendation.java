package socialbookstore.recommendationstrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.UserProfile;
import socialbookstore.mappers.BookMapper;

public class CompositeRecommendation implements BookRecommendationStrategy {
    private BookRecommendationStrategy categoryStrategy;
    private BookRecommendationStrategy authorStrategy;

    public CompositeRecommendation(BookRecommendationStrategy categoryStrategy, BookRecommendationStrategy authorStrategy) {
        this.categoryStrategy = categoryStrategy;
        this.authorStrategy = authorStrategy;
    }

    @Override
    public List<Book> recommendBooks(UserProfile user, BookMapper bookMapper) {
        List<Book> recommendedBooks = new ArrayList<>();
        recommendedBooks.addAll(categoryStrategy.recommendBooks(user,bookMapper));
        recommendedBooks.addAll(authorStrategy.recommendBooks(user,bookMapper));
        return recommendedBooks.stream().distinct().collect(Collectors.toList());
    }
}
