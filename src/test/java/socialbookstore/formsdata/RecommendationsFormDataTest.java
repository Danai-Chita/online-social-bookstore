package socialbookstore.formsdata;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecommendationsFormDataTests {

    private RecommendationsFormData formData;

    @BeforeEach
    void setUp() {
        formData = new RecommendationsFormData();
        formData.setRecommendationType("CATEGORY");
    }

    @Test
    void testGetSetRecommendationType() {
        // Test setting and getting recommendation type
        formData.setRecommendationType("CATEGORY");
        assertEquals("CATEGORY", formData.getRecommendationType());
    }
}
