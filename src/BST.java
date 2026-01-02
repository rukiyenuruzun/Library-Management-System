/**
 * BST (Binary Search Tree) Class
 * Used to store books sorted by their title.
 * Allows efficient searching and alphabetical listing.
 */
public class BST {
    
    // Static yapılarak hafıza tasarrufu sağlandı
    private static class Node {
        Book book;
        Node left, right;

        public Node(Book book) {
            this.book = book;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BST() {
        this.root = null;
    }

    /**
     * Inserts a book into the BST based on its title.
     * Time Complexity: O(log n) average
     *
     */
    public void insert(Book book) {
        root = insertRec(root, book);
    }

    private Node insertRec(Node root, Book book) {
        if (root == null) {
            return new Node(book);
        }

        if (book.getTitle().compareToIgnoreCase(root.book.getTitle()) < 0) {
            root.left = insertRec(root.left, book);
        } else if (book.getTitle().compareToIgnoreCase(root.book.getTitle()) > 0) {
            root.right = insertRec(root.right, book);
        }
        return root;
    }

    /**
     * Searches for a book by its title.
     * Time Complexity: O(log n) average
     *
     */
    public Book search(String title) {
        return searchRec(root, title);
    }

    private Book searchRec(Node root, String title) {
        if (root == null || root.book.getTitle().equalsIgnoreCase(title)) {
            return (root != null) ? root.book : null;
        }

        if (title.compareToIgnoreCase(root.book.getTitle()) < 0) {
            return searchRec(root.left, title);
        }
        return searchRec(root.right, title);
    }

    /**
     * Performs In-Order Traversal to display books alphabetically.
     * Time Complexity: O(n)
     *
     */
    public void inOrderTraversal() {
        if (root == null) System.out.println("Tree is empty.");
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println(root.book);
            inOrderRec(root.right);
        }
    }
}