import java.sql.Connection;
import java.sql.SQLException;

// Singleton Database Connection Class
// This implements the Singleton design pattern to ensure only one database connection exists
public class DatabaseConnection {
    // Static instance - the only instance that will ever exist
    private static DatabaseConnection instance;

    // The actual database connection
    private Connection connection;

    // Encryption key for sensitive data
    private final String encryptionKey = "financialAppSecureKey123";

    /**
     * Private constructor - prevents direct instantiation from outside
     * This is key to the Singleton pattern
     */
    private DatabaseConnection() {
        try {
            // Initialize database connection
            String url = "jdbc:sqlite:financial_manager.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Database connection established successfully");

            // Initialize database schema if needed
            initializeDatabase();
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * The access point to the single instance
     * Creates the instance if it doesn't exist yet
     * @return The singleton instance
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Get the connection for database operations
     * @return The SQL connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Creates database tables if they don't exist
     */
    private void initializeDatabase() {
        try {
            Statement stmt = connection.createStatement();

            // Create assets table
            stmt.execute("CREATE TABLE IF NOT EXISTS assets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "value REAL NOT NULL, " +
                    "purchase_date TEXT, " +
                    "additional_data TEXT, " + // JSON for type-specific data
                    "last_updated TEXT)");

            // Create transactions table
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "asset_id INTEGER, " +
                    "amount REAL NOT NULL, " +
                    "transaction_date TEXT NOT NULL, " +
                    "description TEXT, " +
                    "transaction_type TEXT, " + // 'payment', 'interest', 'purchase', etc.
                    "FOREIGN KEY(asset_id) REFERENCES assets(id))");

            // Create historical values table
            stmt.execute("CREATE TABLE IF NOT EXISTS historical_values (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "asset_id INTEGER NOT NULL, " +
                    "value_date TEXT NOT NULL, " +
                    "value REAL NOT NULL, " +
                    "FOREIGN KEY(asset_id) REFERENCES assets(id))");

            // Create user table with encryption
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL UNIQUE, " +
                    "password_hash TEXT NOT NULL, " +
                    "salt TEXT NOT NULL, " +
                    "last_login TEXT)");

            stmt.close();
            System.out.println("Database schema initialized");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Encrypts sensitive data using AES encryption
     * @param data The data to encrypt
     * @return Encrypted string
     */
    public String encryptData(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            System.err.println("Encryption error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Decrypts data that was encrypted with encryptData
     * @param encryptedData The encrypted data
     * @return Decrypted string
     */
    public String decryptData(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
        } catch (Exception e) {
            System.err.println("Decryption error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Closes the database connection properly
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
}
