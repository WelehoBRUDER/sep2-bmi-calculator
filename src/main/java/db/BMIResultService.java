package db;

import java.sql.*;
public class BMIResultService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bmi_localization";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Test12";

    public static void saveResult(double weight, double height, double bmi, String language) {
        String query = "INSERT INTO bmi_results (weight, height, bmi, language) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, weight);
            stmt.setDouble(2, height);
            stmt.setDouble(3, bmi);
            stmt.setString(4, language);
            stmt.executeUpdate();
            System.out.println("BMI result saved to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
