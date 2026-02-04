package socialbookstore.mappers;

import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.BookAuthor;
import socialbookstore.domainmodel.BookCategory;
import socialbookstore.domainmodel.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper extends JpaRepository<Book, Integer> {
    List<Book> findByTitle(String title); // Exact match assuming case and spacing must match exactly
    List<Book> findByTitleContaining(String title); // For containing specific text
    List<Book> findByOwner(UserProfile owner);
    Book findByBookId(int bookId);
    
    // Find books by category
    List<Book> findByBookCategory(BookCategory category);
    
    // Find books by author
    List<Book> findByBookAuthorsContains(BookAuthor author);
}