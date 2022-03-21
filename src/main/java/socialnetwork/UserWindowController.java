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
import javafx.scene.shape.FillRule;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socialnetwork.domain.Cerere;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserWindowController implements Initializable {
    @FXML
    public static Utilizator current_user;
    public static Utilizator foreign_user;
    public ImageView SearchImage;
    public TextField UserSearchField;

    public Text FriendStatusCommand;
    public ImageView SendMessage;
    public TableView user_table;
    public TableColumn<Utilizator, String> NameColumn;
    public TableColumn<Utilizator, String> SurnameColumn;
    public AnchorPane AnchorPaneUser;
    public TextField NameField;
    public Text BackText;
    public Button AcceptButton;
    public Button DeclineButton;

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

                    UserSearchField.setText(row.getItem().getNume() + " " + row.getItem().getPrenume());
                }
            });
            return row ;
        });

        NameField.setText(foreign_user.getNume() + " " + foreign_user.getPrenume());

        if(loginController.prietenieService.findOne(current_user.getId(), foreign_user.getId())!=null
                || loginController.prietenieService.findOne(foreign_user.getId(), current_user.getId())!=null)
        {
            FriendStatusCommand.setText("Delete friend");
        }
        else if(loginController.prietenieService.findOne(current_user.getId(), foreign_user.getId())==null
                && loginController.prietenieService.findOne(foreign_user.getId(), current_user.getId())==null
                && loginController.cererePrietenieService.findOne(foreign_user.getId(), current_user.getId())==null
                && loginController.cererePrietenieService.findOne(current_user.getId(), foreign_user.getId())==null){
            FriendStatusCommand.setText("Add friend");
        }
        else if(loginController.cererePrietenieService.findOne(foreign_user.getId(), current_user.getId())!=null){
            FriendStatusCommand.setText("Acc/Dec req");
            AcceptButton.setVisible(true);
            DeclineButton.setVisible(true);
        }
        else if(loginController.cererePrietenieService.findOne(current_user.getId(), foreign_user.getId())!=null){
            FriendStatusCommand.setText("Cancel req");
        }
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
        try {
            String[] arrstr = UserSearchField.getText().split(" ");
            if(loginController.utilizatorService.findByName(arrstr[0], arrstr[1])!=null)
            {stage = (Stage) AnchorPaneUser.getScene().getWindow();
                stage.close();
                foreign_user=loginController.utilizatorService.findByName(arrstr[0], arrstr[1]);
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UserWindow.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e){}
    }

    public void OnUserSearchFieldClicked(MouseEvent mouseEvent) {
    }

    public void OnBack2menuClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) AnchorPaneUser.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("meniu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void OnAcceptButtonClicked(MouseEvent mouseEvent) {
        if(Objects.equals(FriendStatusCommand.getText(), "Acc/Dec req")){loginController.cererePrietenieService.AcceptDecline(new Cerere(foreign_user.getId(), current_user.getId(), "pending"), "Accept");}
        AcceptButton.setVisible(false);
        DeclineButton.setVisible(false);
        FriendStatusCommand.setText("Delete friend");
    }

    public void OnDeclineButtonClick(MouseEvent mouseEvent) {
        if(Objects.equals(FriendStatusCommand.getText(), "Acc/Dec req")){loginController.cererePrietenieService.AcceptDecline(new Cerere(foreign_user.getId(), current_user.getId(), "pending"), "Decline");}
        AcceptButton.setVisible(false);
        DeclineButton.setVisible(false);
        FriendStatusCommand.setText("Add friend");
    }

    public void OnFriendStatusCommandClicked(MouseEvent mouseEvent) {
        if(Objects.equals(FriendStatusCommand.getText(), "Delete friend")){if(loginController.prietenieService.findOne(current_user.getId(), foreign_user.getId())!=null){loginController.prietenieService.StergePrietenie(current_user.getId(), foreign_user.getId());} else{loginController.prietenieService.StergePrietenie(foreign_user.getId(),current_user.getId());} FriendStatusCommand.setText("Add friend");}
        else if(Objects.equals(FriendStatusCommand.getText(), "Add friend")){loginController.cererePrietenieService.AdaugaCerere(new Cerere(current_user.getId(), foreign_user.getId(), "pending")); FriendStatusCommand.setText("Cancel req");}
        else if(Objects.equals(FriendStatusCommand.getText(), "Cancel req")){loginController.cererePrietenieService.StergeCerere(new Tuple<>(current_user.getId(), foreign_user.getId())); FriendStatusCommand.setText("Add friend");}
    }

    public void OnSendMessageToUserClicked(MouseEvent mouseEvent) throws IOException {
        stage = (Stage) AnchorPaneUser.getScene().getWindow();
        stage.close();
        MessageController.current_user=current_user;
        MessageController.foreign_user=foreign_user;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Message.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
