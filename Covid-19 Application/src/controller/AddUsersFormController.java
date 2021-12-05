package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.User;



import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class AddUsersFormController implements Initializable {
    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtRole;
    public JFXButton btnSave;
    public TextField txtPassword;
    public AnchorPane ancName;
    public AnchorPane ancEmail;
    public AnchorPane ancPassword;
    public AnchorPane ancRole;


    private UserModifyFormController loadEvent;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern emailPattern = Pattern.compile("^[a-z0-9]{4,}[@][a-z]{2,}[.][a-z]{3,4}$");
    Pattern passwordPattern = Pattern.compile("^[A-z0-9@#]{4,10}$");
    Pattern rolePattern = Pattern.compile("^(Admin|admin|User|user)$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);
        storeValidation();
    }

    private void storeValidation() {
        map.put(txtUsername,namePattern);
        map.put(txtEmail,emailPattern);
        map.put(txtPassword,passwordPattern);
        map.put(txtRole,rolePattern);
    }

    public void saveUsersOnAction(ActionEvent event) {
        try {
            boolean isSave = UserController.saveUser(new User(
                    txtUsername.getText(),
                    txtEmail.getText(),
                    txtPassword.getText(),
                    txtRole.getText()
            ));
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                loadEvent.loadData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void setEvent(UserModifyFormController loadEvent) {
        this.loadEvent = loadEvent;
    }

    public void addUsersTxtKeyReleased(KeyEvent keyEvent) {

        Object addResponse = validate();

       if(keyEvent.getCode()==KeyCode.ENTER) {
          if(addResponse instanceof TextField){
              TextField errorText=(TextField) addResponse;
              errorText.requestFocus();
          }else if (addResponse instanceof Boolean){
              new Alert(Alert.AlertType.INFORMATION,"Added").show();
          }
       }
    }
    private Object validate() {
        btnSave.setDisable(true);
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
        btnSave.setDisable(false);
        return true;
    }
}
