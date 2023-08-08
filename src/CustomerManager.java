import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * This class faciliates and manages customers' information and sign-ups in the point-of-sale application.
 */
public class CustomerManager {
    Connection conn;                                            // Declares the connection used for database interaction
    Scanner console = new Scanner(System.in);                   // Scanner for console user input
    InputValidation inputValidator = new InputValidation();     // Creates an instance of the InputValidation class to validate user inputs
    Logger logger = LoggingUtility.getLogger();                 // Retrieves the logger from the Logging Utility to log events
    int employeeID;                                             // Declares the current employee ID handling the sign-up & query
    int customerID = -1;                                        // Initializes the customer's ID

    /**
     * Constructor to initialize the connection and employee ID
     * 
     * @param conn used to interact with the database
     * @param employeeID to record which employee signed up the customer
     */
    public CustomerManager(Connection conn, int employeeID){
        this.conn = conn;
        this.employeeID = employeeID;
    }

    /**
     * This method handles the query to search for a customer. If not found, it prompts them to sign up
     * 
     * @return valid customer ID or -1 (if customer isn't found and doesn't sign up)
     */
    public int searchCustomer(){
        String phoneNumber = inputValidator.inputPhone();

        try (PreparedStatement searchPhone = conn.prepareStatement("SELECT customer_first, customer_last, customer_id FROM customers WHERE phone_number = ?")) {
            searchPhone.setString(1, phoneNumber);
            try (ResultSet phoneResult = searchPhone.executeQuery()) {

                // If the customer is found, it retrieves their information
                if(phoneResult.next()){
                    String firstName = phoneResult.getString("customer_first");
                    String lastName = phoneResult.getString("customer_last");

                    // Ensures if the customer's information retrieved is correct.
                    System.out.print("\nIs the customer's name: " + firstName + " " + lastName + "? Enter yes/no --> ");
                    String prompt = inputValidator.inputAnswer();
                    customerID =  prompt.equalsIgnoreCase("yes") ? phoneResult.getInt("customer_id") : signUpPrompt();

                // If the customer doesn't exist
                } else {
                    System.out.println("No customer found!");
                    customerID = signUpPrompt();
                }
            }

        } catch (SQLException e){
            e.printStackTrace(System.err);
        }

        return customerID;
    }

    /**
     * This prompts customer's if they want to sign up for customer loyalty.
     * 
     * @return either valid customer ID or -1 (if they don't sign up)
     */
    public int signUpPrompt(){
        System.out.print("\nWould the customer like to sign up? Enter yes/no --> ");
        String prompt = inputValidator.inputAnswer();

        if (prompt.equalsIgnoreCase("yes")) {
            customerID = signUp();
        } else {
            System.out.print("\nSign up: Cancelled");
        }

        return customerID;
    }

    /**
     *  Signs up a customer by collecting their information and inserts it into the database.
     *
     * @return The valid customer ID after successful sign-up
     */
    public int signUp(){
        String phone = inputValidator.inputPhone();
        System.out.print("What's your first name? --> ");
        String firstName = console.next();

        System.out.print("What's your last name? --> ");
        String lastName = console.next();

        try (PreparedStatement signUp = conn.prepareStatement("INSERT INTO customers(customer_first, customer_last, phone_number) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            signUp.setString(1, firstName);
            signUp.setString(2, lastName);
            signUp.setString(3, phone);
            signUp.executeUpdate();

            try (ResultSet generatedKeys = signUp.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customerID = generatedKeys.getInt(1);
                    System.out.println("Great, customer " + firstName + " " + lastName + " signed up! The associated phone number is " + phone);

                    // Logs which employee signed the customer (Will be used to track employee performance)
                    logger.fine("Employee " + employeeID + " added a new customer. Customer ID: [" + customerID + "]. Customer Phone: [" + phone + "]. Customer Name: [" + firstName + "]");
                       
                    return customerID;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace(System.err);
            logger.warning("Employee " + employeeID + " can not add new customer into database.");
        }

        return -1; // Return -1 to handle exceptions
    }
    
}
