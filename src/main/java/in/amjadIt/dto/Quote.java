package in.amjadIt.dto;

public class Quote {
    private String _id;  // Assuming this field is returned by the API
    private String quote; // Changed from 'text' to 'quote' based on API response
    private String author;
    private String source;
    private Integer numberOfVotes;
    private Integer rating;

    // Getters and setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getQuote() {
        return quote;  // Getter for the quote field
    }

    public void setQuote(String quote) {
        this.quote = quote;  // Setter for the quote field
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(Integer numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Quote{" +
               "author='" + author + '\'' +
               ", quote='" + quote + '\'' +  // Make sure 'quote' is displayed here
               '}';
    }
}
