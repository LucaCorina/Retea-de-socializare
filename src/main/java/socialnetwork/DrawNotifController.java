package socialnetwork;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import socialnetwork.domain.Utilizator;

import java.net.URL;
import java.util.ResourceBundle;

public class DrawNotifController implements Initializable {
    public static Utilizator current_user;
    @FXML
    public TextArea TextAreaNotif;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginController.cererePrietenieService.getAllRecievedByUser(current_user.getId()).forEach(cerere -> {
            TextAreaNotif.appendText(loginController.utilizatorService.findOne(cerere.getId_sender()).getNume() + " " +
                    loginController.utilizatorService.findOne(cerere.getId_sender()).getPrenume() + " sent you a friend request\n\n");});
    }
}
