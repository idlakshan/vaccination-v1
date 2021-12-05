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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class UserDashBordFormController implements Initializable {

    public ImageView txtExit;
    public ImageView txtMenu;
    public AnchorPane pane1;
    public AnchorPane pane2;
    public JFXButton btnMore;
    public JFXButton btnPcr;
    public JFXButton btnAntigens;
    public JFXButton btnHome;
    public JFXButton btnVaccination;
    public JFXButton btnPlaceOrder;
    public AnchorPane ControllerDashBoardContext;
    public Label lblTime;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initClock();

        txtExit.setOnMouseClicked(event -> {
            System.exit(0);
        });
        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        txtMenu.setOnMouseClicked(event -> {

            pane1.setVisible(true);

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();

        });
        pane1.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
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


    public void VaccinationOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/VaccinationForm.fxml");
        Parent load = FXMLLoader.load(resource);
        ControllerDashBoardContext.getChildren().clear();
        ControllerDashBoardContext.getChildren().add(load);
    }

    public void AntigensOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AntigenForm.fxml");
        Parent load = FXMLLoader.load(resource);
        ControllerDashBoardContext.getChildren().clear();
        ControllerDashBoardContext.getChildren().add(load);
    }

    public void PcrOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PcrForm.fxml");
        Parent load = FXMLLoader.load(resource);
        ControllerDashBoardContext.getChildren().clear();
        ControllerDashBoardContext.getChildren().add(load);
    }

    public void MoreOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MoreForm.fxml");
        Parent load = FXMLLoader.load(resource);
        ControllerDashBoardContext.getChildren().clear();
        ControllerDashBoardContext.getChildren().add(load);

    }

    public void homeOnAction(ActionEvent actionEvent) {
        ControllerDashBoardContext.getChildren();
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/PlaceOrderForm.fxml");
        Parent load = null;
        load = FXMLLoader.load(resource);
        ControllerDashBoardContext.getChildren().clear();
        ControllerDashBoardContext.getChildren().add(load);
    }
}


