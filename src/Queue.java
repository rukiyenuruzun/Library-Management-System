/**
 * Queue Class
 * Manages borrow requests in a First-In-First-Out (FIFO) manner.
 * Used for the Waiting List feature[cite: 28, 29].
 */
public class Queue {
    
    // Public Static yapıldı: Library sınıfı buna erişebilsin diye.
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

    // Static Node
    private static class Node {
        Request data;
        Node next;

        public Node(Request data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;

    public Queue() {
        this.front = null;
        this.rear = null;
    }

    /**
     * Adds a request to the end of the queue.
     * Time Complexity: O(1)
     * [cite: 30]
     */
    public void enqueue(String userName, int bookID) {
        Request newRequest = new Request(userName, bookID);
        Node newNode = new Node(newRequest);

        if (rear == null) {
            front = rear = newNode;
            return;
        }
        rear.next = newNode;
        rear = newNode;
    }

    /**
     * Removes a request from the front.
     * Time Complexity: O(1)
     * [cite: 30]
     */
    public Request dequeue() {
        if (front == null) {
            return null;
        }
        Request temp = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }
        return temp;
    }

    public boolean isEmpty() {
        return front == null;
    }
}