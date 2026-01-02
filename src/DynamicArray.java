/**
 * DynamicArray Class
 * Custom implementation of a dynamic array to store Book objects.
 * Manages resizing (growth and shrinking) based on capacity usage [Source: 10, 11].
 */
public class DynamicArray {
    
    private Book[] elements; // Kitapları tutacak ilkel dizi
    private int size;        // Şu an dizide kaç kitap var
    private int capacity;    // Dizi toplam kaç kitap alabilir

    /**
     * Constructor
     * Initializes the array with a default capacity (e.g., 5).
     * Time Complexity: O(1)
     */
    public DynamicArray() {
        this.capacity = 5; // Başlangıç kapasitesi (Test için düşük tuttum)
        this.elements = new Book[capacity];
        this.size = 0;
    }

    /**
     * Adds a new book to the array.
     * Automatically grows the array if it is full.
     * Time Complexity: O(1) (Amortized), O(n) (Worst case when resizing)
     * @param book The book object to add [Source: 13]
     */
    public void add(Book book) {
        if (size == capacity) {
            resize(capacity * 2); // Kapasiteyi 2 katına çıkar [Source: 83]
        }
        elements[size] = book;
        size++;
    }

    /**
     * Returns the book at the specified index.
     * Time Complexity: O(1)
     * @param index The position of the book
     * @return Book object or null if index is invalid [Source: 14]
     */
    public Book get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Error: Index out of bounds.");
            return null;
        }
        return elements[index];
    }

    /**
     * Removes the book at the specified index and shifts elements.
     * Automatically shrinks the array if it is largely empty.
     * Time Complexity: O(n) (Due to shifting elements)
     * @param index The position of the book to remove [Source: 15]
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Error: Index out of bounds.");
            return;
        }
        
        // Silinen elemandan sonrakileri bir adım sola kaydır (Boşluğu kapat)
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        
        elements[size - 1] = null; // Son elemanı (kopyası oluştuğu için) temizle
        size--;

        // EKLEME: Eğer dizi boyutunun 1/4'üne düştüyse, kapasiteyi yarıya indir [Source: 11]
        // Bu işlem hafıza tasarrufu sağlar (Optimization).
        if (size > 0 && size == capacity / 4) {
            resize(capacity / 2);
            System.out.println("Array shrank. New capacity: " + capacity);
        }
    }

    /**
     * Returns the number of books currently in the array.
     * Time Complexity: O(1)
     * @return Current size [Source: 16]
     */
    public int size() {
        return size;
    }

    /**
     * Helper method to resize the array (Grow or Shrink).
     * Creates a new array with the target capacity and copies elements.
     * Time Complexity: O(n)
     * [Source: 83]
     */
    private void resize(int newCapacity) {
        Book[] newArray = new Book[newCapacity];
        
        // Eski dizideki kitapları yeniye kopyala
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[i];
        }
        
        elements = newArray;
        capacity = newCapacity;
        
        // Debug için (Hoca çalışırken görsün diye bırakabilirsin)
        // System.out.println("Array resized to: " + capacity); 
    }
}