import java.io.*;

public class FileIO {

    // Dosya isimleri
    private static final String BOOK_FILE = "books.txt";
    private static final String USER_FILE = "users.txt";

    /**
     * Kitapları dosyadan okur ve sisteme yükler.
     */
    public static void loadBooks(DynamicArray array, BST bst) {
        File file = new File(BOOK_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
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
     * İŞTE HATA VEREN METOT BU:
     * Tüm kitap listesini dosyaya kaydeder.
     */
    public static void saveBooks(DynamicArray array) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (int i = 0; i < array.size(); i++) {
                Book book = array.get(i);
                if (book != null) {
                    String line = book.getBookID() + ";" + 
                                  book.getTitle() + ";" + 
                                  book.getAuthor() + ";" + 
                                  book.getStatus();
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    /**
     * Kullanıcıları dosyaya ekler.
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