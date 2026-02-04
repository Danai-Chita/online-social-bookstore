package socialbookstore.strategy;

import socialbookstore.domainmodel.Book;
import socialbookstore.domainmodel.BookAuthor;
import socialbookstore.formsdata.BookFormData;
import socialbookstore.formsdata.SearchFormData;
import socialbookstore.mappers.BookMapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class TemplateSearchStrategy implements SearchStrategy {
	public TemplateSearchStrategy(){
		
	}
    @Override
    public List<BookFormData> search(SearchFormData searchFormData, BookMapper bookMapper) {
    	 List<Book> books = makeInitialListOfBooks(searchFormData);
         return books.stream()
                     .filter(book -> checkIfAuthorsMatch(searchFormData, book))
                     .map(this::convertToBookFormData)
                     .collect(Collectors.toList());
    }
    
    protected abstract List<Book> makeInitialListOfBooks(SearchFormData searchFormData);
    
    protected abstract boolean checkIfAuthorsMatch(SearchFormData searchFormData, Book book);
    
    private BookFormData convertToBookFormData(Book book) {
        BookFormData formData = new BookFormData();
        formData.setTitle(book.getTitle());
        formData.setAuthorNames(book.getBookAuthors().stream().map(BookAuthor::getAuthorName).collect(Collectors.toList()));
        formData.setCategoryName(book.getBookCategory().getCategoryName());
        formData.setSummary(book.getSummary());
        formData.setBookId(book.getBookId());
        return formData;
    }
}
