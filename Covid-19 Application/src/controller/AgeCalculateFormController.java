package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.net.URL;
import java.time.LocalDate;
import java.time.Period;

import java.util.ResourceBundle;


public class AgeCalculateFormController implements Initializable {
    public TextField txtDate;
    public TextField txtMonth;
    public TextField txtYear;
    public JFXButton btnGetYear;
    public Label lblYear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void getYearOnAction(ActionEvent event) {
      ageCalculate();


    }
    public void ageCalculate(){
        int date=Integer.parseInt(txtDate.getText());
        int month=Integer.parseInt(txtMonth.getText());
        int year=Integer.parseInt(txtYear.getText());


        LocalDate birthday=LocalDate.of(year,month,date);
        LocalDate nowDate=LocalDate.now();

        int period= Period.between(birthday,nowDate).getYears();
        if (period>=12){
            lblYear.setText(String.valueOf(period));
            new Alert(Alert.AlertType.INFORMATION,"He/She is eligible for get vaccine").show();
        }else {
            lblYear.setText(String.valueOf(period));
            new Alert(Alert.AlertType.ERROR,"He/She is not eligible for get vaccine").show();
        }
    }
}
