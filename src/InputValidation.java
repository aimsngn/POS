import java.util.*;

/**
 * This utility class provides methods for validating user inputs.
 * 
 * It checks for "yes/no" inputs, valid 10-digit phone numbers, and valid integers.
 * New instances of this class whenever this utility is needed.
 */
public class InputValidation {
    String stringInput;                         // Used for any string inputs
    int intInput;                               // Used for any integer inputs
    Scanner console = new Scanner(System.in);   // Scanner for console user inputs

    /**
     * Prompts the user for an answer ("yes" or "no") and keeps prompting until a valid input is provided.
     *
     * @return The valid "yes" or "no" answer entered by the user.
     */
    public String inputAnswer(){
        do {
            stringInput = console.next();

            if (!stringInput.equalsIgnoreCase("yes") && !stringInput.equalsIgnoreCase("no")) {
                System.out.print("Please enter yes or no --> ");
            }

        } while (!stringInput.equalsIgnoreCase("yes") && !stringInput.equalsIgnoreCase("no"));

        console.nextLine();
        return stringInput;
    }

    /**
     * Prompts the user for a 10-digit phone number and keeps prompting until a valid input is provided.
     *
     * @return The valid 10-digit phone number entered by the user.
     */
    public String inputPhone() { 
        do {
            System.out.print("\nEnter phone number xxx-xxx-xxxx: ");
            stringInput = console.nextLine();
    
            // Only checks digits (\\d+), it trims the input to isolate the digits.
            stringInput = stringInput.replace(" ", "").replace("-", "");
    
            if (stringInput.length() != 10 || !stringInput.matches("\\d+")) {
                System.out.println("Please enter a valid ten-digit number.");
            }
        } while (stringInput.length() != 10 || !stringInput.matches("\\d+"));
    
        return stringInput;
    }

    /**
     * Prompts the user for an integer and keeps prompting until a valid input is provided.
     *
     * @return The valid integer entered by the user.
     */
    public int inputNumber(){
        while(!console.hasNextInt()){
            System.out.print("Enter a valid number:");
            console.next();
        }

        intInput = console.nextInt();
        console.nextLine();
        return intInput;

    }

}
