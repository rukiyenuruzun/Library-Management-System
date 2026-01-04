/**
 * Stack Class
 * Manages actions in a Last-In-First-Out (LIFO) manner using a Linked List approach.
 * Specifically designed for the Undo feature in the Library System [Source: 36, 37].
 */
public class Stack {

    /**
     * Represents a single action (Borrow or Return) stored in the history.
     * Accessible publicly so Library.java can read the action type and book ID.
     */
    public static class Action {
        public String type; // "BORROW" or "RETURN"
        public int bookID;

        public Action(String type, int bookID) {
            this.type = type;
            this.bookID = bookID;
        }
        
        @Override
        public String toString() {
            return type + " operation on Book ID: " + bookID;
        }
    }

    /**
     * Node class for the linked list structure.
     * Private because outside classes don't need to know about the nodes, just the Actions.
     */
    private static class Node {
        Action data;
        Node next;

        public Node(Action data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top; // Yığının en tepesi (Head of the list)

    /**
     * Constructor
     * Initializes an empty stack.
     * Time Complexity: O(1)
     */
    public Stack() {
        this.top = null;
    }

    /**
     * Pushes a new action onto the stack.
     * Adds the new node to the beginning of the list (Top).
     * Time Complexity: O(1)
     * @param type The type of action ("BORROW" or "RETURN")
     * @param bookID The ID of the book involved [Source: 40]
     */
    public void push(String type, int bookID) {
        Action action = new Action(type, bookID);
        Node newNode = new Node(action);
        
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top; // Yeni geleni eskilerin tepesine koy
            top = newNode;      // Top artık yeni gelen düğüm
        }
    }

    /**
     * Pops the last action from the stack.
     * Removes the node from the top of the list.
     * Used by the undo feature to revert changes.
     * Time Complexity: O(1)
     * @return The last Action object or null if stack is empty [Source: 41]
     */
    public Action pop() {
        if (top == null) {
            return null; // Stack boşsa null dön
        }
        
        Action poppedAction = top.data; // Veriyi al
        top = top.next;                 // Top'ı bir aşağı kaydır
        return poppedAction;            // Veriyi geri döndür
    }

    /**
     * Checks if the stack is empty.
     * Time Complexity: O(1)
     */
    public boolean isEmpty() {
        return top == null;
    }
    
    /**
     * Peeks at the top action without removing it.
     * (Optional helper method)
     */
    public Action peek() {
        if (top == null) return null;
        return top.data;
    }
}
