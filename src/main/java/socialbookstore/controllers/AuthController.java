package socialbookstore.controllers;
import socialbookstore.formsdata.UserProfileFormData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import socialbookstore.domainmodel.Role;
import socialbookstore.domainmodel.User;
import socialbookstore.services.UserProfileService;
import socialbookstore.services.UserService;


@Controller
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    UserProfileService userProfileService;
    
    @RequestMapping("/login")
    public String login(){
        return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model){
       
        if(userService.isUserPresent(user)){
            model.addAttribute("failedMessage", "User already registered!");
            return "auth/signin";
        }
        
        user.setRole(Role.USER);
        
        userService.saveUser(user);
        //UserProfile userProfile = new UserProfile();
        UserProfileFormData formData = new UserProfileFormData();
        formData.setUserId(user.getId());
        
		userProfileService.save(formData);
        
        model.addAttribute("successMessage", "User registered successfully!");

        return "auth/signin";
    }
}
