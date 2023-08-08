import java.sql.*;

/**
 * This class serves as the entry point for the Point of Sale (POS) application.
 * It establishes a connection with the database and, upon successful connection, launches the POS system's homepage.
 */
public class DatabaseManager {

    // This method establishes a database connection, initializes the POS, and launches the homepage
    public static void main(String[] args) {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/shop_three";
            String username = "root";
            String password = "password";
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (connection != null) {
            HomePOS pos = new HomePOS(connection);
            pos.homeMenu();
        }
    }
}


