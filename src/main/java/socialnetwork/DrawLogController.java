package socialnetwork;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import socialnetwork.domain.Utilizator;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DrawLogController implements Initializable {
    public static Utilizator current_user;
    public TextArea TextAreaLog;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginController.prietenieService.getAll().forEach(prietenie -> {
            if(Objects.equals(prietenie.getId().getL(), current_user.getId()) || Objects.equals(prietenie.getId().getR(), current_user.getId())){
                TextAreaLog.appendText(loginController.utilizatorService.findOne(prietenie.getId().getL()).getNume() + " and " +
                        loginController.utilizatorService.findOne(prietenie.getId().getR()).getNume() + ", friends since " +
                        prietenie.getDate().getDayOfMonth() + " " + prietenie.getDate().getMonth() + " " + prietenie.getDate().getYear() + "\n\n");
            }});
    }
}
