package socialnetwork;
import com.jfoenix.controls.JFXDrawer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.Notifications;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.*;
import socialnetwork.ui.Console;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
public class loginController extends Application {

    @FXML
    public Button RegisterButton;
    public JFXDrawer drawer;
    public Button LoginButton;
    public TextField LoginUsername;
    public PasswordField LoginPassword;
    public Utilizator current_user;
    public static UtilizatorService utilizatorService;
    public static PrietenieService prietenieService;
    public static UtilizatoriPrieteniiService utilizatoriPrieteniiService;
    public static MesajService mesajService;
    public static CererePrietenieService cererePrietenieService;
    public AnchorPane Anchorpane;

    public loginController(){}

    Stage stage;

    @FXML
    public void onRegisterButtonClick() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("register.fxml")));
        drawer.setSidePane(anchorPane);
        if (drawer.isClosed()) {
            RegisterButton.setText("Back to Login");
            drawer.setDisable(false);
            drawer.open();}
        else{drawer.close(); drawer.setDisable(true); RegisterButton.setText("Register");}
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void onLoginButtonClick() throws IOException {
        String username = LoginUsername.getText();
        String parola = LoginPassword.getText();
        if (utilizatorService.validateLogin(username, parola)!=1){
            Notifications.create().title("Invalid data").text("Invalid username and/or password, try again").showError();}
        else if(utilizatorService.findByUser_Name(username)==null){
            Notifications.create().title("Invalid data").text("Invalid username and/or password, try again").showError();}
        else{current_user=utilizatorService.findByUser_Name(username);
            stage = (Stage) Anchorpane.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("meniu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
            MeniuController.current_user=current_user;
            Notifications.create().title("Welcome").text("Welcome, " + current_user.getNume() + " " + current_user.getPrenume() + " !").showConfirm();
        }
    }
}