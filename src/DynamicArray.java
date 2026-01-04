/**
 * DynamicArray Class
 */
public class DynamicArray {

    private Book[] elements; // list of books
    private int size; // Current number of books in the array
    private int capacity; // Maximum capacity of the array

    /**
     * Constructor
     * Initializes the array with a default capacity.
     * Time Complexity: O(1)
     */
    public DynamicArray() {
        this.capacity = 5; // Starting capacity
        this.elements = new Book[capacity];
        this.size = 0;
    }

    /**
     * Adds a new book to the array.
     * Automatically grows the array if it is full.
     * Time Complexity: O(1) (Amortized), O(n) (Worst case when resizing)
     * 
     * @param book The book object to add [Source: 13]
     */
    public void add(Book book) {
        if (size == capacity) {
            resize(capacity * 2); // Double the capacity
        }
        elements[size] = book;
        size++;
    }

    /**
     * Returns the book at the specified index.
     * Time Complexity: O(1)
     * 
     * @param index The position of the book
     * @return Book object or null if index is invalid
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
     * 
     * @param index The position of the book to remove
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Error: Index out of bounds.");
            return;
        }

        // Shift elements to the left to fill the gap
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null; // Clear the last element
        size--;

        // Shrink the array if necessary
        if (size > 0 && size == capacity / 4) {
            resize(capacity / 2);
        }
    }

    /**
     * Returns the number of books currently in the array.
     * Time Complexity: O(1)
     * 
     * @return Current size
     */
    public int size() {
        return size;
    }

    /**
     * Helper method to resize the array (Grow or Shrink).
     * Creates a new array with the target capacity and copies elements.
     * Time Complexity: O(n)
     */
    private void resize(int newCapacity) {
        Book[] newArray = new Book[newCapacity];

        // Copy existing elements to the new array
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[i];
        }

        elements = newArray;
        capacity = newCapacity;
    }
}
