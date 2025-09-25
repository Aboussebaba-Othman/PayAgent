import config.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            System.out.println("Connexion réussie");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
