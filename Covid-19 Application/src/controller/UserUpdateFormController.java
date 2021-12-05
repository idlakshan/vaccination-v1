package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.User;


import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UserUpdateFormController implements Initializable {
    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtPassword;
    public TextField txtRole;
    public JFXButton btnDone;

    private UserModifyFormController loadEvent;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern emailPattern = Pattern.compile("^[a-z0-9]{4,}[@][a-z]{2,}[.][a-z]{3,4}$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9@#]{4,10}$");
    Pattern rolePattern = Pattern.compile("^(Admin|admin|User|user)$");



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnDone.setDisable(true);
        storeValidation();
    }

    private void storeValidation() {
        map.put(txtUsername,namePattern);
        map.put(txtEmail,emailPattern);
        map.put(txtPassword,passwordPattern);
        map.put(txtRole,rolePattern);
    }

    public void doneUsersOnAction(ActionEvent event) {
        try {
            boolean isUpdated = UserController.updateUser(new User(
                    txtUsername.getText(),
                    txtEmail.getText(),
                    txtPassword.getText(),
                    txtRole.getText()
            ));
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"User has been updated").show();
                loadEvent.loadData();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update user was failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING,"Update user was failed").show();
        }
    }
    public void setEvent(UserModifyFormController loadEvent) {
        this.loadEvent = loadEvent;
    }

    private Object validate() {
        btnDone.setDisable(true);
        for (TextField textFieldKey:map.keySet()) {
            Pattern patternValue=map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()){
                if(!textFieldKey.getText().isEmpty()){
                    textFieldKey.setStyle("-fx-text-fill: red");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-text-fill: green");
        }
        btnDone.setDisable(false);
        return true;
    }


    public void updateUsersTxtKeyReleased(KeyEvent keyEvent) {
        Object updateResponse = validate();

        if(keyEvent.getCode()==KeyCode.ENTER) {
            if(updateResponse instanceof TextField){
                TextField errorText=(TextField) updateResponse;
                errorText.requestFocus();
            }else if (updateResponse instanceof Boolean){
                new Alert(Alert.AlertType.INFORMATION,"Added").show();
            }
        }
    }
}
