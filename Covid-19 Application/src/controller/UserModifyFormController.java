package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import view.tm.UsersTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserModifyFormController implements Initializable {

    public AnchorPane userModifyContext;
    public TableView<UsersTm> tblUserModify;
    public TableColumn colUsername;
    public TableColumn colEmail;
    public JFXTextField txtSearch;
    public TableColumn colPassword;
    public TableColumn colRole;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnAddUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("roles"));

        tblUserModify.getColumns().setAll(colUsername, colEmail, colPassword, colRole);

        tblUserModify.setItems(loadTableData());

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

    }


    public ObservableList<UsersTm> loadTableData() {
        try {
            List<User> allUsers = UserController.getAllUsers();
            ObservableList<UsersTm> userData = FXCollections.observableArrayList();
            for (User user : allUsers) {
                userData.add(new UsersTm(
                        user.getUserName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRoles()
                ));
            }
            return userData;

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }


    public void usersUpdateOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UserUpdateForm.fxml"));
            Parent parent = loader.load();
            UserUpdateFormController controller = loader.<UserUpdateFormController>getController();
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void usersDeleteOnAction(ActionEvent event) {
        try {
            String username = tblUserModify.getSelectionModel().getSelectedItem().getUsername();
            boolean isDeleted = UserController.deleteUser(username);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                tblUserModify.getItems().setAll(loadTableData());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try again").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING, "Try again").show();
        }
    }

    public void addUsersOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddUsersForm.fxml"));
            Parent parent = loader.load();
            AddUsersFormController controller = loader.<AddUsersFormController>getController();
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        tblUserModify.getItems().setAll(loadTableData());
    }

    public void searchUsersOnAction(ActionEvent event) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {
            List<User> users = UserController.searchUsers(value);
            ObservableList<UsersTm> userData = FXCollections.observableArrayList();
            for (User user : users) {
                userData.add(new UsersTm(
                        user.getUserName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRoles()
                ));
            }

            tblUserModify.getItems().setAll(userData);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onTableDataSelect(MouseEvent mouseEvent) {

    }
}
