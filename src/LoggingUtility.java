import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class provides a centralized consistent logging mechanism for the point-of-sale application.
 * Includes a format of the log message and release of resources when the program exits.
 * 
 * It creates and configures a logger instance to capture various log messages associated with the application's behavior.
 * It captures log messages for different log levels and outputs them to the designated log file.
 */
public class LoggingUtility {
    // Creates an instance of the logger that is associated with the program
    private static final Logger logger = Logger.getLogger(LoggingUtility.class.getName()); 

    private static FileHandler fileHandler; //Declares the fileHandler for managing log files

    // Static initialization block runs when the class is loaded
    static {
        try {
            // Disables the default console handlers
            logger.setUseParentHandlers(false);

            // Establishing a file path based on its relative path
            Path filePath = Paths.get("files/pos_logs.txt");
            filePath = filePath.toAbsolutePath();

            // Initializes the FileHandler to log messages to the designated file path
            fileHandler = new FileHandler(filePath.toString(), true); 

            // Sets the custom format of the log message
            fileHandler.setFormatter(new Format());

            // Adds the logger to the file handler
            logger.addHandler(fileHandler);

            // Sets log level to capture all types of log levels (from finest to severe)
            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter method to retrieve the logger instance
    public static Logger getLogger() {
        return logger;
    }

    // Getter method to retrieve the file handler instance
    public static FileHandler getFileHandler(){
        return fileHandler;
    }

    // Closes the file handler to release resources. This method is called before the program exits.
    public static void closeHandler(){
        if (fileHandler != null){
            fileHandler.close();
        }
    }

}

// Custom format for log messages to ensure a consistent format for logging
class Format extends Formatter {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Override
    // Formats log records as: [DATE] log_level: log_message
    // Example: [2023-07-26 00:56:07] INFO: Employee 1 logged in
    public String format(LogRecord record) {
        return "[" + dateFormat.format(record.getMillis()) + "] "+ record.getLevel() + ": " + record.getMessage() + "\n";
    }
}

