import service.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Connexion réussie");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
