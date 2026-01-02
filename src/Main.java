import java.util.Scanner;

/**
 * Main Class
 * The entry point of the Library Management System.
 * Provides a console-based menu for user interaction[cite: 68].
 */
public class Main {
    
    public static void main(String[] args) {
        Library library = new Library(); // Sistemi başlat
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        System.out.println("Welcome to the Library Management System!");

        while (choice != 0) {
            printMenu();
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine()); // Hata önlemek için nextLine
                
                switch (choice) {
                    case 1: // Add a new book [cite: 49]
                        System.out.print("Enter Book Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author Name: ");
                        String author = scanner.nextLine();
                        library.addBook(title, author);
                        break;

                    case 2: // Remove a book [cite: 50]
                        System.out.print("Enter Book ID to remove: ");
                        int removeId = Integer.parseInt(scanner.nextLine());
                        library.removeBook(removeId);
                        break;

                    case 3: // Search by ID [cite: 51]
                        System.out.print("Enter Book ID to search: ");
                        int searchId = Integer.parseInt(scanner.nextLine());
                        library.searchByID(searchId);
                        break;

                    case 4: // Search by Title [cite: 52]
                        System.out.print("Enter Book Title to search: ");
                        String searchTitle = scanner.nextLine();
                        library.searchByTitle(searchTitle);
                        break;

                    case 5: // List all books (Dynamic Array) [cite: 53]
                        library.listAllBooks();
                        break;

                    case 6: // List alphabetically (BST) [cite: 54]
                        library.listAllBooksAlphabetical();
                        break;

                    case 7: // Request to borrow (Queue) [cite: 55]
                        System.out.print("Enter User Name: ");
                        String user = scanner.nextLine();
                        System.out.print("Enter Book ID to request: ");
                        int reqId = Integer.parseInt(scanner.nextLine());
                        library.requestBook(user, reqId);
                        break;

                    case 8: // Process borrow requests [cite: 56]
                        library.processQueue();
                        break;

                    case 9: // Borrow a book [cite: 57]
                        System.out.print("Enter Book ID to borrow: ");
                        int borrowId = Integer.parseInt(scanner.nextLine());
                        library.borrowBook(borrowId);
                        break;

                    case 10: // Return a book [cite: 58]
                        System.out.print("Enter Book ID to return: ");
                        int returnId = Integer.parseInt(scanner.nextLine());
                        library.returnBook(returnId);
                        break;

                    case 11: // Undo last action [cite: 59]
                        library.undo();
                        break;

                    case 0: // Exit [cite: 60]
                        System.out.println("Exiting system. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--------------------------------");
        System.out.println("Library Management System Menu");
        System.out.println("1. Add a new book");
        System.out.println("2. Remove a book");
        System.out.println("3. Search book by ID");
        System.out.println("4. Search book by title");
        System.out.println("5. List all books (ID Order)");
        System.out.println("6. List all books alphabetically");
        System.out.println("7. Request to borrow a book (Queue)");
        System.out.println("8. Process borrow requests");
        System.out.println("9. Borrow a book");
        System.out.println("10. Return a book");
        System.out.println("11. Undo last action");
        System.out.println("0. Exit");
        System.out.println("--------------------------------");
    }
}