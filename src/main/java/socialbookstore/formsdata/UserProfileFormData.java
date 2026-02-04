package socialbookstore.formsdata;

import java.util.ArrayList;
import java.util.List;

import socialbookstore.domainmodel.BookAuthor;
import socialbookstore.domainmodel.BookCategory;

public class UserProfileFormData {
    private int profileId;
    private String fullName;
    private String address;
    private int age;
    private String phoneNumber;
    private int userId;
    private List<BookCategory> favouriteCategory = new ArrayList<>(); 
    private List<BookAuthor> favouriteAuthors = new ArrayList<>(); 
    
    public UserProfileFormData() {
    }

    public UserProfileFormData(int profileId, String fullName, String address, int age, String phoneNumber, int userId) {
        this.profileId = profileId;
        this.fullName = fullName;
        this.address = address;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    // Getters
    public int getProfileId() {
        return profileId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    // Setters
    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public List<BookCategory> getFavouriteCategory() {
        if (favouriteCategory == null) {
            favouriteCategory = new ArrayList<>();
        }
        return favouriteCategory;
    }

    public void setFavouriteCategory(List<BookCategory> favouriteCategory) {
        this.favouriteCategory = favouriteCategory;
    }

    public List<BookAuthor> getFavouriteAuthors() {
        if (favouriteAuthors == null) {
            favouriteAuthors = new ArrayList<>();
        }
        return favouriteAuthors;
    }

    public void setFavouriteAuthors(List<BookAuthor> favouriteAuthors) {
        this.favouriteAuthors = favouriteAuthors;
    }

}

