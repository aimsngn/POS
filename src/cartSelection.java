import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.*;

/**
 * This class provides functionality for managing the "cart." It displays, adds, and removes products from the cart.
 * It interacts with the database connection to manage the cart contents, retrieving products' names and IDs.
 */
public class cartSelection {
    Connection conn;                                    // Declares the connection used to interact with the database
    InputValidation validator = new InputValidation();  // Creates an instance of the InputValidation class to validate user inputs
    ArrayList<Integer> productIDs = new ArrayList<>();  // ArrayList to store the selected products' IDs
    Logger logger = LoggingUtility.getLogger();         // Retrieves the logger from the Logging Utility to log events
    int employeeID;                                     // Declares the current employee handling the transaction
    Scanner console = new Scanner(System.in);           // Scanner used for console user input
    
    /**
     * Constructor initializes the database connection and employee ID
     * 
     * @param conn used to interact with the database
     * @param employeeID that's handling the current transaction
     */
    public cartSelection (Connection conn, int employeeID){
        this.conn = conn;
        this.employeeID = employeeID;

    }

    /**
     * This method enables users to manage the cart. It adds/removes selected products.
     * 
     * It connects with the database to search for the selected product's information based on the inputted name. 
     * It calls the "editCart" method when a valid selection is made.
     * 
     * Additionally, it imitates a "autocomplete" feature, where users only need to input the initial characters of a product's name,
     * the system will automatically match those characters with the available products in the database.
     *
     * @return a list of selected product IDs when done selecting.
     */
    public List<Integer> productSelection (){
        char firstCharacterSelection = ' '; 

        // Instructions on how to select a product and edit the cart
        System.out.print("Select a product.\n" +
            "Type \"+\" followed by the product to add an item. For example, +matcha\n" +
            "Type \"-\" followed by the product to remove an item. For example, -matcha\n" +
            "Type \"<\" to void the transaction.\n" +
            "Type \">\" to proceed with the transaction.\n");
        
        do {
            int count = 0;
            int productID = -1;
            System.out.println();
            System.out.print("Enter selection: "); 
            String selection = console.nextLine();

            // This retrieves the user's inputted symbol, from the instruction list (e.g., "+"), at substring 0
            firstCharacterSelection = selection.charAt(0);

            // Will only proceed if user doesn't select to void or proceed with the transaction
            if (firstCharacterSelection != '>' && firstCharacterSelection != '<'){
                try (PreparedStatement getProductInfo = conn.prepareStatement("SELECT product_id FROM products WHERE REPLACE(product_name, ' ', '') LIKE CONCAT('%', REPLACE(?, ' ', ''), '%')")) {
                    getProductInfo.setString(1, selection.substring(1)); 
                    try (ResultSet resultProductInfo = getProductInfo.executeQuery()){  

                        // If there are multiple products found based on user's input, it prompts user's to re-enter and be more specific.
                        while (resultProductInfo.next()){
                            count++; 
                            productID = resultProductInfo.getInt("product_id");
                        }

                        // If there's only one product result from the database query, it calls "editCart" to add/remove product
                        if (count == 1){ 
                            editCart(productID, firstCharacterSelection);

                        // If there's more than one results, it asks users to specify which product
                        } else if (count > 1) { 
                            System.out.println("More than one result for " + selection.substring(1) + ". Please specify which product.");

                        // If there's no product results at all, it asks user's to re-enter a valid selection.
                        } else { 
                            System.out.println("No results found. Please make a valid selection.");
                        }

                    }
                } catch (SQLException e) {
                    System.out.println(System.err);
                    logger.warning("System failure. Can not proceed with product selection and transaction");
                    LoggingUtility.closeHandler();
                    System.exit(1);
                }
            }

        } while (firstCharacterSelection != '>' && firstCharacterSelection != '<');

        // Handles unexpected bypass errors
        if (firstCharacterSelection == ' '){
            System.out.println("Error 0"); //zero for empty seletion bypass
            LoggingUtility.closeHandler();
            System.exit(1);
        }

        // If the list of selected product IDs is empty, it'll void the transaction and return an empty list to transactionPOS
        if (productIDs.isEmpty()) {
            System.out.println("\n\nThe cart is empty. Cancelling current transaction");
            logger.info("Employee " + employeeID + " voided transaction.");
            productIDs.clear();
        } else if (firstCharacterSelection == '<') {
            System.out.println("\n\nVoiding current transaction");
            logger.info("Employee " + employeeID + " voided transaction.");
            productIDs.clear();
        } 

        // Returns the list of product IDs (if not empty)
        return productIDs;
    }

    /**
     * Analyzes the symbol inputted by the user (from the instruction list) and
     * performs actions based on it. It can add, remove, or prompt for re-entry if invalid.
     *
     * @param productID the product to manage in the cart (add/remove)
     * @param firstCharacter The symbol entered by the user (+, -, etc.)
     */
    public void editCart (int productID, char firstCharacter){
            // Analyzes first character of the input, which is the symbol.
            switch (firstCharacter){
                case '+': // ADDING PRODUCT ID TO ARRAYLIST
                    productIDs.add(productID); 
                    System.out.println("_________________________________");
                    System.out.println("\nItem added. Your cart:");
                    showCart();
                    System.out.println("_________________________________");
                    break;
                    
                case '-': // REMOVING PRODUCT ID TO ARRAYLIST
                    if (productIDs.contains(productID)) {
                        productIDs.remove(Integer.valueOf(productID)); 
                        System.out.println("_________________________________");
                        System.out.println("\nItem removed. Your cart:");
                        showCart();
                        System.out.println("_________________________________");
                    } else {
                        System.out.println ("The item you are trying to remove is not in the cart. No changes were made.");
                    }
                    break;

                default: //UNKNOWN SYMBOL; It discards the selected product ID, and prompts users to re-enter product & symbol.
                    System.out.println("Please make a valid selection with correct symbols.");
            }
    }

    //Displays the names and quantities of products currently in the cart.
    public void showCart(){
        HashMap<String, Integer> productNamesCount = new HashMap<>(); //to store the quantity of all product IDs
        try (PreparedStatement getName = conn.prepareStatement("SELECT product_name FROM products where product_id = ?")) {
            for (int id : productIDs) {
                getName.setInt(1, id);
                try (ResultSet resultGetName = getName.executeQuery()){
                    if (resultGetName.next()) {
                        String productName = resultGetName.getString("product_name");

                        if(!productNamesCount.containsKey(productName)) {
                            productNamesCount.put(productName, 1);    
                        } else {
                            int quantity = productNamesCount.get(productName);
                            productNamesCount.put(productName, quantity+1);
                        }
                        
                    }
                } 
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            logger.warning("System can not show cart");
        }

        // If the hashmap is empty, which means the ArrayList is empty, it'll display "Empty."
        if (!productNamesCount.isEmpty()){
            for(Map.Entry<String, Integer> cart : productNamesCount.entrySet()){
                String productName = cart.getKey();
                int productID = cart.getValue();
                System.out.println(productName + " : " + productID);
            }
        } else {
            System.out.println("Empty.");
        }
                    
    }

}
