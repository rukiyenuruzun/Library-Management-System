/*
 * Stack Class
 * manages actions in a LastInFirstOut (LIFO) manner using a linkedlist.
 * stores the history of borrow / rreturn operations for the Undo feature.
 * project : 4. Borrow and Return Books + Undo feature
 */

public class Stack {

    
     //represents a single action (borrow or return) stored in the stack.
     //made static and public so Library.java can access fields like .type and .bookID
     
    public static class Action {
        public String type; // barroww or return 
        public int bookID;  //the ID of the book involved

        public Action(String type, int bookID) {
            this.type = type;
            this.bookID = bookID;
        }

        @Override
        public String toString() {
            return "Action: " + type + " on Book ID: " + bookID;
        }
    }
 
     // node class for linkedlist implementation.
    private class Node {
        Action data;
        Node next;

        public Node(Action data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top; //top data of the stack 

    public Stack() {
        this.top = null;
    }

    /**
     * pushes a new action onto the top of the stack.
    time complexity bigO: O(1) Inserting at the head of a linked list is constant
    type  type of action (barrow or return)
    bookID The ID of the book involved
     */
    public void push(String type, int bookID) {
        Action newAction = new Action(type, bookID);
        Node newNode = new Node(newAction);

        newNode.next = top;//link the new node to the current top
        top = newNode;//update top to be the new node
        
    }

    /**
     * removes and returns the last action added to the stack.
     time Complexity: O(1)removing from the head is constant
     * the last action object or null if stack is empty
     */
    public Action pop() {
        if (isEmpty()) {
            return null;
        }
        
        Action poppedAction = top.data;//get data
        
        top = top.next; //shift top to next node
        
        return poppedAction;
    }
      //time Complexity: O(1)
    public boolean isEmpty() {
        return top == null;
    }
    
    // returns the number of elements instack
    //time complxity: O(n) traversal required
    public int size() {
        int count = 0;
        Node current = top;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
}
