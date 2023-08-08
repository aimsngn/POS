import java.sql.*;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;
import java.util.logging.Logger;

/**
 * This class handles employee log-ins for the point-of-sale application.
 * It verifies employee's credentials using employee's ID and password.
 * It only grants access when the employee exists, and inputted password matches the hashed password
 */
public class EmployeeManager {
    Connection conn;                                           // Declares the connection used for database interaction
    Scanner console = new Scanner(System.in);                  // Scanner for console input
    InputValidation inputValidator = new InputValidation();    // Creates an instance of the InputValidation class to validate user inputs
    Logger logger = LoggingUtility.getLogger();                // Retrieves the logger from the LoggingUtility to log events

    /**
     * Constructor to initialize the database connection
     * 
     * @param conn used to interact with the database
     */
    public EmployeeManager(Connection conn){ 
        this.conn = conn;
    }

    /**
     * This method asks for the employee ID and password
     * 
     * It checks if the inputted password matches the hashed password from the database.
     * If it is, the employee is logged. Otherwise, it prompts to re-enter or exit.
     * 
     * @return either logged-in employee ID or exit code -1
     */
    public int employeeLogIn() {
        try {
            int empID = -1;
            boolean chckEmployee = true; // Boolean that determines loop
            String password;
            System.out.print("\nLog in or enter -1 to exit.");
    
            // Loops until user's enter valid ID and password, or chooses to exits.
            do {
                System.out.println();

                // If either the employee doesn't exist or password is incorrect
                if (!chckEmployee) {
                    System.out.println("Incorrect ID or Password. Try again or input -1 to return home.");
                }
    
                System.out.print("Enter Employee ID: ");
                empID = inputValidator.inputNumber();
                if (empID == -1) {  // If user chooses to exit, it returns exit code -1
                    System.out.println("Exiting...");
                    return -1;
                }

                System.out.print("Enter Password: ");
                password = console.nextLine();               
                if (password.equals("-1")) {  // If user chooses to exit, it returns exit code -1
                    System.out.println("Exiting...");
                    return -1;
                }
    
                try (PreparedStatement employeeLogQuery = conn.prepareStatement("SELECT first_name, passwords FROM employees WHERE emp_id = ?")) {
                    employeeLogQuery.setInt(1, empID);
                    try (ResultSet employeeLogResults = employeeLogQuery.executeQuery()) {
    
                        // Checks if the employee exists and password is correct
                        if (employeeLogResults.next()) {
                            String comparePassword = employeeLogResults.getString("passwords");
                            
                            // Compares inputted password with stored hashed password from the database
                            chckEmployee = BCrypt.checkpw(password, comparePassword); 
                        
                        } else {
                            chckEmployee = false;
                        }
    
                        if (chckEmployee) { 
                            String name = employeeLogResults.getString("first_name");
                            System.out.println("\n~~~Welcome " + name + "~~~");
                            return empID;
                        }
                    }
                }
    
            } while (!chckEmployee);
    
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warning("System can not retrieve employee");
        }
    
        return -1; // Returns -1 to handle exceptions
    }

}

