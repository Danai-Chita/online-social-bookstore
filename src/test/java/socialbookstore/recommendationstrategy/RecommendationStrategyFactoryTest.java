package socialbookstore.recommendationstrategy;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecommendationStrategyFactoryTest {

    @Test
    void testGetStrategyAuthor() {
        assertTrue(RecommendationStrategyFactory.getStrategy("AUTHOR") instanceof AuthorBasedRecommendation);
    }

    @Test
    void testGetStrategyCategory() {
        assertTrue(RecommendationStrategyFactory.getStrategy("CATEGORY") instanceof CategoryBasedRecommendation);
    }

    @Test
    void testGetStrategyComposite() {
        assertTrue(RecommendationStrategyFactory.getStrategy("COMPOSITE") instanceof CompositeRecommendation);
    }

    @Test
    void testGetStrategyInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            RecommendationStrategyFactory.getStrategy("NON_EXISTENT");
        });
        assertEquals("Unknown strategy type", exception.getMessage());
    }
}
