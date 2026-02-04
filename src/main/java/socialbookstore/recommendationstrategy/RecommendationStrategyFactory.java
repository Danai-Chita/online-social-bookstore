package socialbookstore.recommendationstrategy;

import org.springframework.stereotype.Component;

@Component
public class RecommendationStrategyFactory {
    public static BookRecommendationStrategy getStrategy(String type) {
        switch (type) {
            case "CATEGORY":
                return new CategoryBasedRecommendation();
            case "AUTHOR":
                return new AuthorBasedRecommendation();
            case "COMPOSITE":
                return new CompositeRecommendation(new CategoryBasedRecommendation(), new AuthorBasedRecommendation());
            default:
                throw new IllegalArgumentException("Unknown strategy type");
        }
    }
}
