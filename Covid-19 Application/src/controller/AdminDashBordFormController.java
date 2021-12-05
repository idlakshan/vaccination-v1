package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AdminDashBordFormController implements Initializable {
    public AnchorPane pane2;

    public JFXButton btnAntigens;
    public JFXButton btnMore;
    public JFXButton btnUserModify;
    public JFXButton btnVaccine;
    public JFXButton btnHome;
    public AnchorPane pane1;
    public Label lblTime;
    public ImageView txtExit;
    public ImageView txtMenu;
    public AnchorPane AdminDashBoardContext;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initClock();


        txtExit.setOnMouseClicked(event -> {
            System.exit(0);
        });
        pane1.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        txtMenu.setOnMouseClicked(event -> {

            pane1.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();

        });
        pane1.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();

        });

    }

    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss a");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void VaccineOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("../view/VaccineForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardContext.getChildren().clear();
        AdminDashBoardContext.getChildren().add(load);
    }

    public void testingKitsOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("../view/TestingKitsForm.fxml");
        Parent load = null;
            load = FXMLLoader.load(resource);
        AdminDashBoardContext.getChildren().clear();
        AdminDashBoardContext.getChildren().add(load);
    }


    public void usersModifyOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("../view/UserModifyForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardContext.getChildren().clear();
        AdminDashBoardContext.getChildren().add(load);
    }

    public void moreOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("../view/MoreForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardContext.getChildren().clear();
       AdminDashBoardContext.getChildren().add(load);


    }

}
