import java.util.*;
import java.io.*;


public class BookOrder {

    static int nodeNumber = 0;
    public static void main(String[] args) {
        //Creating scanner variable
        Scanner s = new Scanner(System.in);
        boolean done = false;
       
        //Creating AVL tree variable to store order data.
        AVLTree orderTree = null;
        int value = 0;
       
        //prompting the user to enter the file path of the order file, taking input.
        String filePath = "orders.csv";
        //Reading the order data using the method below from the file and storing it in the AVL tree.
        orderTree = readOrdersFromFile(filePath);

        //loop to control user input, prompts the user after entry is processed
        while (done != true) {
        
            System.out.printf(
                    "Please enter the number corresonding to what you want to do. %n1:Add a book %n2:Remove a book %n3:Print an In-order list of books by order ID %n4:Find the name of a book for a specific order number %n5:Find the oldest book %n6:Find the latest book order %n7:Select when done %n");
           if(s.hasNextInt()){
            value = s.nextInt();
            
           }
           else{
            System.out.println("Please enter a valid number between 1 and 7");
            s.next();
            continue;
           }
           
           

            //Out of bounds value, reprompt the user.
            if (value < 1 || value > 7) {
                System.out.println("Please enter a value greater than 0 and less than 8");
            }
            //First function, manually add a book order, prompt the user for id and book name, calls insert method if user enters valid information.
            if (value == 1) {
                int orderId = -1; // Initialize with an invalid value
                String bookName = null;
                
                // Loop for valid Order ID input
                while (true) {
                    System.out.println("Please enter the order number (It should be a number less than 100 and greater than 0):");
                    if (!s.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid integer for the order ID.");
                        s.next(); // Clear the invalid input
                        continue; // Restart the loop
                    }
            
                    orderId = s.nextInt();
                    if (orderId > 99 || orderId < 1) {
                        System.out.println("Please enter a valid number in the range of 1 to 99.");
                        continue; // reestart the loop
                    }
            
                    // Check if Order ID already exists
                    if (orderTree != null && !orderTree.search(orderTree, orderId).equals("Order ID not found")) {
                        System.out.println("That OrderID is already in the system. Please pick another ID.");

                        continue; // Restart the loop
                    }
            
                    break; // Exit the loop if input is valid
                }
            
                // Loop for valid Book Name input
                s.nextLine(); // get rid of new line
                while (true) {
                    System.out.println("Please enter the book name:");
                    bookName = s.nextLine();
            
                    if (bookName.trim().isEmpty()) {
                        System.out.println("Please enter at least one character for the name.");
                        continue; // Restart the loop
                    }
            
                    break; // Exit the loop if input is valid
                }
            
                // Add the new order to the tree
                if (orderTree == null) {
                    orderTree = new AVLTree(orderId, bookName);
                } else {
                    orderTree = orderTree.insert(orderTree, orderId, bookName);
                }
                nodeNumber++;
                System.out.println("Order number " + orderId + " with name " + bookName + " was added successfully.");
            }

            
            
            //Second function, manually remove a book order, prompt the user for id and book name, calls delete method if user enters valid information.
            if (value == 2) {
                if (orderTree == null) {
                    System.out.println("Tree is empty. Please try adding a node first");
                } else {

                    


                    int removeID; // Initialize with an invalid value
                    
                    // Loop for valid Order ID input
                    while (true) {
                        System.out.println("Please enter the order ID of the book you want to remove. (Must be a  number greater than 0 and less than 100):");
            
                        // Check if the input is an integer
                        if (!s.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid integer for the order ID.");
                            s.next(); // Clear the invalid input
                            continue; // Restart the loop
                        }
            
                        // Retrieve the order ID
                        removeID = s.nextInt();
            
                        // Check if the order ID is within the valid range
                        if (removeID < 1 || removeID > 99) {
                            System.out.println("Please enter a valid number in the range of 1 to 99.");
                            continue; // Restart the loop
                        }

                        if (orderTree.search(orderTree, removeID).equals("Order ID not found")) {
                            System.out.println("Order ID " + removeID + " not found in the tree.");
                             continue;
                        }
            
                        break; // Exit the loop if input is valid
                    }
                    
            
                          // Update `orderTree` with the result of `delete`
        orderTree = orderTree.delete(orderTree, removeID);
        
        // Check if the tree is now empty
                 if (orderTree == null) {
            System.out.println("Tree is now empty after removing the last node.");
            nodeNumber = 0; // Reset the node count
            
                 } else {
            System.out.println("Order ID " + removeID + " was removed successfully.");
                    nodeNumber--;
                 }
      }
                    
                }
            
            
            //Third function, output an inorder tree with current book information, enters inOrder Method.
            if (value == 3) {
                if (orderTree == null) {
                    System.out.println("Tree is empty");
                } else {
                    orderTree.inOrder(orderTree);
                }
            }
            //Fourth function, find the name of a book for a specific order number, enters search method if user enters valid information.
            if (value == 4) {
                int lookupID = -1; // Initialize with an invalid value
                
                if (orderTree == null) {
                    System.out.println("Tree is empty.");
                } else {
                    while (true) {
                        System.out.println("Please enter the OrderID for the book you want to find (between 1 and 99):");
            
                        // Check if the input is an integer
                        if (s.hasNextInt()) {
                            lookupID = s.nextInt();
            
                            // Check if the integer is within the valid range
                            if (lookupID >= 1 && lookupID <= 99) {
                                break; // Valid input, exit the loop
                            } else {
                                System.out.println("Please enter a valid number between 1 and 99.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a valid integer.");
                            s.next(); // Clear the invalid input
                        }
                    }
            
                    // Attempt to find the specified OrderID in the tree
                    String result = orderTree.search(orderTree, lookupID);
                    if (result.equals("Order ID not found")) {
                        System.out.println("Order ID not found. Please enter an existing order ID.");
                    } else {
                        System.out.println("The name of the book is " + result);
                    }
                }
            }
            
            //Fifth function, find the oldest book order, enters oldestOrder method if user enters valid information.
            if (value == 5) {
                if (orderTree == null) {
                    System.out.println("The tree is completely empty.");
                }
                AVLTree oldest = orderTree.oldestOrder(orderTree);
                System.out.println("The oldest order is orderID: " + oldest.orderId + " and the name of the book is "
                        + oldest.bookName);

            }
            //Sixth function, find the latest/most recent book order, enters latestOrder method if user enters valid information.
            if (value == 6) {
                if (orderTree == null) {
                    System.out.println("The tree is completely empty.");
                }
                AVLTree newest = orderTree.latestOrder(orderTree);
                System.out.println("The latest order is orderID: " + newest.orderId + " and the name of the book is "
                        + newest.bookName);
            }

            //Completion of functions, exit if user enters 7
            if (value == 7) {
                done = true;
                System.out.println("Thank you for using our service. Have a good day!");
            }
            
            System.out.println("The number of nodes is " + nodeNumber);
            if (orderTree == null) {
                System.out.println("The height of the tree is: 0");
            } else {
                
                System.out.println("The height of the tree is: " + (orderTree.height ));
            }

        }
        s.close();
    }


    //Method to read filpath from entered 
    public static AVLTree readOrdersFromFile(String filePath) {
        //Needed as this is a static method to pass it (needed)
        AVLTree orderTree = null;
        //BufferedReader is from the JavaIO Library to read the user-provided file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            //Reads the line
            br.readLine();

            //While we have a line, we split the integer and the name of the book into two seperated strings.
            while ((line = br.readLine()) != null) {
                //Splits into two parts
                String[] parts = line.split(",", 2);
                //Initializing the order number using the first part
                int orderId = Integer.parseInt(parts[0].trim());
                //Initializing the book name using the second part
                String bookName = parts[1].trim();

                //If the tree is empty, we create a new node with the order number and book name. Just inserts if not null
                if (orderTree == null) {
                    orderTree = new AVLTree(orderId, bookName);
                } else {
                    orderTree = orderTree.insert(orderTree, orderId, bookName);
                }
                nodeNumber++;
            }
            
            //Confirmation of completion, returns (hopefully correct) height and number of nodes
            System.out.println("The book orders were all added.");
            System.out.println("The height of the tree is: " + (orderTree.height) );
            System.out.println("The number of nodes is " + nodeNumber);
            
        }
        //Exception handling for file reading, if it can't be read
        catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        //Exception handling for file format, if the file an expected format
        catch (NumberFormatException e) {
            System.out.println("The format of the file is not accepted.");
        }
        return orderTree;
    }
}