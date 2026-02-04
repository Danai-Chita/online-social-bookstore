package socialbookstore.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import socialbookstore.formsdata.BookFormData;
import socialbookstore.formsdata.RecommendationsFormData;
import socialbookstore.formsdata.SearchFormData;
import socialbookstore.formsdata.UserProfileFormData;
import socialbookstore.services.UserProfileService;

@Controller
public class UserController {
	@Autowired
    private UserProfileService userProfileService;
	
	// Helper method to get currently logged user's username
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    //UCS 1-3 
    // Displays the main dashboard for the user if their profile data is complete, otherwise redirects to profile information form
    @RequestMapping("/user/dashboard")
    public String getUserMainMenu(Model model){
        UserProfileFormData formData = userProfileService.retrieveProfile(getCurrentUsername());
        model.addAttribute("userProfileFormData", formData);
        
        if(formData.getFullName() == null) {
        	model.addAttribute("allCategories" ,userProfileService.getAllCategories());
			return "user/user-informations";
		}
        return "user/dashboard";
    }
   
    // Retrieves and displays the user profile information for editing
    @RequestMapping("/user/retrieveProfile")
    public String retrieveProfile(Model model) {
        UserProfileFormData formData = userProfileService.retrieveProfile(getCurrentUsername());
        
        model.addAttribute("userProfileFormData", formData);
        model.addAttribute("allCategories" ,userProfileService.getAllCategories());
    	return "user/user-informations";
    }
    
    // Saves or updates user profile data and redirects to the dashboard
    @RequestMapping("/user/save")
    public String saveProfile(UserProfileFormData userProfileFormData, Model theModel) {
    	userProfileService.save(userProfileFormData);
    	return "redirect:/user/dashboard";
    }
    
    //UCS 4
    // Lists the book offers made by the user
    @RequestMapping("/user/offers")
    public String listBookOffers(Model model) {
        model.addAttribute("bookOffers", userProfileService.retrieveBookOffers(getCurrentUsername()));
        return "user/list-book-offers";
    }
    
    // Shows a form for the user to add a new book offer
    @RequestMapping("/user/offer-form")
    public String showOfferForm(Model model) {
        model.addAttribute("bookFormData", new BookFormData());
        return "user/add-book-offer";
    }
    
    // Saves a new book offer made by the user and redirects to the list of offers
    @RequestMapping("/user/save-offer")
    public String saveOffer(BookFormData bookFormData, Model model) {
    	userProfileService.addBookOffer(getCurrentUsername(),bookFormData);
        return "redirect:/user/offers";
    }
    
    //UCS 10
    // Displays a form for searching books based on user criteria
    @RequestMapping("/user/search")
    public String showSearchForm(Model model) {
        model.addAttribute("searchFormData", new SearchFormData());
        return "user/search-books";
    }
    
    // Displays search results based on user input from the search form
    @RequestMapping("/user/search-results")
    public String search(SearchFormData searchFormData, Model model) {
        model.addAttribute("searchResults", userProfileService.searchBooks(getCurrentUsername(),searchFormData));
        return "user/list-search-results";
    }
    
    //UCS 11
    // Shows a form for searching book recommendations based on user preferences
    @RequestMapping("/user/recommendations-search")
    public String showReccomendationsForm (Model model) {
        model.addAttribute("searchFormData", new RecommendationsFormData());
        model.addAttribute("recommendationTypes", List.of("CATEGORY", "AUTHOR", "COMPOSITE"));
        return "user/search-reccomendation-books";
    }
    
    // Displays recommendation results based on user preferences
    @RequestMapping("/user/search-recommendation-results")
    public String recommendBooks(RecommendationsFormData recommendationsFormData, Model model) {
    	model.addAttribute("searchResults", userProfileService.recommendBooks(getCurrentUsername(),recommendationsFormData));
    	return "user/list-search-results";
    }
    
    // Allows the user to request a book from another user
    @RequestMapping("/user/request/{bookId}")
    public String requestBook(@PathVariable int bookId, Model model) {
    	userProfileService.requestBook(bookId,getCurrentUsername());  
        return "redirect:/user/search";
    }
    
    //UCS 5
    // Displays the list of books requested by the user
    @RequestMapping("/user/book-requests")
    public String showUserBookRequests(Model model) {
        String username = getCurrentUsername(); // Assume a method to fetch the currently logged in user's username
        model.addAttribute("bookRequests", userProfileService.retrieveBookRequests(username));
        return "user/book-requests";
    }
    
    // Shows users requesting a specific book offered by the logged-in user
    @RequestMapping("/user/book-offer-requests/{bookId}")
    public String showRequestingUsersForBookOffer(@PathVariable int bookId, Model model) {
        model.addAttribute("requestingUsers", userProfileService.retrieveRequestingUsers(bookId));
        return "user/book-offer-requests";
    }
    
    // UCS 9
    // Deletes a book offer made by the user
    @RequestMapping("/user/delete-book-offer/{bookId}")
    public String deleteBookOffer(@PathVariable int bookId, Model model) {
    	String username = getCurrentUsername();
        userProfileService.deleteBookOffer(username, bookId);
        return "redirect:/user/offers";
    }
    
    // Deletes a book request made by the user
    @RequestMapping("/user/delete-book-request/{bookId}/{profileId}")
    public String deleteBookRequest(@PathVariable int bookId, @PathVariable int profileId, Model model) {
    	String username = getCurrentUsername();
        userProfileService.deleteBookRequest(username, profileId, bookId);
        return "redirect:/user/book-requests";
    }
}