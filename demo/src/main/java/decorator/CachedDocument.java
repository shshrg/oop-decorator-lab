package decorator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class CachedDocument implements Document {
    private final Document document;
    private final String cacheDbUrl = "jdbc:sqlite:cache.db";

    public CachedDocument(Document document) {
        this.document = document;
        initializeCache();
    }

    private void initializeCache() {
        try (Connection conn = DriverManager.getConnection(cacheDbUrl);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS cache (gcsPath TEXT PRIMARY KEY, content TEXT)";
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String parse() {
        String gcsPath = ((SmartDocument) document).gcsPath;
        String cachedContent = getCachedContent(gcsPath);
        if (cachedContent != null) {
            return cachedContent;
        }

        String content = document.parse();
        cacheContent(gcsPath, content);
        return content;
    }

    private String getCachedContent(String gcsPath) {
        try (Connection conn = DriverManager.getConnection(cacheDbUrl);
             PreparedStatement pstmt = conn.prepareStatement("SELECT content FROM cache WHERE gcsPath = ?")) {
            pstmt.setString(1, gcsPath);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("content");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void cacheContent(String gcsPath, String content) {
        try (Connection conn = DriverManager.getConnection(cacheDbUrl);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO cache (gcsPath, content) VALUES (?, ?)")) {
            pstmt.setString(1, gcsPath);
            pstmt.setString(2, content);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}