/**
 * Library Class
 * Manages the entire system by integrating DynamicArray, BST, Queue, and Stack.
 * [cite_start]Handles File I/O for persistence via helper class[cite: 67, 86].
 */
public class Library {

    // --- Data Structures ---
    private DynamicArray catalog;   // Main list to store books (Array) [cite: 10]
    private BST bookTree;           // BST for title-based searching [cite: 21]
    private Queue borrowRequests;   // Queue for managing waiting list [cite: 28]
    private Stack undoStack;        // Stack for undoing operations [cite: 36]

    private int lastBookID = 100;   // Counter for auto-generating IDs

    /**
     * Constructor
     * Initializes all data structures and loads existing data from files.
     */
    public Library() {
        catalog = new DynamicArray();
        bookTree = new BST();
        borrowRequests = new Queue();
        undoStack = new Stack();

        // 1. Load existing books from the file
        FileIO.loadBooks(catalog, bookTree);
        
        // 2. Update the ID counter to prevent duplicates
        updateLastID(); 
    }

    /**
     * Updates the lastBookID based on the highest ID currently in the catalog.
     * Prevents duplicate IDs when restarting the program.
     */
    private void updateLastID() {
        for (int i = 0; i < catalog.size(); i++) {
            Book b = catalog.get(i);
            if (b.getBookID() > lastBookID) {
                lastBookID = b.getBookID();
            }
        }
    }

    /**
     * Adds a new book to the system (Array + BST) and saves to file.
     * Time Complexity: O(n) due to array resize possibility
     */
    public void addBook(String title, String author) {
        lastBookID++; // Increment ID
        // RULE: New books are always initialized as "Available"
        Book newBook = new Book(lastBookID, title, author, "Available");

        catalog.add(newBook);       // Add to Dynamic Array 
        bookTree.insert(newBook);   // Insert into BST 
        
        // Save changes to the file
        FileIO.saveBooks(catalog);

        System.out.println("Book added successfully: " + title + " (ID: " + lastBookID + ")");
    }

    /**
     * Removes a book from the catalog by ID and updates file.
     * Time Complexity: O(n)
     */
    public void removeBook(int id) {
        for (int i = 0; i < catalog.size(); i++) {
            Book b = catalog.get(i);
            if (b.getBookID() == id) {
                catalog.remove(i); 
                
                // Update the file to reflect removal
                FileIO.saveBooks(catalog);
                
                // Note: Deletion from BST is complex and omitted for this project scope.
                // The book is effectively removed from the main catalog.
                System.out.println("Book removed successfully.");
                return;
            }
        }
        System.out.println("Book not found with ID: " + id);
    }

    /**
     * Searches for a book by ID using the Dynamic Array.
     * Time Complexity: O(n)
     */
    public void searchByID(int id) {
        for (int i = 0; i < catalog.size(); i++) {
            Book b = catalog.get(i);
            if (b.getBookID() == id) {
                System.out.println("Found: " + b);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    /**
     * Searches for a book by Title using BST.
     * Time Complexity: O(log n) average
     */
    public void searchByTitle(String title) {
        Book b = bookTree.search(title);
        if (b != null) {
            System.out.println("Found in BST: " + b);
        } else {
            System.out.println("Book not found in BST.");
        }
    }

    /**
     * Lists all books using Dynamic Array (Order of addition).
     * Time Complexity: O(n)
     */
    public void listAllBooks() {
        System.out.println("\n--- All Books (Catalog Order) ---");
        if (catalog.size() == 0) System.out.println("Library is empty.");
        
        for (int i = 0; i < catalog.size(); i++) {
            System.out.println(catalog.get(i));
        }
    }

    /**
     * Lists all books alphabetically using BST In-Order Traversal.
     * Time Complexity: O(n)
     */
    public void listAllBooksAlphabetical() {
        System.out.println("\n--- All Books (Alphabetical Order) ---");
        bookTree.inOrderTraversal(); 
    }

    /**
     * Adds a borrow request to the queue and saves user to file.
     * Time Complexity: O(1)
     */
    public void requestBook(String userName, int bookID) {
        // Enqueue the request
        borrowRequests.enqueue(userName, bookID); 
        
        // Log the user request to the database/file
        FileIO.appendUser(userName + ";Requested Book ID: " + bookID);
        
        System.out.println("Request added to waiting list for user: " + userName);
    }

    /**
     * Processes the next borrow request in the queue.
     * Time Complexity: O(1)
     */
    public void processQueue() {
        // Retrieve the next request object from the queue
        Object req = borrowRequests.dequeue(); 
        
        if (req != null) {
            System.out.println("Processing request: " + req.toString());
            // Logic to automatically borrow the book can be implemented here.
        } else {
            System.out.println("No waiting requests.");
        }
    }

    /**
     * Borrows a book (Changes status to Borrowed).
     * Time Complexity: O(n) to find book
     */
    public void borrowBook(int id) {
        for (int i = 0; i < catalog.size(); i++) {
            Book b = catalog.get(i);
            if (b.getBookID() == id) {
                if (b.getStatus().equalsIgnoreCase("Available")) {
                    b.setStatus("Borrowed");
                    
                    // Push action to Stack for Undo capability
                    undoStack.push("BORROW", id);
                    
                    // Save updates to file
                    FileIO.saveBooks(catalog);
                    
                    System.out.println("You borrowed: " + b.getTitle());
                } else {
                    System.out.println("Book is already borrowed.");
                }
                return;
            }
        }
        System.out.println("Book not found.");
    }

    /**
     * Returns a book (Changes status to Available).
     * Time Complexity: O(n)
     */
    public void returnBook(int id) {
        for (int i = 0; i < catalog.size(); i++) {
            Book b = catalog.get(i);
            if (b.getBookID() == id) {
                b.setStatus("Available");
                
                // Push action to Stack for Undo capability
                undoStack.push("RETURN", id); 
                
                // Save updates to file
                FileIO.saveBooks(catalog);
                
                System.out.println("Book returned: " + b.getTitle());
                return;
            }
        }
        System.out.println("Book not found.");
    }

    /**
     * Undoes the last Borrow or Return action.
     * Time Complexity: O(n) to find book and revert
     */
    public void undo() {
        // Pop the last action object from the stack
        Stack.Action lastAction = undoStack.pop(); 
        
        if (lastAction == null) {
            System.out.println("Nothing to undo.");
            return;
        }

        // REVERSE LOGIC: 
        // If last action was "BORROW", revert status to "Available".
        // If last action was "RETURN", revert status to "Borrowed".
        String newStatus = (lastAction.type.equals("BORROW")) ? "Available" : "Borrowed";
        
        // Find the book and update its status
        for (int i = 0; i < catalog.size(); i++) {
            Book b = catalog.get(i);
            if (b.getBookID() == lastAction.bookID) {
                b.setStatus(newStatus);
                
                // Save updates to file
                FileIO.saveBooks(catalog);
                
                System.out.println("Undo successful. Book ID " + lastAction.bookID + " is now " + newStatus);
                return;
            }
        }
    }
}
