package socialbookstore.domainmodel;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profiles")
@NoArgsConstructor
public class UserProfile  {
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int profileId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "age")
    private int age;
    
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne()
    private User user;
    
    @OneToMany(mappedBy = "owner")
    private List<Book> bookOffers = new ArrayList<>();
    
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "user_requested_books",
        joinColumns = {@JoinColumn(name = "profile_id")},
        inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<Book> requestedBooks = new ArrayList<>();
    
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "favourite_book_authors",
        joinColumns =  {@JoinColumn(name = "profile_id")},
        inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private List<BookAuthor> favouriteBookAuthors = new ArrayList<>();
    
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "user_favourite_categories",
        joinColumns = {@JoinColumn(name = "profile_id")},
        inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<BookCategory> favouriteBookCategories = new ArrayList<>();
    
	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Book> getBookOffers() {
		return bookOffers;
	}

	public void setBookOffers(List<Book> bookOffers) {
		this.bookOffers = bookOffers;
	}

	public List<Book> getRequestedBooks() {
		return requestedBooks;
	}

	public void setRequestedBooks(List<Book> requestedBooks) {
		this.requestedBooks = requestedBooks;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<BookAuthor> getFavouriteBookAuthors() {
		return favouriteBookAuthors;
	}

	public void setFavouriteBookAuthors(List<BookAuthor> favouriteBookAuthors) {
		this.favouriteBookAuthors = favouriteBookAuthors;
	}

	public List<BookCategory> getFavouriteBookCategories() {
		return favouriteBookCategories;
	}

	public void setFavouriteBookCategories(List<BookCategory> favouriteBookCategories) {
		this.favouriteBookCategories = favouriteBookCategories;
	}
}