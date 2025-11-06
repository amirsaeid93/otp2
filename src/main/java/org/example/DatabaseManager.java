package org.example;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Locale;
import java.util.Properties;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/weight_db?useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS translations;");
            stmt.execute("DROP TABLE IF EXISTS ui_elements;");

            String createUiElementsTable = "CREATE TABLE ui_elements (" +
                    "element_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "element_key VARCHAR(255) NOT NULL UNIQUE" +
                    ");";

            String createTranslationsTable = "CREATE TABLE translations (" +
                    "translation_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "element_id INT," +
                    "language_code VARCHAR(10) NOT NULL," +
                    "translation_text VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL," +
                    "FOREIGN KEY (element_id) REFERENCES ui_elements(element_id)" +
                    ");";

            stmt.execute(createUiElementsTable);
            stmt.execute(createTranslationsTable);

            System.out.println("MariaDB database initialized successfully.");

            populateFromPropertiesFiles(conn);

        } catch (Exception e) {
            System.err.println("Failed to connect to or initialize MariaDB. Please check your connection details, ensure the server is running, and that the 'weight_db' database exists.");
            e.printStackTrace();
        }
    }

    private static void populateFromPropertiesFiles(Connection conn) throws Exception {
        Locale[] locales = {new Locale("en", "US"), new Locale("fr", "FR"), new Locale("ur", "PA"), new Locale("vi", "VN")};
        for (Locale locale : locales) {
            loadAndInsertProperties(conn, locale);
        }
        System.out.println("Database populated from properties files.");
    }

    private static void loadAndInsertProperties(Connection conn, Locale locale) throws Exception {
        String resourceName = "MessagesBundle_" + locale.toString() + ".properties";
        InputStream is = DatabaseManager.class.getClassLoader().getResourceAsStream(resourceName);

        if (is == null) {
            System.err.println("Could not find resource file: " + resourceName);
            return;
        }

        try (InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            Properties props = new Properties();
            props.load(reader);

            for (String key : props.stringPropertyNames()) {
                String value = props.getProperty(key);
                int elementId = getOrCreateUiElement(conn, key);

                String insertTranslation = "INSERT INTO translations (element_id, language_code, translation_text) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertTranslation)) {
                    pstmt.setInt(1, elementId);
                    pstmt.setString(2, locale.toLanguageTag());
                    pstmt.setString(3, value);
                    pstmt.executeUpdate();
                }
            }
        }
    }

    private static int getOrCreateUiElement(Connection conn, String key) throws SQLException {
        String selectSql = "SELECT element_id FROM ui_elements WHERE element_key = ?";
        try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
            selectPstmt.setString(1, key);
            ResultSet rs = selectPstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("element_id");
            } else {
                String insertSql = "INSERT INTO ui_elements (element_key) VALUES (?)";
                try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    insertPstmt.setString(1, key);
                    insertPstmt.executeUpdate();
                    ResultSet generatedKeys = insertPstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        throw new SQLException("Failed to get or create UI element for key: " + key);
    }

    public static String getTranslation(String key, Locale locale) {
        String sql = "SELECT t.translation_text " +
                "FROM translations t " +
                "JOIN ui_elements e ON t.element_id = e.element_id " +
                "WHERE e.element_key = ? AND t.language_code = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, key);
            pstmt.setString(2, locale.toLanguageTag());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("translation_text");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Translation not found for key '" + key + "' and locale '" + locale.toLanguageTag() + "'. Falling back to English.");
        return getTranslation(key, new Locale("en", "US"));
    }
}
