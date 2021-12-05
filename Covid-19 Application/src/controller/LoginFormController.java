package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public Pane pane1;
    public Pane pane2;
    public Pane pane3;
    public Pane pane4;
    public Label txtExit;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtExit.setOnMouseClicked(event -> {
            System.exit(0);
        });
        SliderAnimation();
    }

    private void SliderAnimation() {
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(3),pane4);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(3),pane3);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {

                FadeTransition fadeTransition2=new FadeTransition(Duration.seconds(3),pane2);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();

                fadeTransition2.setOnFinished(event2 -> {

                    FadeTransition fadeTransition3=new FadeTransition(Duration.seconds(3),pane2);
                    fadeTransition3.setFromValue(1);
                    fadeTransition3.setToValue(0);
                    fadeTransition3.play();

                    fadeTransition3.setOnFinished(event3 -> {

                        FadeTransition fadeTransition4=new FadeTransition(Duration.seconds(3),pane3);
                        fadeTransition4.setFromValue(1);
                        fadeTransition4.setToValue(0);
                        fadeTransition4.play();

                        fadeTransition4.setOnFinished(event4 -> {

                            FadeTransition fadeTransition5=new FadeTransition(Duration.seconds(3),pane4);
                            fadeTransition5.setFromValue(1);
                            fadeTransition5.setToValue(0);
                            fadeTransition5.play();

                            fadeTransition5.setOnFinished(event5 -> {
                                SliderAnimation();
                            });
                        });
                    });
                });
            });
        });
    }

    public void loginOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        User u=new User(txtUserName.getText(),txtPassword.getText());
        String roles=new UserController().login(u);

        if(roles.equals("admin")){
            URL resource =getClass().getResource("../view/AdminDashBordForm.fxml");
            Parent load= FXMLLoader.load(resource);
            Scene scene=new Scene(load);
            Stage stage=(Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);


        }else if(roles.equals("user")){
            URL resource =getClass().getResource("../view/UserDashBordForm.fxml");
            Parent load= FXMLLoader.load(resource);
            Scene scene=new Scene(load);
            Stage stage=(Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);

        }else{
            new Alert(Alert.AlertType.WARNING,"Something went wrong Please try again..").show();
            txtUserName.clear();
            txtPassword.clear();
        }


    }
}
