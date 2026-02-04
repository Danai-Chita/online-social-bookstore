package socialbookstore.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import socialbookstore.services.UserProfileService;
import socialbookstore.formsdata.BookFormData;
import socialbookstore.formsdata.RecommendationsFormData;
import socialbookstore.formsdata.SearchFormData;
import socialbookstore.formsdata.UserProfileFormData;

@ExtendWith(SpringExtension.class)
class UserControllerTest {
	
	@InjectMocks
    private UserController controller;
    @Mock
    private UserProfileService userProfileService;

    @Mock
    private Authentication authentication;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(controller).build();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getName()).thenReturn("testUser");
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetUserMainMenuWithNullFullName() throws Exception {
        UserProfileFormData formData = new UserProfileFormData();
        when(userProfileService.retrieveProfile("testUser")).thenReturn(formData);

        mockMvc.perform(get("/user/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/user-informations"));
        
        verify(userProfileService).getAllCategories();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetUserMainMenuWithNonNullFullName() throws Exception {
        UserProfileFormData formData = new UserProfileFormData();
        formData.setFullName("John Doe");
        when(userProfileService.retrieveProfile("testUser")).thenReturn(formData);

        mockMvc.perform(get("/user/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/dashboard"));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testRetrieveProfile() throws Exception {
        UserProfileFormData formData = new UserProfileFormData();
        when(userProfileService.retrieveProfile("testUser")).thenReturn(formData);

        mockMvc.perform(get("/user/retrieveProfile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/user-informations"));
        
        verify(userProfileService).getAllCategories();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testSaveProfile() throws Exception {
        UserProfileFormData formData = new UserProfileFormData();

        mockMvc.perform(post("/user/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("userProfileFormData", formData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"));

        verify(userProfileService).save(any(UserProfileFormData.class));
    }
    
    @Test
    public void testListBookOffers() throws Exception {
        List<BookFormData> bookOffers = new ArrayList<>();
        when(userProfileService.retrieveBookOffers("testUser")).thenReturn(bookOffers);

        mockMvc.perform(get("/user/offers"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list-book-offers"))
                .andExpect(model().attributeExists("bookOffers"));
    }

    @Test
    public void testShowOfferForm() throws Exception {
        mockMvc.perform(get("/user/offer-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add-book-offer"))
                .andExpect(model().attributeExists("bookFormData"));
    }

    @Test
    public void testSaveOffer() throws Exception {
        BookFormData bookFormData = new BookFormData();
        bookFormData.setTitle("New Book");

        mockMvc.perform(post("/user/save-offer")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("bookFormData", bookFormData))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/offers"));

        verify(userProfileService).addBookOffer(eq("testUser"), any(BookFormData.class));
    }
    
    @Test
    public void testShowSearchForm() throws Exception {
        mockMvc.perform(get("/user/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/search-books"))
                .andExpect(model().attributeExists("searchFormData"));
    }

    @Test
    public void testSearchResults() throws Exception {
        SearchFormData searchFormData = new SearchFormData();
        searchFormData.setTitle("Test Book");

        when(userProfileService.searchBooks("testUser", searchFormData)).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/user/search-results")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("searchFormData", searchFormData))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list-search-results"))
                .andExpect(model().attributeExists("searchResults"));
    }

    @Test
    public void testShowRecommendationsForm() throws Exception {
        mockMvc.perform(get("/user/recommendations-search"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/search-reccomendation-books"))
                .andExpect(model().attributeExists("searchFormData"))
                .andExpect(model().attributeExists("recommendationTypes"));
    }

    @Test
    public void testRecommendBooks() throws Exception {
        RecommendationsFormData formData = new RecommendationsFormData();
        formData.setRecommendationType("CATEGORY");

        when(userProfileService.recommendBooks("testUser", formData)).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/user/search-recommendation-results")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("recommendationsFormData", formData))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list-search-results"))
                .andExpect(model().attributeExists("searchResults"));
    }

    @Test
    public void testRequestBook() throws Exception {
        int bookId = 1;
        mockMvc.perform(get("/user/request/" + bookId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/search"));

        verify(userProfileService).requestBook(bookId, "testUser");
    }
    
    @Test
    public void testShowUserBookRequests() throws Exception {
        when(userProfileService.retrieveBookRequests("testUser")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/user/book-requests"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/book-requests"))
                .andExpect(model().attributeExists("bookRequests"));
    }

    @Test
    public void testShowRequestingUsersForBookOffer() throws Exception {
        int bookId = 1;
        when(userProfileService.retrieveRequestingUsers(bookId)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/user/book-offer-requests/" + bookId))
                .andExpect(status().isOk())
                .andExpect(view().name("user/book-offer-requests"))
                .andExpect(model().attributeExists("requestingUsers"));
    }

    @Test
    public void testDeleteBookOffer() throws Exception {
        int bookId = 1;

        mockMvc.perform(get("/user/delete-book-offer/" + bookId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/offers"));

        verify(userProfileService).deleteBookOffer("testUser", bookId);
    }

    @Test
    public void testDeleteBookRequest() throws Exception {
        int bookId = 1;
        int profileId = 2;

        mockMvc.perform(get("/user/delete-book-request/" + bookId + "/" + profileId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/book-requests"));

        verify(userProfileService).deleteBookRequest("testUser", profileId, bookId);
    }
}
