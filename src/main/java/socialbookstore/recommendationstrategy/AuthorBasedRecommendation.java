package socialbookstore.recommendationstrategy;

import java.util.List;
import java.util.stream.Collectors;
import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.UserProfile;
import socialbookstore.mappers.BookMapper;

public class AuthorBasedRecommendation  implements BookRecommendationStrategy {
    
    @Override
    public List<Book> recommendBooks(UserProfile user, BookMapper bookMapper) {
        return user.getFavouriteBookAuthors().stream()
                   .flatMap(author -> bookMapper.findByBookAuthorsContains(author).stream())
                   .distinct()
                   .collect(Collectors.toList());
    }
}
