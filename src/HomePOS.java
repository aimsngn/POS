import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * This is the "homepage" of the POS system.
 * 
 * It provides a menu with options to navigate to the transaction and sales functionalities,
 * specifically, it redirects to the TransactionPOS and SalesPOS classes as class objects.
 * It also handles shutdown processes, such as releasing resources, before exiting the program.
 */
public class HomePOS {
    Scanner console = new Scanner(System.in);       // Scanner for console input
    Connection conn;                                // Declares the connection used to interact with the database
    EmployeeManager employeeManager;                // Declares the EmployeeManager class for log-ins 
    Logger logger = LoggingUtility.getLogger();     // Retrieves the logger from the Logging Utility to log events

    /**
     * Constructor to initialize the objects with the passed database connection
     * 
     * @param connection used to interact with the database
     */
    public HomePOS(Connection connection) {
        this.conn = connection;
        this.employeeManager = new EmployeeManager(connection);
    }

    //This is the entry display of the program.
    //It displays the homepage and prompts the user to choose a destination.
    //It redirects based on user's choice.
    public void homeMenu() {
        String inputRedirection;
        
        while (true) { // Continuously loop the menu until user chooses to exit
            System.out.println();
            System.out.println("Enter 1: Transactions");
            System.out.println("Enter 2: Sales");
            System.out.println("Enter 3: Shut Down");
            System.out.print("--> ");
    
            inputRedirection = console.next().trim();
    
            switch (inputRedirection) {
                case "1":
                    transactions();
                    break;
                case "2":
                    sales();
                    break;
                case "3":
                    shutDown();
                    return; // Exits the loop, thus the program
                default:
                    System.out.println("Invalid input. Please enter a valid option.");
            }
        }
    }

    // This method creates an instance of the TransactionPOS to process a new transaction, then returns to the homepage.
    public void transactions(){
        int employeeID = -1;

        // Retrieves and logs-in the employee ID using the employeeManager class utility 
        employeeID = employeeManager.employeeLogIn(); 

        // If the employee ID is valid, it creates a TransactionPOS instance and runs it.
        if (employeeID != -1) {
            TransactionPOS transactionPos = new TransactionPOS(conn, employeeID);
            logger.info("Employee " + employeeID + " logged in"); 
            transactionPos.run();
        }
    }

    // This method creates an instance of the SalesPOS to check total sales and transaction details. It returns to homepage.
    public void sales(){
        SalesPOS salesPos = new SalesPOS(conn);
        salesPos.run();
    }

    // Releases all resources (handles shutdown operations).
    public void shutDown() {
        System.out.println("\nShutting down...");
         System.out.println("Logging Logs...");
        LoggingUtility.closeHandler();
    }

}