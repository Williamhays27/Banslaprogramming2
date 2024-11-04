public class AVLTree {
   
    // Instance variables for AVL Tree nodes, height, id, and names.
     AVLTree left;
     AVLTree right;
     int height;
     int orderId;
     String bookName;
    
     // Constructor for AVL Tree nodes.
     AVLTree(int orderId, String bookName){
         this.left = null;
         this.right = null;
         this.height = 1; 
         this.orderId = orderId;
         this.bookName = bookName;
     }
 
     // Helper method to get the height of a node, returns 0 if the node is null.
     private int height(AVLTree node) {
         return (node == null) ? 0 : node.height;
     }
 
     // Method to update the height of a node based on the heights of its children.
     private void updateHeight(AVLTree node) {
         if (node != null) {
             node.height = 1 + Math.max(height(node.left), height(node.right));
         }else{
            height = 0;
         }
     }
 
     // Method used in rotating right to balance AVL tree.
     public AVLTree rightRotate(AVLTree t) {
         AVLTree y = t.left;
         AVLTree temp = y.right;
 
         // Perform rotation
         y.right = t;
         t.left = temp;
 
         // Update heights
         updateHeight(t);
         updateHeight(y);
 
         return y;
     }
 
     // Method used in rotating left to balance AVL tree.
     public AVLTree leftRotate(AVLTree t) {
         AVLTree y = t.right;
         AVLTree temp = y.left;
 
         // Perform rotation
         y.left = t;
         t.right = temp;
 
         // Update heights
         updateHeight(t);
         updateHeight(y);
 
         return y;
     }
 
     // Balancing function: Calls leftRotate or rightRotate based on the balance factor.
     public AVLTree balanceTree(AVLTree t) {
         updateHeight(t);  // Ensure the current node has an updated height.
 
         int balanceFactor = calcBF(t);
 
         // Left-heavy case
         if (balanceFactor > 1) {
             if (calcBF(t.left) < 0) { // Left-Right Case
                 t.left = leftRotate(t.left);
             }
             return rightRotate(t); // Left-Left Case
         }
         // Right-heavy case
         if (balanceFactor < -1) {
             if (calcBF(t.right) > 0) { // Right-Left Case
                 t.right = rightRotate(t.right);
             }
             return leftRotate(t); // Right-Right Case
         }
 
         return t; // No rotation needed
     }
 
     // Calculates the balance factor (height difference between left and right subtrees) for a node.
     public int calcBF(AVLTree t) {
         if (t == null) return 0;
         return height(t.left) - height(t.right);
     }
 
     // Inserts a new node into the AVL tree with user-provided orderId and bookName.
     public AVLTree insert(AVLTree t, int orderId, String bookName) {
         // If tree is null, creates a new node
         if (t == null) {
             return new AVLTree(orderId, bookName);
         }
 
         // Recursively inserts new node into left or right subtree based on orderId.
         if (orderId < t.orderId) {
             t.left = insert(t.left, orderId, bookName);
         } else if (orderId > t.orderId) {
             t.right = insert(t.right, orderId, bookName);
         } else {
             return t; // Duplicate orderId not allowed
         }
 
         // Balance the tree
         return balanceTree(t);
     }
 
     // Deletes a node based on user-provided orderId.
     public AVLTree delete(AVLTree t, int orderId) {
         if (t == null) return t;
 
         if (orderId < t.orderId) {
             t.left = delete(t.left, orderId);
         } else if (orderId > t.orderId) {
             t.right = delete(t.right, orderId);
         } else {
             // Node found for deletion
             if (t.left == null) return t.right;
             if (t.right == null) return t.left;
 
             // Node with two children: Get the inorder successor (smallest in the right subtree)
             AVLTree successor = oldestOrder(t.right);
             t.orderId = successor.orderId;
             t.bookName = successor.bookName;
             t.right = delete(t.right, successor.orderId);
         }
 
         // Balance the tree
         return balanceTree(t);
     }
 
     // Method to get the node with the minimum orderId (oldest order).
     public AVLTree oldestOrder(AVLTree t) {
         AVLTree current = t;
         while (current.left != null) {
             current = current.left;
         }
         return current;
     }
 
     // Method to get the node with the maximum orderId (latest order).
     public AVLTree latestOrder(AVLTree t) {
         AVLTree current = t;
         while (current.right != null) {
             current = current.right;
         }
         return current;
     }
 
     // Traverse the tree using inOrder traversal to output the tree in order by orderId.
     public void inOrder(AVLTree t) {
         if (t == null) {
             return;
         }
         inOrder(t.left);
         System.out.println(t.bookName + " ");
         inOrder(t.right);
     }
 
     // Method to search for a book based on orderId.
     public String search(AVLTree t, int lookupID) {
         if (t == null) {
             return "Order ID not found";
         }
         if (lookupID == t.orderId) {
             return t.bookName;
         }
         if (lookupID < t.orderId) {
             return search(t.left, lookupID);
         }
         return search(t.right, lookupID);
     }
 }
 