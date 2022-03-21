package socialnetwork;
import com.jfoenix.controls.JFXDrawer;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.loginController;
public class MeniuController implements Initializable {

    @FXML
    public ImageView NotifImage;
    public ImageView LogImage;
    public ImageView MsgImage;
    public ImageView SearchImage;
    public TextField UserSearchField;
    public JFXDrawer DrawerMsg;
    public JFXDrawer DrawerLog;
    public static Utilizator current_user;
    public TableView user_table;
    public TableColumn<Utilizator, String> NameColumn;
    public TableColumn<Utilizator, String> SurnameColumn;
    public AnchorPane AnchorPaneMenu;
    public Button LogoutButton;

    public MeniuController(){}

    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO ADD IN {} THE FUNCTION TO PRINT IN IDK THE FUCK WHAT A TABLE SOME USERS IDK
        UserSearchField.textProperty().addListener(o -> FilterUser());
        user_table.setVisible(false);
        user_table.setRowFactory(tableView -> {
            TableRow<Utilizator> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {

                    UserSearchField.setText(row.getItem().getNume() + " " + row.getItem().getPrenume());
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

    @FXML
    public void OnMsgImageClick(MouseEvent mouseEvent) throws IOException {
        DrawMsgController.stage = (Stage) AnchorPaneMenu.getScene().getWindow();
        DrawMsgController.current_user=current_user;
        if (DrawerMsg.isClosed()) {
            DrawerMsg.setDisable(false);
            DrawMsgController.current_user=current_user;
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("DrawMsg.fxml")));
            DrawerMsg.setSidePane(anchorPane);
            DrawerMsg.open();}
        else{DrawerMsg.close(); DrawerMsg.setDisable(true);}
    }

    public void OnNotifImageClicked(MouseEvent mouseEvent) throws IOException {
        if (DrawerMsg.isClosed()) {
            DrawerMsg.setDisable(false);
            DrawNotifController.current_user=current_user;
            ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("DrawNotif.fxml")));
            DrawerMsg.setSidePane(scrollPane);
            DrawerMsg.open();}
        else{DrawerMsg.close(); DrawerMsg.setDisable(true);}
    }

    public void OnLogImageClicked(MouseEvent mouseEvent) throws IOException {
        if (DrawerLog.isClosed()) {
            DrawerLog.setDisable(false);
            DrawLogController.current_user=current_user;
            ScrollPane scrollPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("DrawLog.fxml")));
            DrawerLog.setSidePane(scrollPane);
            DrawerLog.open();}
        else{DrawerLog.close(); DrawerLog.setDisable(true);}
    }

    public void OnUserSearchFieldClicked(MouseEvent mouseEvent) {

    }

    public void OnSearchImageClick(MouseEvent mouseEvent) {
        try {
            String[] arrstr = UserSearchField.getText().split(" ");
            if(loginController.utilizatorService.findByName(arrstr[0], arrstr[1])!=null)
            {stage = (Stage) AnchorPaneMenu.getScene().getWindow();
                stage.close();
                UserWindowController.current_user=current_user;
                UserWindowController.foreign_user=loginController.utilizatorService.findByName(arrstr[0], arrstr[1]);
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UserWindow.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e){}
    }

    public void OnLogOutButtonClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) AnchorPaneMenu.getScene().getWindow();
        stage.close();
        current_user=null;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}


