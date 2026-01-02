/**
 * Book Class
 * represents a book entity in the library system.
 * This class stores the details required by the project PDF [Source: 63].
 */
public class Book {
    
    // Attributes as specified in the PDF [Source: 63]
    private int bookID;
    private String title;
    private String author;
    private String status; // Can be "Available" or "Borrowed"

    /**
     * Constructor to initialize a Book object.
     * Time Complexity: O(1)
     * @param bookID Unique identifier for the book
     * @param title Title of the book
     * @param author Author of the book
     * @param status Current status (e.g., "Available")
     */
    public Book(int bookID, String title, String author, String status) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    // --- GETTER METHODS ---

    /**
     * Gets the book ID.
     * Time Complexity: O(1)
     */
    public int getBookID() {
        return bookID;
    }

    /**
     * Gets the book title.
     * Used for BST sorting and searching [Source: 19].
     * Time Complexity: O(1)
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the book author.
     * Time Complexity: O(1)
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets the current status of the book.
     * Time Complexity: O(1)
     */
    public String getStatus() {
        return status;
    }

    // --- SETTER METHODS ---

    /**
     * Updates the status of the book.
     * Crucial for Borrow/Return operations [Source: 35].
     * Time Complexity: O(1)
     * @param status New status (e.g., "Borrowed")
     */
    public void setStatus(String status) {
        this.status = status;
    }

    // --- UTILITY METHODS ---

    /**
     * Returns a string representation of the book.
     * Useful for printing book details to the console or file.
     * Time Complexity: O(1)
     */
    @Override
    public String toString() {
        return "ID: " + bookID + " | Title: " + title + " | Author: " + author + " | Status: " + status;
    }
}