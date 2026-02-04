package socialbookstore.domainmodel;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_authors")
@NoArgsConstructor
public class BookAuthor {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "author_id")
    private int authorId;
	
	@JoinColumn(name = "author_name")
    private String authorName;

    
	@ManyToMany(mappedBy = "favouriteBookAuthors")
	private List<UserProfile> selectedProfileUsers = new ArrayList<>();
	
	@ManyToMany(mappedBy = "bookAuthors")
	private List<Book> books = new ArrayList<>();
	
	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public List<UserProfile> getSelectedProfileUsers() {
		return selectedProfileUsers;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public void setSelectedProfileUsers(List<UserProfile> selectedProfileUsers) {
		this.selectedProfileUsers = selectedProfileUsers;
	}
	
}
