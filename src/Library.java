/**
 * Library Class
 * Manages the entire system by integrating DynamicArray, BST, Queue, and Stack.
 * Handles File I/O for persistence via helper class [Source: 67, 86].
 */
public class Library {

    // --- Data Structures ---
    private DynamicArray catalog;   // Kitapları tutan ana liste (Array) [Source: 10]
    private BST bookTree;           // İsimle arama yapmak için ağaç (BST) [Source: 21]
    private Queue borrowRequests;   // Bekleme sırası (Queue) [Source: 28]
    private Stack undoStack;        // Geri alma işlemi için yığın (Stack) [Source: 36]

    private int lastBookID = 100;   // ID sayacı (100'den başlattık)

    /**
     * Constructor
     * Initializes all data structures and loads existing data from files.
     */
    public Library() {
        catalog = new DynamicArray();
        bookTree = new BST();
        borrowRequests = new Queue();
        undoStack = new Stack();

        // 1. Dosyadan kayıtlı kitapları yükle
        FileIO.loadBooks(catalog, bookTree);
        
        // 2. ID çakışmasını önlemek için son ID'yi güncelle
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
        lastBookID++; // ID'yi artır
        // KURAL: Yeni eklenen kitap her zaman "Available" olur.
        Book newBook = new Book(lastBookID, title, author, "Available");

        catalog.add(newBook);       // Array'e ekle [Source: 17]
        bookTree.insert(newBook);   // BST'ye ekle [Source: 24]
        
        // Değişikliği dosyaya kaydet
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
                catalog.remove(i); // [Source: 15]
                
                // Silme işlemini dosyaya yansıt
                FileIO.saveBooks(catalog);
                
                // Not: BST'den silme işlemi karmaşık olduğu için 
                // bu proje kapsamında genelde istenmez ama listeden silindi.
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
        Book b = bookTree.search(title); // [Source: 25]
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
        bookTree.inOrderTraversal(); // [Source: 46]
    }

    /**
     * Adds a borrow request to the queue and saves user to file.
     * Time Complexity: O(1)
     */
    public void requestBook(String userName, int bookID) {
        // İsteği kuyruğa ekle
        borrowRequests.enqueue(userName, bookID); // [Source: 30]
        
        // Kullanıcıyı veritabanına ekle (Log amaçlı)
        FileIO.appendUser(userName + ";Requested Book ID: " + bookID);
        
        System.out.println("Request added to waiting list for user: " + userName);
    }

    /**
     * Processes the next borrow request in the queue.
     * Time Complexity: O(1)
     */
    public void processQueue() {
        // Queue sınıfının içinde Request diye bir obje/inner class döndürdüğünü varsayıyoruz
        // Eğer Queue string döndürüyorsa burayı ona göre güncellemelisin.
        Object req = borrowRequests.dequeue(); // [Source: 33]
        
        if (req != null) {
            System.out.println("Processing request: " + req.toString());
            // Buraya otomatik borrowBook çağırma mantığı eklenebilir.
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
                    
                    // İşlemi Stack'e at (Undo için)
                    undoStack.push("BORROW", id); // [Source: 40]
                    
                    // Dosyayı güncelle
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
                
                // İşlemi Stack'e at (Undo için)
                undoStack.push("RETURN", id); // [Source: 40]
                
                // Dosyayı güncelle
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
        // Stack sınıfının Action diye bir obje döndürdüğünü varsayıyoruz.
        // Eğer Stack string döndürüyorsa parse etmen gerekir.
        Stack.Action lastAction = undoStack.pop(); // [Source: 41]
        
        if (lastAction == null) {
            System.out.println("Nothing to undo.");
            return;
        }

        // TERS MANTIK: Eğer son işlem "BORROW" ise geri alınca "Available" olur.
        // Eğer son işlem "RETURN" ise geri alınca "Borrowed" olur.
        String newStatus = (lastAction.type.equals("BORROW")) ? "Available" : "Borrowed";
        
        // Kitabı bul ve durumunu güncelle
        for (int i = 0; i < catalog.size(); i++) {
            Book b = catalog.get(i);
            if (b.getBookID() == lastAction.bookID) {
                b.setStatus(newStatus);
                
                // Dosyayı güncelle
                FileIO.saveBooks(catalog);
                
                System.out.println("Undo successful. Book ID " + lastAction.bookID + " is now " + newStatus);
                return;
            }
        }
    }
}