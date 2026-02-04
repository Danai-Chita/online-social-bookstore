package socialbookstore.domainmodel;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id", nullable = false)
    private int bookId;
	
	@Column(name = "title",nullable = false)
	private String title;
	
	@Column(name = "summary",nullable = false)
	private String summary;
	
	@ManyToMany(mappedBy = "requestedBooks")
	private List<UserProfile> requestingUsers = new ArrayList<>();
	 
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private UserProfile owner;
	
	 @ManyToMany
	 @JoinTable(
			 name = "book_authors_books",
			 joinColumns =  {@JoinColumn(name = "book_id")},
			 inverseJoinColumns = {@JoinColumn(name = "author_id")}
	 )
	 private List<BookAuthor> bookAuthors = new ArrayList<>();
	
	 @ManyToOne
	 @JoinColumn(name = "book_category_id")
	 private BookCategory bookCategory;
	

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserProfile> getRequestingUsers() {
		return requestingUsers;
	}

	public void setRequestingUsers(List<UserProfile> requestingUsers) {
		this.requestingUsers = requestingUsers;
	}

	public UserProfile getOwner() {
		return owner;
	}

	public void setOwner(UserProfile owner) {
		this.owner = owner;
	}

	public List<BookAuthor> getBookAuthors() {
		return bookAuthors;
	}

	public void setBookAuthors(List<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	public BookCategory getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(BookCategory bookCategory) {
		this.bookCategory = bookCategory;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
