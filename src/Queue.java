/**
 * Queue Class
 * Manages borrow requests in a First-In-First-Out (FIFO) manner.
 * Used for the Waiting List feature
 */
public class Queue {
    
    /**
     * Request Class
     * Represents a single borrow request containing user and book details.
     * Made public static so the Library class can access this type
     */
    public static class Request {
        public String userName;
        public int bookID;

        public Request(String userName, int bookID) {
            this.userName = userName;
            this.bookID = bookID;
        }

        @Override
        public String toString() {
            return "User: " + userName + ", BookID: " + bookID;
        }
    }

    /**
     * Node Class
     * Private static inner class representing a node in the linked list.
     */
    private static class Node {
        Request data;
        Node next;

        public Node(Request data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front; // The front of the queue (remove from here)
    private Node rear;  // The end of the queue (add to here)

    /**
     * Constructor
     * Initializes an empty queue.
     * Time Complexity: O(1)
     */
    public Queue() {
        this.front = null;
        this.rear = null;
    }

    /**
     * Adds a new borrow request to the end of the queue (Enqueue).
     * Time Complexity: O(1)
     * @param userName Name of the user requesting the book
     * @param bookID ID of the requested book
     */
    public void enqueue(String userName, int bookID) {
        Request newRequest = new Request(userName, bookID);
        Node newNode = new Node(newRequest);

        if (rear == null) {
            // If queue is empty, both front and rear point to the new node
            front = rear = newNode;
            return;
        }
        
        // Link the new node to the end and update rear
        rear.next = newNode;
        rear = newNode;
    }

    /**
     * Removes and returns the request from the front of the queue (Dequeue).
     * Time Complexity: O(1)
     * @return The processed Request object, or null if empty
     */
    public Request dequeue() {
        if (front == null) {
            return null;
        }
        
        Request temp = front.data; // Store data to return
        front = front.next;        // Move front pointer
        
        // If the queue becomes empty after removal, rear must also be null
        if (front == null) {
            rear = null;
        }
        return temp;
    }

    /**
     * Checks if the queue is empty.
     * Time Complexity: O(1)
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return front == null;
    }
}
