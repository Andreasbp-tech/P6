/*import utilities.DatabaseConnection;
import controller.AuthenticationController;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.getConnection(); // Opret forbindelse til databasen
        AuthenticationController.launch(); // Start appen på AuthenticationScreen
    }
}
// This is the main entry point for the application. It initializes the database
// connection and launches the authentication screen.
 */

import model.MedarbejderModel;
import view.AuthenticationView;
import controller.AuthenticationController;
import utilities.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        // Opret forbindelse til databasen
        DatabaseConnection.getConnection();

        // Instantiér Model, View og Controller
        MedarbejderModel model = new MedarbejderModel();
        AuthenticationView view = new AuthenticationView();
        AuthenticationController controller = new AuthenticationController(model, view);

        // Vis login skærmen
        view.show();
    }
}
