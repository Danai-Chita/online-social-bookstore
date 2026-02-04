package socialbookstore.domainmodel;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_categories")
@NoArgsConstructor
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "category_id")
    private int categoryId;
    
    @JoinColumn(name = "category_name")
    private String categoryName;
    
    @ManyToMany(mappedBy = "favouriteBookCategories")
	private List<UserProfile> favouriteUsersCategory = new ArrayList<>();
    
    @OneToMany(mappedBy = "bookCategory")
    private List<Book> books = new ArrayList<>();

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<UserProfile> getFavouriteUsersCategory() {
		return favouriteUsersCategory;
	}

	public void setFavouriteUsersCategory(List<UserProfile> favouriteUsersCategory) {
		this.favouriteUsersCategory = favouriteUsersCategory;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}