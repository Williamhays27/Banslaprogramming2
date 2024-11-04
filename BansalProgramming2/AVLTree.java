public class AVLTree {
   
   //Instance variables for AVL Tree nodes, height, id, and names.
    AVLTree left;
    AVLTree right;
    int height;
    int orderId;
    String bookName;
   
    //Constructor for AVL Tree nodes.
    AVLTree(int orderId, String bookName){
        this.left = null;
        this.right = null;
        this.height = 1;
        this.orderId =orderId;
        this.bookName = bookName;
    }

   
    //Method used in rotating right to balance AVL tree.
    public AVLTree rightRotate(AVLTree t) {
        AVLTree y = t.left;
        AVLTree temp = y.right;
        y.right = t;
        t.left = temp;
        return y;
    }

    //Method used in rotating left to balance AVL tree.
    public AVLTree leftRotate(AVLTree t) {
        AVLTree y = t.right;
        AVLTree temp = y.left;
        y.left = t;
        t.right = temp;
        return y;

    }

    //Call leftRotate/Right Rotate based on Balacing Factor in order to maintain a balanced tree structure.
    public AVLTree BalanceTree(AVLTree t) {
        updateHeight(t);
        int bf = calcBF(t);
        if (bf < -1) {
            if (calcBF(t.right) > 0) {
                t.right = rightRotate(t.right);
            }
            return leftRotate(t);
        } else if (bf > 1) {
            if (calcBF(t.left) < 0) {
                t.left = leftRotate(t.left);
            }
            return rightRotate(t);
        }
        return t;
    }

    //Maintains the a correct height variable called after insert and delete methods.
    public int updateHeight(AVLTree t) {
        if (t == null) {
            return -1;
        }
        //If tree is not null, we add 1 to the max height of the subtree with a larger height, and return it.
        else {
            t.height = 1 + Math.max(updateHeight(t.left), updateHeight(t.right));
            return t.height;
        }
    }
    
    //Calculates the balance factor (height difference between left and right subtrees) for each node. (Used in BalanceTree method).
    public int calcBF(AVLTree t) {
        if (t == null) {
            return 0;
        }
        // Use 0 for height if left or right subtree is null.
        int leftHeight = (t.left != null) ? t.left.height : 0;
        int rightHeight = (t.right != null) ? t.right.height : 0;
    
        return leftHeight - rightHeight;
    }
    

    //Inserts new node into AVL tree with user-provided orderId and bookName.
    public AVLTree insert(AVLTree t, int orderId, String bookName) {
        //If tree is null, creates a new node
        if (t == null) {
            return new AVLTree(orderId, bookName);
        }
        //Recursively inserts new node into left or right subtree based on orderId.
        if (orderId < t.orderId) {
            t.left = insert(t.left, orderId, bookName);
        } else if (orderId > t.orderId) {
            t.right = insert(t.right, orderId, bookName);
        } else {
            return t;
        }

        
      
        //Balances tree after insertion of new node
        t = BalanceTree(t);
        //Updates height after insertion of new node
        t.updateHeight(t);
        return t;

    }

    boolean deleted = false;
    
    //Method to "fulfill order"/delete a node based on user-provided orderId
    public AVLTree delete(AVLTree t, int orderId) {
        // Return the tree if it's null (orderId not found)
        if (t == null) {
            return t;
        }

        // Traverse left if provided id is less than the node
        if (orderId < t.orderId) {
            t.left = delete(t.left, orderId);
        }
        // Traverse right if provided id is greater than the node
        else if (orderId > t.orderId) {
            t.right = delete(t.right, orderId);
        }
        // Node found for deletion
        else {
            deleted = true; // Set deletion flag to true

            // Case with one child or no children
            if (t.left == null && t.right == null) {
                t = null;
            } else if (t.left == null) {
                t = t.right;
            } else if (t.right == null) {
                t = t.left;
            } else {
                // Retrieves minimum value of the right subtree
                AVLTree successor = oldestOrder(t.right);
                t.orderId = successor.orderId;
                t.bookName = successor.bookName;
                // Recursively call delete on the successor
                t.right = delete(t.right, successor.orderId);
            }
        }

        // If the node removed was the only node
        if (t == null) {
            return null;
        }

        // Balance and update height
        t = BalanceTree(t);
        updateHeight(t);
        return t;
          }
    
    public boolean deleteOrder(int orderId) {
        deleted = false; // Reset the deletion flag
        delete(this, orderId);
        return deleted; // Returns true if deletion occurred, false if not found
    }
    
    

    //Method to get the node with the maximum orderId
    public AVLTree oldestOrder(AVLTree t) {

        AVLTree oldestOrder = t;
        while (oldestOrder.left != null) {
            oldestOrder = oldestOrder.left;
        }

        return oldestOrder;

    }

    //Method to get the node with the minimum orderID
    public AVLTree latestOrder(AVLTree t) {
        AVLTree latestOrder = t;
        while (latestOrder.right != null) {
            latestOrder = latestOrder.right;
        }
        return latestOrder;

    }
    
    //Traverse the tree using inOrder traversal to output the in order tree by orderId
    public void inOrder(AVLTree t) {
        if (t == null) {
            return;
        }
        //Traverses left subtree first until it cant anymore, then the current node, then the right subtree.
        inOrder(t.left);
        if (t != null) {
            System.out.println(t.bookName + " ");
        }
        inOrder(t.right);
    }

    //Method to search for a book based on orderId
    public String search(AVLTree t, int lookupID){
        
            //Empty tree
            if (t == null) {
                return "Order ID not found";
            }
            
            //Takes the given ID, checks equivalence within the tree, returns the name cooresponding to the ID
            if (lookupID == t.orderId) {
                return t.bookName;
            }
            
            //Traverse left if provided ID is less than the one we are comparing to.
            if (lookupID < t.orderId) {
                return search(t.left, lookupID);
            }
            
            
            return search(t.right, lookupID);
        }
        
    

}
