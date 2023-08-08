import java.util.logging.Logger;
import java.util.*;
import java.sql.*;

/**
 * The SalesPOS class manages the display of current total sales and today's transaction details.
 * Enables quick access to transaction records for efficient business monitoring and management.
 * 
 * It provides functionality to view today's total sales amount and retrieve transaction information
 * for the current date. Users can choose to view all today's transactions or exit back to the main menu.
 *
 * This class collaborates with a database connection to retrieve sales and transaction data. It utilizes
 * a logger to record events and prompts user input through the console.
 */
public class SalesPOS {
    Connection conn;                                // Declares the connection used for database interaction
    Logger logger = LoggingUtility.getLogger();     // Retrieves the logger from the Logging Utility to log events      
    Scanner console = new Scanner(System.in);       // Scanner for console input
    
    /**
     * Constructor to innitialize the connection to retrieve transaction details
     * 
     * @param connection to the database
     */
    public SalesPOS (Connection connection){
        this.conn = connection;
    }

    /**
     * This is the entry point of the program.
     * It displays today's total sales amount, and offer users to view all transaction details.
     */
    public void run(){
        try (Statement getCurrentTotalSale = conn.createStatement()) {
            try(ResultSet resultCurrentTotalSale = getCurrentTotalSale.executeQuery("SELECT sum(subtotal) as subtotal FROM transactions WHERE DATE(trans_date) = CURDATE()")) {
                if(resultCurrentTotalSale.next()){
                    double totalSales = resultCurrentTotalSale.getDouble("subtotal");
                    System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
                    System.out.println("Today's total sales: " + totalSales);
                    System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            logger.severe("System can not retrieve sale");
            LoggingUtility.closeHandler();
            System.exit(1);
        
        }
    
        // Offers users to view all transaction details. Exits the program afterwards.
        String choice;
        do {
            choice = "";
            System.out.println("\n1: Show all of today's transactions\n2: Exit");
            System.out.print("--> ");
            choice = console.next();

            if(choice.equalsIgnoreCase("1")){
                todaysTransactions();
                System.out.println("\n\nCommand complete. Exiting now...");
            }

        } while (!(choice.equalsIgnoreCase("1") || choice.equalsIgnoreCase("2")));

    }


    //This method displays all of today's transaction details
    public void todaysTransactions() {
        System.out.println("\n\n--------------------------");

        try (Statement getCurrentTransactions = conn.createStatement()) {
            try(ResultSet resultCurrentTransactions = getCurrentTransactions.executeQuery("SELECT id, trans_date, employee_id, subtotal FROM transactions WHERE DATE(trans_date) = CURDATE()")) {
                
                // Incase there's no transactions yet  
                if (!resultCurrentTransactions.isBeforeFirst()) {        
                    System.out.println("No transactions found for the current date.");
                
                // If there's at least one transaction
                } else {
                    while(resultCurrentTransactions.next()){
                        int transactionID = resultCurrentTransactions.getInt("id");
                        int employeeID = resultCurrentTransactions.getInt("employee_id");
                        Timestamp dateTime = resultCurrentTransactions.getTimestamp("trans_date");
                        double subtotal = resultCurrentTransactions.getDouble("subtotal");

                        System.out.println("Transaction ID:   " + transactionID);
                        System.out.println("Transaction Date: " + dateTime);
                        System.out.println("Employee ID:      " + employeeID);
                        System.out.println("Subtotal:         " + subtotal);
                        System.out.println("--------------------------");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            logger.severe("System can not retrieve transactions");
            LoggingUtility.closeHandler();
            System.exit(1);
        
        }
    }

}
