package socialbookstore.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import socialbookstore.domainmodel.Book;
import socialbookstore.formsdata.SearchFormData;
import socialbookstore.mappers.BookMapper;

@Component
public class ExactSearchStrategy extends TemplateSearchStrategy {
	@Autowired
	private BookMapper bookMapper;
	
	protected List<Book> makeInitialListOfBooks(SearchFormData searchFormData){
		 List <Book> books = bookMapper.findByTitle(searchFormData.getTitle());
		 return books;
		
	 }
	 protected boolean checkIfAuthorsMatch(SearchFormData searchFormData, Book book) {
		 return searchFormData.getAuthorNames().isEmpty() || book.getBookAuthors().stream()
		            .allMatch(author -> searchFormData.getAuthorNames().contains(author.getAuthorName()));
	 }

}
