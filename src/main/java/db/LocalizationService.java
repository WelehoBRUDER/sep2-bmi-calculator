package db;

import java.sql.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocalizationService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bmi_localization";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Map<String, String> getLocalizedStrings(Locale locale) {
        Map<String, String> strings = new HashMap<>();
        String lang = locale.getLanguage();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT `key`, value FROM localization_strings WHERE language = ? ";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, lang);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    strings.put(rs.getString("key"), rs.getString("value"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
