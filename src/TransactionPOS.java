import java.sql.*;
import java.util.*;
import java.util.logging.Logger;


/**
 * This class manages point-of-sale transactions for employees.
 *
 * This offers methods for displaying products, choosing items for purchase, calculating subtotals,
 * logging transactions into the database, and generating transaction receipts.
 * 
 * It collaborates with InputValidation, ProductManager, CustomerManager, and cartSelection
 * components to achieve this functionality. 
 * 
 * It interacts with a database connection to retrieve product and customer information, 
 * log transactions, and generate receipts.
 *
 * This class encapsulates the logic for point-of-sale transactions and facilitates interaction
 * between employees, customers, and product offerings.
 */
public class TransactionPOS {
    InputValidation validator = new InputValidation();  // Creates an instance of the InputValidation class to validate user inputs
    Logger logger = LoggingUtility.getLogger();         // Retrieves the logger from the Logging Utility to log events
    Scanner console = new Scanner(System.in);           // Scanner for console input
    List<Integer> productIDs = new ArrayList<>();       // Declares a list to store selected product IDs based on user input
    int employeeID;                                     // Declares the current employee handling the transaction
    ProductManager menu;                                // Declares the ProductManager class, which will be used to display the products
    Connection conn;                                    // Declares the connection used for database interaction
    cartSelection cart;                                 // Declares the cartSelection class for managing cart items

    /**
    * Constructor to initialize the objects with the passed database connection and employee ID.
    *
    * @param conn The database used for the entire point-of-sale system
    * @param employeeID the unique identifer for the employee conducting the transaction
    */
    public TransactionPOS (Connection conn, int employeeID) {
        this.conn = conn;
        this.menu = new ProductManager(conn);
        this.employeeID = employeeID;
        this.cart = new cartSelection(conn, employeeID);
    }
    
    // This is the entry point of the program, that allows employees to select products and process transactions
    public void run (){
        String exitString; // User input to exit the program

        do {
            productIDs.clear(); // Clears the list to ensure an empty cart
            System.out.println("----------------------------------------------------------\n");
            menu.displayProducts();
            System.out.println("----------------------------------------------------------");

            productIDs = cart.productSelection(); // Redirects users to productSelection of the cartSelection class; allows users to manage cart.

            // If the cart isn't empty, it'll process the transaction. Otherwise, it voids.
            if (!productIDs.isEmpty()){
                int transactionID = logPurchase();
                receipt(transactionID); 
            }
            
            System.out.print("\nAnother transaction? Enter yes/no --> ");
            exitString = validator.inputAnswer();
        
        } while (!exitString.equalsIgnoreCase("no"));

        // Logs the employee's log-out event before exiting the program.
        logger.info("Employee " + employeeID + " logged out");

    }

    /**
    * This method faciliates new and returning customers. It interacts with the customerManager object to do this.
    *
    * @return either a valid customer ID or -1. This will be used later to log a transaction and generate a receipt.
    */
    public int customerLoyalty () {
        CustomerManager customer = new CustomerManager(conn, employeeID); // Creates an instance of the CustomerManager class to handle queries and signups

        System.out.print("\nIs the customer registered in the loyalty system? Enter yes/no --> ");
        String prompt = validator.inputAnswer();
        int customerID = prompt.equalsIgnoreCase("yes") ? customer.searchCustomer() : customer.signUpPrompt(); 
        
        return customerID;
    }

    /**
     * This method calculates the subtotal of the selected products.
     * 
     * @return subtotal amount
     */
    public double calculateSubtotal(){
        double subtotal = 0.0;

        try (PreparedStatement getPrice = conn.prepareStatement("SELECT price FROM products where product_id = ?")) {
            for (int id : productIDs) {
                getPrice.setInt(1, id);
                try (ResultSet resultGetPrice = getPrice.executeQuery()){
                    if(resultGetPrice.next()) {
                        double price = resultGetPrice.getDouble("price");
                        subtotal = subtotal + price;
                    }
                } 
            }
        
        } catch (SQLException e) {
                e.printStackTrace(System.err);
                logger.warning("Can't calculate subtotal. Price can't be found for product IDs: " + productIDs.toString());

        }
        return subtotal;  
    }

    /**
     * This method logs the transaction details into the database. 
     * It logs employee's ID, customer's ID (if available), a string format of the selected product ID/s, and the subtotal.
     * 
     * @returns the transaction ID to be displayed on the receipt
     */
    public int logPurchase() {
        String stringPID = productIDs.toString(); // Creates a string of the product IDs in a list format
        double subtotal = calculateSubtotal();
        int customerID = customerLoyalty();

        try (PreparedStatement logPurchase = conn.prepareStatement("INSERT INTO transactions(employee_id, customer_id, product_id, subtotal) VALUES (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            logPurchase.setInt(1, employeeID);

            // Either sets a valid customer ID or null type
            logPurchase.setObject(2, (customerID > 0) ? customerID : null, java.sql.Types.INTEGER); 

            logPurchase.setString(3, stringPID);
            logPurchase.setDouble(4, subtotal);
            logPurchase.executeUpdate();

            try(ResultSet generatedKeys = logPurchase.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    int transactionID = generatedKeys.getInt(1);
                    logger.info("Employee " + employeeID + " processed a new transaction. Transaction: [" + transactionID + "]");

                    return transactionID;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            logger.severe("Employee " + employeeID + " failed to process a new transaction.");
            LoggingUtility.closeHandler();
            System.exit(1);
        }

        return -1; //Handles exception and returns -1 if necessary
    }
   
    /**
     * This method generates a receipt. It shows customer ID (if available), transaction date, products, and subtotal
     * 
     * @param transactionID to retrieve transaction details
     */
    public void receipt(int transactionID) {
        System.out.println("\n\n--------------Your Receipt--------------");
        System.out.println("           Amelie's Cafe Shop           ");
        System.out.println("             California, USA            ");
        System.out.println("----------------------------------------");
    
        try (PreparedStatement getTransaction = conn.prepareStatement("SELECT customer_id, trans_date, product_id, subtotal FROM transactions WHERE id = ?")){
            getTransaction.setInt(1, transactionID);

            try (ResultSet resultTransaction = getTransaction.executeQuery()){
                if (resultTransaction.next()) {
                    Integer customerID = resultTransaction.getInt("customer_id"); 
                    Timestamp purchaseDate = resultTransaction.getTimestamp("trans_date");
                    String StringPID = resultTransaction.getString("product_id");
                    Double subtotal = resultTransaction.getDouble("subtotal");
        
                    System.out.println();
                    System.out.println("Purchase Date:  " + purchaseDate);
                    System.out.println("Transaction ID: " + transactionID);
                    System.out.println("Employee ID:   #" + employeeID);
        
                    if (customerID != null && customerID > 0) { // Checks if customer ID is available and isn't null; Only prints if it's available.
                        System.out.println("#" + customerID);
                    }
        
                    System.out.println();
                    System.out.println("----------------------------------------");
        
                    // Convert the string format of the product IDs into a list of integers
                    // Decided to create a new list based on the transaction log to ensure accuracy,
                    // rather than using the previous list that was used to hold selected products.
                    StringPID = StringPID.substring(1, StringPID.length() - 1);
                    String[] IDs = StringPID.split(",");
                    List<Integer> product_ids = new ArrayList<>();
                    for (String ID : IDs) {
                        int PID = Integer.parseInt(ID.trim());
                        product_ids.add(PID);
                    }

                   // Retrieve product details based on the list of converted integer product IDs
                    for (Integer product_id : product_ids) {       
                        try (PreparedStatement productStatement = conn.prepareStatement("SELECT product_name, price FROM products WHERE product_id = ?")){
                            productStatement.setInt(1, product_id);
                            try (ResultSet productResult = productStatement.executeQuery()){
                                if (productResult.next()) {
                                    String name = productResult.getString("product_name");
                                    Double price = productResult.getDouble("price");
                                    System.out.println("Product: " + name + ", Price: $" + price);
                                }
                            }
                        }
                    }
        
                    System.out.println("----------------------------------------");
                    System.out.println("Subtotal: $" + subtotal);
        
                } else {
                    System.out.println("No purchase result found.");
                }
        
            }
    
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            logger.warning("System can not retrieve transaction for receipt");
        }
    }
    
}


