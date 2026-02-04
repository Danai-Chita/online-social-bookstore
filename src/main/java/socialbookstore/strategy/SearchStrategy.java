package socialbookstore.strategy;


import socialbookstore.formsdata.BookFormData;
import socialbookstore.formsdata.SearchFormData;
import socialbookstore.mappers.BookMapper;

import java.util.List;

public interface SearchStrategy {
    List<BookFormData> search(SearchFormData searchFormData, BookMapper bookMapper);
}
