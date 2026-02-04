package socialbookstore.services;

import socialbookstore.mappers.*;
import socialbookstore.recommendationstrategy.BookRecommendationStrategy;
import socialbookstore.recommendationstrategy.RecommendationStrategyFactory;
import socialbookstore.strategy.ApproximateSearchStrategy;
import socialbookstore.strategy.ExactSearchStrategy;
import socialbookstore.strategy.SearchStrategy;
import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.BookAuthor;
import socialbookstore.domainmodel.BookCategory;
import socialbookstore.domainmodel.User;
import socialbookstore.domainmodel.UserProfile;
import socialbookstore.formsdata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
	@Autowired
    private  UserMapper userMapper;
	@Autowired
	private  BookMapper bookMapper;
	@Autowired
	private  BookAuthorMapper bookAuthorMapper;
	@Autowired
	private  BookCategoryMapper bookCategoryMapper;
	@Autowired
	private  UserProfileMapper userProfileMapper;

	@Autowired
    private ApproximateSearchStrategy approximateSearchStrategy;
    @Autowired
    private ExactSearchStrategy exactSearchStrategy;
    
    public UserProfileServiceImpl() {
    	super();
    }
    
    // Retrieves user profile data for the given username, converting it into a form data object
    @Override
    public UserProfileFormData retrieveProfile(String username) {
    	User user = userMapper.findByUsername(username).orElse(null);
        UserProfile userProfile = userProfileMapper.findById(user.getId()).get();
        
        UserProfileFormData formData = new UserProfileFormData();
        formData.setProfileId(userProfile.getProfileId());
        formData.setFullName(userProfile.getFullName());
        formData.setAddress(userProfile.getAddress());
        formData.setAge(userProfile.getAge());
        formData.setPhoneNumber(userProfile.getPhoneNumber());
        formData.setFavouriteCategory(userProfile.getFavouriteBookCategories());
        formData.setFavouriteAuthors(userProfile.getFavouriteBookAuthors());
        formData.setUserId(userProfile.getUser().getId());
        
        return formData;
    }
    
    // Saves or updates user profile data based on the form data provided
    @Transactional
    public void save(UserProfileFormData userProfileFormData) {
        // Implementation for saving user profile
    	UserProfile userProfile = new UserProfile();
    	User user = userMapper.findById(userProfileFormData.getUserId()).orElse(null);
    	userProfile.setUser(user);
        userProfile.setProfileId(userProfileFormData.getProfileId());
        userProfile.setFullName(userProfileFormData.getFullName());
        userProfile.setAddress(userProfileFormData.getAddress());
        userProfile.setAge(userProfileFormData.getAge());
        userProfile.setPhoneNumber(userProfileFormData.getPhoneNumber());
        userProfile.setFavouriteBookCategories(userProfileFormData.getFavouriteCategory());
        
        // Converts the favourite author names into BookAuthor entities, creating new ones as necessary
        List<BookAuthor> authors = userProfileFormData.getFavouriteAuthors().stream()
                .map(BookAuthor::getAuthorName)
                .filter(authorName -> authorName != null && !authorName.trim().isEmpty())  // Filter out null or empty author names
                .map(this::findOrCreateAuthor)
                .collect(Collectors.toList());
            userProfile.setFavouriteBookAuthors(authors);
        
        
        userProfileMapper.save(userProfile);
    }
    
    // Helper method to find or create a BookAuthor entity by author name
    private BookAuthor findOrCreateAuthor(String authorName) {
        return bookAuthorMapper.findByAuthorName(authorName)
            .orElseGet(() -> {
                BookAuthor newAuthor = new BookAuthor();
                newAuthor.setAuthorName(authorName);
                return bookAuthorMapper.save(newAuthor);
            });
    }
        
    // Retrieves all book offers made by the user specified by username
    @Override
    public List<BookFormData> retrieveBookOffers(String username) {
        User user = userMapper.findByUsername(username).orElse(null);

        UserProfile userProfile = userProfileMapper.findById(user.getId()).get();
        return userProfile.getBookOffers().stream()
                .map(this::convertToBookFormData)
                .collect(Collectors.toList());
    }
    
    // Adds a new book offer based on the form data provided by the user
    @Override
    public void addBookOffer(String username, BookFormData bookFormData) {
        User user = userMapper.findByUsername(username).orElse(null);
        if (bookFormData.getTitle().isBlank() || bookFormData.getAuthorNames().isEmpty() || bookFormData.getSummary().isBlank()) {
            return; // Prevent saving empty titles,authors or summary
        }

        UserProfile userProfile = userProfileMapper.findById(user.getId()).get();
        Book newBook = new Book();
        newBook.setTitle(bookFormData.getTitle());
        newBook.setBookAuthors(bookFormData.getAuthorNames().stream()
                .map(authorName -> bookAuthorMapper.findByAuthorName(authorName).orElse(findOrCreateAuthor(authorName)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        newBook.setBookCategory(bookCategoryMapper.findByCategoryName(bookFormData.getCategoryName()).orElse(null));
        newBook.setOwner(userProfile);
        newBook.setSummary(bookFormData.getSummary());

        bookMapper.save(newBook);
        userProfile.getBookOffers().add(newBook);
        userProfileMapper.save(userProfile);
    }
    
    // Converts a Book entity into a BookFormData object
    private BookFormData convertToBookFormData(Book book) {
        BookFormData formData = new BookFormData();
        formData.setTitle(book.getTitle());
        formData.setBookId(book.getBookId());
        formData.setAuthorNames(book.getBookAuthors().stream().map(BookAuthor::getAuthorName).collect(Collectors.toList()));
        formData.setCategoryName(book.getBookCategory().getCategoryName());
        formData.setSummary(book.getSummary());
        return formData;
    }
    
    // Retrieves all book categories and returns a map of category IDs to names
    @Override
    public Map<Integer, String> getAllCategories() {
        return bookCategoryMapper.findAll().stream()
                .collect(Collectors.toMap(BookCategory::getCategoryId, BookCategory::getCategoryName));
    }
    
    // Performs book search based on the criteria specified in SearchFormData using the selected search strategy
    @Override
    public List<BookFormData> searchBooks(String username,SearchFormData searchFormData) {
        SearchStrategy strategy = searchFormData.isExactSearch() ? exactSearchStrategy : approximateSearchStrategy;
        List<BookFormData> books = strategy.search(searchFormData, bookMapper);
        
        User user = userMapper.findByUsername(username).orElse(null);
        UserProfile userProfile = userProfileMapper.findById(user.getId()).get();
        
        // Enhance book form data with 'owned' and 'requested' status based on user's book offers and requests
        Set<Integer> offeredBookIds = userProfile.getBookOffers().stream()
                                                 .map(Book::getBookId)
                                                 .collect(Collectors.toSet());
        Set<Integer> requestedBookIds = userProfile.getRequestedBooks().stream()
                                                   .map(Book::getBookId)
                                                   .collect(Collectors.toSet());
        
        
        books.forEach(bookFormData -> {
            bookFormData.setOwned(offeredBookIds.contains(bookFormData.getBookId()));
            bookFormData.setRequested(requestedBookIds.contains(bookFormData.getBookId()));
        });
        return books;
    }
    
    // Recommends books based on the user's profile and the selected recommendation strategy
    @Override
    public List<BookFormData> recommendBooks(String username, RecommendationsFormData formData) {
        User user = userMapper.findByUsername(username).orElse(null);
        UserProfile userProfile = userProfileMapper.findById(user.getId()).get();
        
        BookRecommendationStrategy bookRecommendationStrategy = RecommendationStrategyFactory.getStrategy(formData.getRecommendationType());
        
        List<Book> books = bookRecommendationStrategy.recommendBooks(userProfile,bookMapper);
        
        // Collect IDs of books the user has offered and requested for 'owned' and 'requested' status
        Set<Integer> offeredBookIds = userProfile.getBookOffers().stream()
                                                 .map(Book::getBookId)
                                                 .collect(Collectors.toSet());
        Set<Integer> requestedBookIds = userProfile.getRequestedBooks().stream()
                                                   .map(Book::getBookId)
                                                   .collect(Collectors.toSet());
        
        List<BookFormData> booksformdata = books.stream()
                    .map(this::convertToBookFormData)
                    .collect(Collectors.toList());
        
        booksformdata.forEach(bookFormData -> {
            bookFormData.setOwned(offeredBookIds.contains(bookFormData.getBookId()));
            bookFormData.setRequested(requestedBookIds.contains(bookFormData.getBookId()));
        });
        return booksformdata;
    }
    
    // User requests a book; adds the user profile to the requesting users of the book
    @Override
    public void requestBook(int bookId, String username) {
    	User user = userMapper.findByUsername(username).orElse(null);
    	Book book = bookMapper.findById(bookId).orElse(null);
        UserProfile userProfile = userProfileMapper.findById(user.getId()).get();
        if (book != null && userProfile != null) {	
	        book.getRequestingUsers().add(userProfile);
	        bookMapper.save(book);
	        userProfile.getRequestedBooks().add(book);
	        userProfileMapper.save(userProfile);
        }
    }
    
    // Retrieves book requests made by the user; lists all books that have been requested by other users
    @Override
    public List<BookFormData> retrieveBookRequests(String username){
    	User user = userMapper.findByUsername(username).orElse(null);
        UserProfile userProfile = userProfileMapper.findById(user.getId()).get();
    	List<Book> books = bookMapper.findByOwner(userProfile);
    	return books.stream()
    		.filter(book -> book.getRequestingUsers() != null && !book.getRequestingUsers().isEmpty())  // Filte
	        .map(this::convertToBookFormData)
	        .collect(Collectors.toList());
    }
    
    // Retrieves a list of user profiles that have requested a specific book
    @Override
    public List<UserProfileFormData> retrieveRequestingUsers(int bookId) {
        Book book = bookMapper.findByBookId(bookId);
        if (book != null) {
            return book.getRequestingUsers().stream().map(this::convertToUserProfileFormData).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    
    // Converts a UserProfile entity into a UserProfileFormData object
    private UserProfileFormData convertToUserProfileFormData(UserProfile userProfile) {
        UserProfileFormData formData = new UserProfileFormData();
        formData.setProfileId(userProfile.getProfileId());
        formData.setFullName(userProfile.getFullName());
        formData.setAddress(userProfile.getAddress());
        formData.setAge(userProfile.getAge());
        formData.setPhoneNumber(userProfile.getPhoneNumber());
        return formData;
    }
    
    // Deletes a book offer; removes the book from the user's profile and deletes it from the database
    @Override
    public void deleteBookOffer(String username, int bookId) {
        // Implementation for deleting a book offer
    	User user = userMapper.findByUsername(username).orElse(null);
    	UserProfile userProfile = userProfileMapper.findById(user.getId()).get();
        Book book = bookMapper.findById(bookId).orElse(null);
        if (book != null && userProfile != null) {
            // Remove requests from other users
            List <UserProfile> userProf = book.getRequestingUsers();
            for(UserProfile userprof : userProf) {
            	userprof.getRequestedBooks().remove(book);
            	userProfileMapper.save(userprof);
            }
            // Remove the book from the database
            bookMapper.delete(book);
        }
    }
    
    // Deletes a book request; removes the book from all user profiles that have requested it
    @Override
    public void deleteBookRequest(String username, int acceptedProfileId, int bookId) {
    	Book book = bookMapper.findById(bookId).orElse(null);
    	
        List<UserProfile> allRequestors = book.getRequestingUsers();
            
        for(UserProfile userprof : allRequestors) {
        	userprof.getRequestedBooks().remove(book);
        	userProfileMapper.save(userprof);
         }
         bookMapper.delete(book);
    }

}