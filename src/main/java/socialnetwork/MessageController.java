package socialnetwork;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.domain.Mesaj;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

public class MessageController implements Initializable {

    public static Utilizator current_user;
    public static Utilizator foreign_user;

    @FXML
    public TextArea MessageArea;
    public ImageView SendMsgIcon;
    public TextArea InsertTextField;
    public Text BackText;
    public AnchorPane AnchorPaneMessage;

    Stage stage;

    public void OnSendMsgIconClicked(MouseEvent mouseEvent) {
        if(!Objects.equals(InsertTextField.getText(), "")){
            List<Utilizator> listRecieve = new ArrayList<>();
            listRecieve.add(foreign_user);
            Date date = new Date();
            loginController.mesajService.addMesaj(new Mesaj(current_user, listRecieve, InsertTextField.getText(), new Timestamp(date.getTime()), 0L));
            MessageArea.appendText("You: " + InsertTextField.getText()+"\n");
            InsertTextField.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    loginController.mesajService.Conversation_History_2(current_user.getId(), foreign_user.getId()).forEach(mesaj -> {MessageArea.appendText(mesaj);});}

    public void OnBack2menuClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) AnchorPaneMessage.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("meniu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
