package socialnetwork;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;
import socialnetwork.domain.Utilizator;
import socialnetwork.loginController;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
public class registerController {
    @FXML
    public Button SignUpProceedButton;
    public TextField SignUpNume;
    public TextField SignUpPrenume;
    public PasswordField SignUpParola;
    public TextField SignUpUsername;
    public void OnSignUpProceedButtonClick() throws NoSuchAlgorithmException {
        String nume = SignUpNume.getText();
        String prenume = SignUpPrenume.getText();
        String parola = SignUpParola.getText();
        String username = SignUpUsername.getText();
        String salt = Hashed.getSalt();
        if(loginController.utilizatorService.findByName(nume, prenume)!=null){
            Notifications.create().title("Invalid data").text("Nume si prenume exista deja").showError();
            SignUpNume.clear(); SignUpPrenume.clear();}
        else if(loginController.utilizatorService.findByUser_Name(username)!=null){
            Notifications.create().title("Invalid data").text("Username exista deja").showError();
        }
        else if(Objects.equals(nume, "Nume") || Objects.equals(prenume, "Prenume") || Objects.equals(parola, "Parola") || Objects.equals(username, "Username")){
            Notifications.create().title("Invalid data").text("Default parameters").showError();
        }
        else{loginController.utilizatorService.addUtilizator(new Utilizator(nume, prenume));
            loginController.utilizatorService.registerUser(loginController.utilizatorService.findByName(nume, prenume), username, parola, salt);
            Notifications.create().title("Sucess").text("User creat. Go back to login").showConfirm();
            SignUpNume.clear(); SignUpPrenume.clear(); SignUpParola.clear(); SignUpUsername.clear();}
    }
}