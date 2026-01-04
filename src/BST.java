/**
 * BST (Binary Search Tree) Class
 * This class stores Book objects sorted by their title.
 * It allows efficient searching by title and alphabetical listing
 * using in-order traversal.
 */
public class BST {

    /**
     * Node class represents a single node in the Binary Search Tree.
     * Each node stores one Book object and references to its left
     * and right child nodes.
     */
    private static class Node {
        Book book;
        Node left;
        Node right;

        /**
         * Constructs a new Node with the given Book.
         * Worst-case Time Complexity: O(1)
         */
        public Node(Book book) {
            this.book = book;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    /**
     * Constructs an empty Binary Search Tree.
     * Worst-case Time Complexity: O(1)
     */
    public BST() {
        this.root = null;
    }

    /**
     * Inserts a Book into the BST based on its title.
     * Title comparison is done in a case-insensitive manner.
     *
     * Worst-case Time Complexity: O(n)
     * (Occurs when the tree becomes skewed, e.g., sorted insertions)
     */
    public void insert(Book book) {
        root = insertRec(root, book);
    }

    /**
     * Recursive helper method for inserting a Book into the BST.
     *
     * Worst-case Time Complexity: O(n)
     */
    private Node insertRec(Node current, Book book) {
        if (current == null) {
            return new Node(book);
        }

        int comparison = book.getTitle()
                .compareToIgnoreCase(current.book.getTitle());

        if (comparison < 0) {
            current.left = insertRec(current.left, book);
        } else if (comparison > 0) {
            current.right = insertRec(current.right, book);
        }
        // If titles are equal, the book is not inserted again
        return current;
    }

    /**
     * Searches for a Book in the BST by its title.
     *
     * Worst-case Time Complexity: O(n)
     * (Occurs when the tree is completely unbalanced)
     */
    public Book search(String title) {
        return searchRec(root, title);
    }

    /**
     * Recursive helper method for searching a Book by title.
     *
     * Worst-case Time Complexity: O(n)
     */
    private Book searchRec(Node current, String title) {
        if (current == null) {
            return null;
        }

        int comparison = title
                .compareToIgnoreCase(current.book.getTitle());

        if (comparison == 0) {
            return current.book;
        } else if (comparison < 0) {
            return searchRec(current.left, title);
        } else {
            return searchRec(current.right, title);
        }
    }

    /**
     * Performs an in-order traversal of the BST.
     * This method prints all books in alphabetical order by title.
     *
     * Worst-case Time Complexity: O(n)
     */
    public void inOrderTraversal() {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        inOrderRec(root);
    }

    /**
     * Recursive helper method for in-order traversal.
     *
     * Worst-case Time Complexity: O(n)
     */
    private void inOrderRec(Node current) {
        if (current != null) {
            inOrderRec(current.left);
            System.out.println(current.book);
            inOrderRec(current.right);
        }
    }
}

