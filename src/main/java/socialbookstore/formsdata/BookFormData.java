package socialbookstore.formsdata;


import java.util.ArrayList;
import java.util.List;

public class BookFormData {
    private String title;
    private List<String> authorNames;  // List of author IDs for simplicity in form handling
    private String categoryName;           // ID of the book category
    private String summary;
    private int bookId;
    private List<String> allAvailableCategories;
    
    private boolean owned;
    private boolean requested;
    
    public BookFormData() {
    	this.allAvailableCategories = new ArrayList<String>();
    	this.allAvailableCategories.add("Art");
    	this.allAvailableCategories.add("Comic");
    	this.allAvailableCategories.add("Fantasy");
    	this.allAvailableCategories.add("Fiction");
    	this.allAvailableCategories.add("Biographies");
    	this.allAvailableCategories.add("Science");
    	this.allAvailableCategories.add("Literature");
    	this.allAvailableCategories.add("Adventure");
    	this.allAvailableCategories.add("Crime");
    	this.allAvailableCategories.add("Other");
    }
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

	public List<String> getAllAvailableCategories() {
		return allAvailableCategories;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public boolean getOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

	public boolean getRequested() {
		return requested;
	}

	public void setRequested(boolean requested) {
		this.requested = requested;
	}
}

