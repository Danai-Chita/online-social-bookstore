package socialbookstore.recommendationstrategy;

import java.util.List;
import java.util.stream.Collectors;
import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.UserProfile;
import socialbookstore.mappers.BookMapper;

public class CategoryBasedRecommendation implements BookRecommendationStrategy {
    
	@Override
    public List<Book> recommendBooks(UserProfile user,BookMapper bookMapper) {
        return user.getFavouriteBookCategories().stream()
                   .flatMap(category -> bookMapper.findByBookCategory(category).stream())
                   .distinct()
                   .collect(Collectors.toList());
    }
}
