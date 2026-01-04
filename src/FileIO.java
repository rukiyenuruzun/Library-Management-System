import java.io.*;

/**
 * FileIO Class
 * Handles all file input/output operations for the library system.
 * This class ensures data persistence for books and users 
 */
public class FileIO {

    // File names for persistence storage
    private static final String BOOK_FILE = "books.txt";
    private static final String USER_FILE = "users.txt";

    /**
     * Loads books from the text file and populates the system.
     * Reads line by line, splits data by semicolon, and adds to both Array and BST.
     * Time Complexity: O(n) where n is the number of lines in the file.
     * @param array The DynamicArray to populate 
     * @param bst The Binary Search Tree to populate 
     */
    public static void loadBooks(DynamicArray array, BST bst) {
        File file = new File(BOOK_FILE);
        if (!file.exists()) return; // If file doesn't exist, do nothing

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse the line: ID;Title;Author;Status
                String[] data = line.split(";");
                
                // Ensure the line has all 4 required fields to avoid errors
                if (data.length == 4) {
                    int id = Integer.parseInt(data[0]);
                    String title = data[1];
                    String author = data[2];
                    String status = data[3];

                    Book book = new Book(id, title, author, status);
                    array.add(book);
                    bst.insert(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    /**
     * Saves all books from the DynamicArray back to the text file.
     * Overwrites the existing file to reflect updates (e.g., status changes, new books).
     * Time Complexity: O(n) where n is the number of books.
     * @param array The list of books to save 
     */
    public static void saveBooks(DynamicArray array) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (int i = 0; i < array.size(); i++) {
                Book book = array.get(i);
                if (book != null) {
                    // Format: ID;Title;Author;Status
                    String line = book.getBookID() + ";" + 
                                  book.getTitle() + ";" + 
                                  book.getAuthor() + ";" + 
                                  book.getStatus();
                    bw.write(line);
                    bw.newLine(); // Move to the next line
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    /**
     * Appends a new user or log entry to the user file.
     * Uses append mode (true) so previous data is not lost.
     * Time Complexity: O(1) (Appends to the end of file)
     * @param userName The string data to write to the file
     */
    public static void appendUser(String userName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            bw.write(userName);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
}
