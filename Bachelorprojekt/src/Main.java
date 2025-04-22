import utilities.DatabaseConnection;
import view.AuthenticationPage;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.getConnection(); // Opret forbindelse til databasen
        AuthenticationPage.launch(); // Start appen på AuthenticationScreen
    }
}
// This is the main entry point for the application. It initializes the database
// connection and launches the authentication screen.