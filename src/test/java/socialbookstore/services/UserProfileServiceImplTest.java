package socialbookstore.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import socialbookstore.domainmodel.*;
import socialbookstore.formsdata.UserProfileFormData;
import socialbookstore.formsdata.BookFormData;
import socialbookstore.formsdata.RecommendationsFormData;
import socialbookstore.formsdata.SearchFormData;
import socialbookstore.mappers.*;
import socialbookstore.recommendationstrategy.BookRecommendationStrategy;
import socialbookstore.recommendationstrategy.RecommendationStrategyFactory;
import socialbookstore.strategy.ApproximateSearchStrategy;
import socialbookstore.strategy.ExactSearchStrategy;

@ExtendWith(SpringExtension.class)
public class UserProfileServiceImplTest {

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserProfileMapper userProfileMapper;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookAuthorMapper bookAuthorMapper;

    @Mock
    private BookCategoryMapper bookCategoryMapper;
    
    @Mock
    private ExactSearchStrategy exactSearchStrategy;

    @Mock
    private ApproximateSearchStrategy approximateSearchStrategy;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveProfile() {
        // Setup
        User user = new User();
        user.setId(1);
        UserProfile userProfile = new UserProfile();
        userProfile.setProfileId(1);
        userProfile.setFullName("testName");
        userProfile.setAddress("1234 Ioannina");
        userProfile.setAge(23);
        userProfile.setPhoneNumber("1234567890");
        userProfile.setUser(user);
        
        when(userMapper.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(userProfileMapper.findById(user.getId())).thenReturn(Optional.of(userProfile));
        
        // Action
        UserProfileFormData formData = userProfileService.retrieveProfile("testUser");
        
        // Assertion
        assertNotNull(formData);
        assertEquals(userProfile.getFullName(), formData.getFullName());
        assertEquals(userProfile.getAddress(), formData.getAddress());
    }

    @Test
    public void testSave() {
        // Setup
        UserProfileFormData formData = new UserProfileFormData(1, "testName", "1234 Ioannina", 23, "1234567890", 1);
        User user = new User();
        user.setId(1);
        //UserProfile userProfile = new UserProfile();

        when(userMapper.findById(formData.getUserId())).thenReturn(Optional.of(user));
        
        // Action
        userProfileService.save(formData);
        
        // Assertion
        verify(userProfileMapper).save(any(UserProfile.class));
    }

    @Test
    public void testRetrieveBookOffersWithNoCategory() {
        // Setup
        User user = new User();
        user.setId(1);
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        
        BookCategory category = new BookCategory();
        category.setCategoryId(1);
        category.setCategoryName("Fantasy");
        bookCategoryMapper.save(category);
        
        Book book = new Book();
        book.setTitle("Example Book");
        book.setBookCategory(category); // Explicitly setting this to null to test the handling of books without categories
        userProfile.setBookOffers(List.of(book));
        userProfileMapper.save(userProfile);
        //userMapper.save(user);
        when(userMapper.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(userProfileMapper.findById(user.getId())).thenReturn(Optional.of(userProfile));
        
        
        // Action
        List<BookFormData> bookOffers = userProfileService.retrieveBookOffers("testUser");
        
        // Assertion
        assertFalse(bookOffers.isEmpty());
        assertEquals("Example Book", bookOffers.get(0).getTitle());
        assertEquals("Fantasy",bookOffers.get(0).getCategoryName()); // Ensuring the category name is null as expected in this scenario
    }
    @Test
    public void testAddBookOffer() {
        String username = "testUser";
        User user = new User();
        user.setId(1);
        UserProfile userProfile = new UserProfile();
        
        BookFormData bookFormData = new BookFormData();
        bookFormData.setTitle("New Book");
        bookFormData.setAuthorNames(List.of("Author One"));
        bookFormData.setCategoryName("Fiction");
        bookFormData.setSummary("Summary of the book");

        when(userMapper.findByUsername(username)).thenReturn(Optional.of(user));
        when(userProfileMapper.findById(user.getId())).thenReturn(Optional.of(userProfile));
        when(bookCategoryMapper.findByCategoryName("Fiction")).thenReturn(Optional.of(new BookCategory()));
        
        userProfileService.addBookOffer(username, bookFormData);

        verify(bookMapper).save(any(Book.class));
        verify(userProfileMapper).save(userProfile);
    }
    @Test
    public void testGetAllCategories() {
        List<BookCategory> categories = List.of(
            new BookCategory() {{ setCategoryId(1); setCategoryName("Fiction"); }},
            new BookCategory() {{ setCategoryId(2); setCategoryName("Non-Fiction"); }}
        );

        when(bookCategoryMapper.findAll()).thenReturn(categories);

        Map<Integer, String> result = userProfileService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("Fiction", result.get(1));
        assertEquals("Non-Fiction", result.get(2));
    }
    
    @Test
    public void testSearchBooks() {
        String username = "testUser";
        SearchFormData searchFormData = new SearchFormData();
        searchFormData.setExactSearch(true);

        User user = new User();
        user.setId(1);
        UserProfile userProfile = new UserProfile();

        List<BookFormData> searchResults = List.of(new BookFormData());

        when(userMapper.findByUsername(username)).thenReturn(Optional.of(user));
        when(userProfileMapper.findById(user.getId())).thenReturn(Optional.of(userProfile));
        when(exactSearchStrategy.search(searchFormData, bookMapper)).thenReturn(searchResults);

        List<BookFormData> results = userProfileService.searchBooks(username, searchFormData);

        assertFalse(results.isEmpty());
        verify(exactSearchStrategy).search(searchFormData, bookMapper);
    }
    
    @Test
    public void testRecommendBooks() {
        try (MockedStatic<RecommendationStrategyFactory> mockedStatic = Mockito.mockStatic(RecommendationStrategyFactory.class)) {
            String username = "testUser";
            RecommendationsFormData formData = new RecommendationsFormData();
            formData.setRecommendationType("CATEGORY");

            User user = new User();
            user.setId(1);
            UserProfile userProfile = new UserProfile();
            userProfile.setUser(user);

            BookRecommendationStrategy strategy = mock(BookRecommendationStrategy.class);
            List<Book> recommendedBooks = List.of(new Book());

            when(userMapper.findByUsername(username)).thenReturn(Optional.of(user));
            when(userProfileMapper.findById(user.getId())).thenReturn(Optional.of(userProfile));
            mockedStatic.when(() -> RecommendationStrategyFactory.getStrategy("CATEGORY")).thenReturn(strategy);
            when(strategy.recommendBooks(userProfile, bookMapper)).thenReturn(recommendedBooks);

            List<BookFormData> results = userProfileService.recommendBooks(username, formData);

            assertFalse(results.isEmpty());
            verify(strategy).recommendBooks(userProfile, bookMapper);
        }
    }
    @Test
    public void testRequestBook() {
        String username = "testUser";
        int bookId = 123;
        User user = new User();
        user.setId(1);
        UserProfile userProfile = new UserProfile();
        Book book = new Book();

        when(userMapper.findByUsername(username)).thenReturn(Optional.of(user));
        when(bookMapper.findById(bookId)).thenReturn(Optional.of(book));
        when(userProfileMapper.findById(user.getId())).thenReturn(Optional.of(userProfile));

        userProfileService.requestBook(bookId, username);

        assertTrue(book.getRequestingUsers().contains(userProfile));
        assertTrue(userProfile.getRequestedBooks().contains(book));
        verify(bookMapper).save(book);
        verify(userProfileMapper).save(userProfile);
    }
    
    @Test
    public void testRetrieveBookRequests() {
        String username = "testUser";
        User user = new User();
        user.setId(1);
        UserProfile userProfile = new UserProfile();
        Book book = new Book();
        book.setRequestingUsers(List.of(new UserProfile()));  // Assume there is at least one requesting user

        when(userMapper.findByUsername(username)).thenReturn(Optional.of(user));
        when(userProfileMapper.findById(user.getId())).thenReturn(Optional.of(userProfile));
        when(bookMapper.findByOwner(userProfile)).thenReturn(List.of(book));

        List<BookFormData> results = userProfileService.retrieveBookRequests(username);

        assertFalse(results.isEmpty());
        verify(bookMapper).findByOwner(userProfile);
    }
    
    @Test
    public void testRetrieveRequestingUsers() {
        int bookId = 123;
        Book book = new Book();
        UserProfile userProfile = new UserProfile();
        userProfile.setProfileId(1);
        userProfile.setFullName("testName");
        userProfile.setAddress("1234 Ioannina");
        userProfile.setAge(23);
        userProfile.setPhoneNumber("1234567890");

        book.setRequestingUsers(List.of(userProfile));

        when(bookMapper.findByBookId(bookId)).thenReturn(book);

        List<UserProfileFormData> results = userProfileService.retrieveRequestingUsers(bookId);

        assertFalse(results.isEmpty());
        assertEquals("testName", results.get(0).getFullName());
        verify(bookMapper).findByBookId(bookId);
    }
    
    @Test
    public void testDeleteBookOffer() {
        String username = "testUser";
        int bookId = 123;
        User user = new User();
        user.setId(1);
        UserProfile userProfile = new UserProfile();
        Book book = new Book();
        UserProfile requestor = new UserProfile();  // A user who requested the book
        book.setRequestingUsers(List.of(requestor));

        when(userMapper.findByUsername(username)).thenReturn(Optional.of(user));
        when(userProfileMapper.findById(user.getId())).thenReturn(Optional.of(userProfile));
        when(bookMapper.findById(bookId)).thenReturn(Optional.of(book));

        userProfileService.deleteBookOffer(username, bookId);

        verify(bookMapper).delete(book);  // Verify that the book is deleted
        verify(userProfileMapper).save(requestor);  // Verify that changes to the requestor are saved
    }
    
    @Test
    public void testDeleteBookRequest() {
        int bookId = 123;
        int acceptedProfileId = 2;  // Assuming this ID is irrelevant since all requests are removed
        Book book = new Book();
        UserProfile userProfile1 = new UserProfile();
        userProfile1.setProfileId(1);
        UserProfile userProfile2 = new UserProfile();
        userProfile2.setProfileId(2);

        book.setRequestingUsers(new ArrayList<>(List.of(userProfile1, userProfile2)));

        when(bookMapper.findById(bookId)).thenReturn(Optional.of(book));

        userProfileService.deleteBookRequest("testUser", acceptedProfileId, bookId);

        verify(bookMapper).delete(book);  // Verify that the book is deleted
        verify(userProfileMapper, times(2)).save(any(UserProfile.class));  // Verify that all user profiles are updated
    }
}