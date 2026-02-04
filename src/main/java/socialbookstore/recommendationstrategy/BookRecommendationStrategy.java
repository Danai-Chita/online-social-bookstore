package socialbookstore.recommendationstrategy;

import java.util.List;

import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.UserProfile;
import socialbookstore.mappers.BookMapper;

public interface BookRecommendationStrategy {
    List<Book> recommendBooks(UserProfile user, BookMapper bookMapper);
}