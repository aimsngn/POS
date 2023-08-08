import java.sql.*;
import java.util.logging.Logger;

/**
 * This class handles operations related to managing products in the database.
 * It provides methods to display all products, add new products, update existing products,
 * and remove products from the database.
 *
 * This class collaborates with a database connection to perform operations on the products table.
 * It utilizes a logger to record events.
 */
public class ProductManager {
    Connection conn;                                // Declares the connection used for database interaction
    Logger logger = LoggingUtility.getLogger();     // Retrieves the logger from the Logging Utility to log events
    
    /**
     * Constructor initialize the connection to the database
     * 
     * @param connection to the database
     */
    public ProductManager (Connection conn){
        this.conn = conn;

    }

    //This method displays all the products' names and prices from the database
    public void displayProducts(){
        try (Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM products")){
            
                while(resultSet.next()){
                    String product = resultSet.getString("product_name");
                    double price = resultSet.getDouble("price");
                    System.out.println(product + " || " + price);
                    System.out.println();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.severe("System can not display product menu");
        }
    }

    /**
     * This method adds a new product to the database
     * 
     * @param
     * Needs a call method that passes a product name and price
     */
    public void addProduct(String productName, double productPrice){
        try (PreparedStatement addProduct = conn.prepareStatement("INSERT INTO products (product_name, price) VALUES (?, ?)")) { 
            addProduct.setString(1, productName);
            addProduct.setDouble(2, productPrice);
            addProduct.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace(System.err);
            logger.severe("System can not add product");
        }
    }

    /**
     * This method updates an existing product in the database
     * 
     * @param
     * Needs a call method that passes the existing product ID, new name, and new price
     */
    public void updateProduct(int productID, String newProductName, double newProductPrice){
        try (PreparedStatement updateProduct = conn.prepareStatement("UPDATE products SET product_name = ?, price = ? WHERE product_id = ?")) {
            updateProduct.setString(1, newProductName);
            updateProduct.setDouble(2, newProductPrice);
            updateProduct.setInt(3, productID);
            updateProduct.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace(System.err);
            logger.severe("System can not update product");
        }
    }

    /**
     * This method removes an existing product in the database
     * 
     * @param
     * Needs a call method that passes the existing product ID
     */
    public void removeProduct(int productID){
        try (PreparedStatement removeProduct = conn.prepareStatement("DELETE FROM products WHERE product_id = ?")) {       
            removeProduct.setInt(1, productID);
            removeProduct.executeUpdate();
            
        } catch (SQLException e){
            e.printStackTrace(System.err);
            logger.severe("System can not remove product");
        }
    }

}
    

