public class Tree<E> {
    /*
        Inner Node class for the Tree
        Smaller on the left, larger on the right
     */
    class Node<E> {
        E data;
        Node<E> left, right; // pointers for left and right

        // Constructor for inner class
        /*
        First node will have left and right point to null
            null <--[left][data][right] --> null
         */
        public Node(E obj) {
            this.data = obj;
            left = right = null;
        }
    }

    // Global variable of the tree
    private Node<E> root;
    private int currentSize;

    /*
        Add method
        Recursive method that traverse to all level until it reaches null
        This method receives obj and node
            obj is the object to add
            node is the position to add the new object
     */
    private void add(E obj, Node<E> node) {
        if (((Comparable<E>)obj).compareTo(node.data) > 0) {
            /* '> 0' means if obj > node.data
                If the new object is bigger than the data, we will take the right path
             */

            if(node.right == null) {
                // Check if at the last level. If at last level, add the obj to the right
                node.right = new Node<E>(obj);
                return;
            }
            //  If not at the last level, call the recursive add method and compare with node.right
            add(obj, node.right);
            return;
        }

        // If obj is less or equal to data, we go to the left
        if (node.left == null) {
            // Check if at the last level. If at last level, add the obj to the left
            node.left = new Node<E>(obj);
            return;
        }
        //  If not at the last level, call the recursive add method and compare with node.left
        add(obj, node.left);
    }

    /* This public add() overloads the private add()
        Others can only add using this method, which allow them to enter the tree only from the root node
        Because the private add() can add to a specified node position (second parameter)
     */
    public void add(E obj) {
        if (root == null) {
            // If the tree is empty, add the obj as root
            root = new Node<E>(obj);
        }
        else
            add (obj, root);

        currentSize++;
    }

    /* Contains method
        Check if a tree contains an object. Use Comparable. Then traverse either left/right
        Stop until we reach the object
        If we reach null, the object is not there
        Others can use the public overload contains() to find object in the tree
     */

    public boolean contains(E obj) {
        return contains(obj, root);
    }
    /* The private contains() cannot be accessed from outside
        to prevent people mess around with the tree
        Only let people access the tree through the root
     */

    private boolean contains(E obj, Node<E> node) {
        // If node is null then the tree doesn't have the object
        if (node == null)
            return false;

        // If the object equals to node.data
        if (((Comparable<E>)obj).compareTo(node.data) == 0)
            return true;

        // If the object is bigger than node.data, go to the right
        if (((Comparable<E>)obj).compareTo(node.data) > 0)
            return contains(obj, node.right);

        // If the object is smaller than node.data, go to the left
        return contains(obj, node.left);
    }

    /*
        Rotation to balance an imbalanced tree
        4       ->      6
         \             / \
          6           4   8
         / \           \
        X   8           X
        Set temp to grandparent's right child (6)
        Point grandparent's right child (6) to temp's left child
            (break the 4-6 connection and make 4 the parent of 6's left child)
        Set temp's left child to grandparent (make 4 become 6's left child)
     */
    public Node<E> leftRotate(Node<E> node) { // Parameter is grandparent node
        Node<E> temp = node.right; // Set temp to GP's right child
        node.right = temp.left; // Point GP's right to temp's left
        temp.left = node; // Set temp's left to GP
        return temp; // return the new grandparent
    }

    /* Right rotate is similar to left rotate but the other way around
                4     ->        6
               /               / \
              6               8   4
             / \                 /
            8   Y               Y
     */
    public Node<E> rightRotate(Node<E> node) {
        Node<E> temp = node.left; // Set temp to GP's left
        node.left = temp.right; // Set GP's left to temp's right
        temp.right = node; // Set temp's right to GP; temp is now the GP
        return temp;
    }

    /*
        4       ->      4        ->     6
         \               \             / \
          8               6           4   8
         /                 \
        6                   8
     */
    public Node<E> rightLeftRotate(Node<E> node) { // Take GP node as parameter
        node.right = rightRotate(node.right); // Set GP's right with the new node after rotation
         return leftRotate(node); // Rotate the GP and return new GP
    }
    /*
        4       ->      4        ->     6
       /               /               / \
      8               6               8   4
       \             /
        6           8
     */
    public Node<E> leftRightRotate(Node<E> node) { // Take GP node as parameter
        node.left = leftRotate(node.left);
        return rightRotate(node); // Rotate the GP and return new GP
    }
}
