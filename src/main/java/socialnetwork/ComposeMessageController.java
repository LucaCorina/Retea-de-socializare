package socialnetwork;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.domain.Mesaj;
import socialnetwork.domain.Utilizator;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ComposeMessageController implements Initializable {

    public static Utilizator current_user;

    @FXML
    public ImageView SearchImage;
    public TextField UserSearchField;
    public TableView user_table;
    public TableColumn<Utilizator, String> NameColumn;
    public TableColumn<Utilizator, String> SurnameColumn;
    public Text BackText;
    public TextArea UserListArea;
    public ImageView SendMsgIcon;
    public TextArea InsertTextField;
    public AnchorPane AnchorPaneCompose;
    public List<Utilizator> lstuser = new ArrayList<>();
    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserSearchField.textProperty().addListener(o -> FilterUser());
        user_table.setVisible(false);
        user_table.setRowFactory(tableView -> {
            TableRow<Utilizator> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {

                    if(!lstuser.contains(loginController.utilizatorService.findByName(row.getItem().getNume(), row.getItem().getPrenume())))
                        {lstuser.add(loginController.utilizatorService.findByName(row.getItem().getNume(), row.getItem().getPrenume()));
                        UserListArea.appendText(row.getItem().getNume() + " " + row.getItem().getPrenume() + "\n");}
                }
            });
            return row ;
        });
    }

    public void FilterUser(){
        NameColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getNume()));
        SurnameColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getPrenume()));
        Predicate<Utilizator> p1 = n -> n.getNume().startsWith(UserSearchField.getText()) && UserSearchField.getText()!="";
        Predicate<Utilizator> p2 = n -> n.getPrenume().startsWith(UserSearchField.getText()) && UserSearchField.getText()!="";
        Predicate<Utilizator> p3 = n -> !(n.getNume().equals(current_user.getNume())&&n.getPrenume().equals(current_user.getPrenume()));
        ObservableList<Utilizator> lst = FXCollections.observableList(StreamSupport.stream(loginController.utilizatorService.getAll().spliterator(), false).filter(p1.or(p2).and(p3)).collect(Collectors.toList()));
        if(lst.isEmpty()){user_table.setVisible(false);}
        else{user_table.setVisible(true); user_table.setItems(lst);}
    }

    public void OnSearchImageClick(MouseEvent mouseEvent) {
    }

    public void OnUserSearchFieldClicked(MouseEvent mouseEvent) {
    }

    public void OnBack2menuClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) AnchorPaneCompose.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("meniu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void OnSendMsgIconClicked(MouseEvent mouseEvent) {
        if(!Objects.equals(InsertTextField.getText(), "") && !lstuser.isEmpty()){
            Date date = new Date();
            loginController.mesajService.addMesaj(new Mesaj(current_user, lstuser, InsertTextField.getText(), new Timestamp(date.getTime()), 0L));
            InsertTextField.clear();
            lstuser.clear();
            UserListArea.clear();
        }
    }
}
