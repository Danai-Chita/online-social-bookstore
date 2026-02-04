package socialbookstore.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import socialbookstore.domainmodel.User;
import socialbookstore.services.UserService;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    
    @Test
    public void loginPageTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signin"));
    }

    @Test
    public void registerPageTest() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("auth/signup"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) // Ensure the 'USER' role is sufficient and correctly configured
    public void saveNewUserTest() throws Exception {
        // Assuming your /save endpoint needs CSRF token included
        mockMvc.perform(post("/save")
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // Include CSRF token
                .param("username", "testUser")
                .param("password", "password123")
                .param("email", "testUser@example.com") // Use valid email if validated
                .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signin"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) 
    public void saveExistingUserTest() throws Exception {
        when(userService.isUserPresent(any(User.class))).thenReturn(true);
        mockMvc.perform(post("/save")
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // Include CSRF token
                .param("username", "testUser")
                .param("password", "password123")
                .param("email", "testUser@example.com")
                .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("failedMessage"))
                .andExpect(view().name("auth/signin"));
    }
}
