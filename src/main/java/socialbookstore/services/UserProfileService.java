package socialbookstore.services;

import java.util.List;
import java.util.Map;

import socialbookstore.formsdata.BookFormData;
import socialbookstore.formsdata.UserProfileFormData;
import socialbookstore.formsdata.RecommendationsFormData;
import socialbookstore.formsdata.SearchFormData;

public interface UserProfileService {
	UserProfileFormData retrieveProfile(String username);
    void save(UserProfileFormData userProfileFormData);
    List<BookFormData> retrieveBookOffers(String username);
    void addBookOffer(String username, BookFormData bookFormData);
    List<BookFormData> searchBooks(String username,SearchFormData searchFormData);
    List<BookFormData> recommendBooks(String username, RecommendationsFormData recommendationsFormData);
    void requestBook(int bookId, String username);
    List<BookFormData> retrieveBookRequests(String username);
    List<UserProfileFormData> retrieveRequestingUsers(int bookId);
    void deleteBookOffer(String username, int bookId);
    void deleteBookRequest(String username, int acceptedprofileId, int bookId);
    Map<Integer, String> getAllCategories();
}
