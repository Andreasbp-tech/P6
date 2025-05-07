import controller.AuthenticationController;
import model.MedarbejderModel;
import utilities.DatabaseConnection;
import view.AuthenticationView;

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
