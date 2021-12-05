package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.AntigenPatient;
import model.PcrPatient;
import view.tm.AntigenPatientTm;
import view.tm.PcrPatientTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class    PcrFormController implements Initializable {
    public AnchorPane pcrPatientContext;
    public TableView<PcrPatientTm> tblPcrPatient;
    public TableColumn colNic;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colDate;
    public TableColumn colPhoneNumber;
    public JFXTextField txtSearch;
    public TextField txtNic;
    public TextField txtAddress;
    public TextField txtName;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public TextField txtPhoneNumber;
    public DatePicker txtDate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));

        tblPcrPatient.getColumns().setAll(colNic,colName,colAddress,colDate,colPhoneNumber);
        tblPcrPatient.setItems(loadTableData());

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);

            }
        });


    }
    public void clearFields(){
        txtNic.clear();
        txtName.clear();
        txtAddress.clear();
        txtPhoneNumber.clear();
    }
    private PcrPatientTm pcrPatient;
    public void onTableDataSelect(MouseEvent mouseEvent) {
        pcrPatient= (PcrPatientTm) tblPcrPatient.getSelectionModel().getSelectedItem();

        if(pcrPatient != null){
            txtNic.setText(pcrPatient.getNic());
            txtName.setText(pcrPatient.getName());
            txtAddress.setText(pcrPatient.getAddress());
            txtDate.setValue(pcrPatient.getDate());
            txtPhoneNumber.setText(pcrPatient.getPhone());
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        pcrPatient.setNic(txtNic.getText());
        pcrPatient.setName(txtName.getText());
        pcrPatient.setAddress(txtAddress.getText());
        pcrPatient.setDate(txtDate.getValue());
        pcrPatient.setPhone(txtPhoneNumber.getText());


        try {
            if (TestingPatientController.updatePcrPatient(pcrPatient)){
                new Alert(Alert.AlertType.INFORMATION,"Patient has been updated ").show();
                tblPcrPatient.setItems(loadTableData());
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update patient was failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING,"Update patient was failed").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            String nic = tblPcrPatient.getSelectionModel().getSelectedItem().getNic();
            boolean isDeleted = TestingPatientController.deletePcrPatient(nic);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Patient has been deleted").show();
                tblPcrPatient.setItems(loadTableData());
            } else {
                new Alert(Alert.AlertType.WARNING, "Delete patient was failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING, "Try again").show();
        }
    }


    public ObservableList<PcrPatientTm> loadTableData(){
        try {
            List<PcrPatient> allPcrPatient= TestingPatientController.getPcrPatient();
            ObservableList<PcrPatientTm> tableData = FXCollections.observableArrayList();
            for (PcrPatient pcrPatient:allPcrPatient
            ) {
                tableData.add(new PcrPatientTm(
                        pcrPatient.getNic(),
                        pcrPatient.getName(),
                        pcrPatient.getAddress(),
                        pcrPatient.getDate(),
                        pcrPatient.getPhone()
                ));
            }
            return tableData;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void savePatientOnAction(ActionEvent actionEvent) {
        try {
            boolean isSaved = TestingPatientController.savePcrPatient(new PcrPatient(
                    txtNic.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtDate.getValue(),
                   txtPhoneNumber.getText()

            ));
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Patient has been saved").show();
                tblPcrPatient.setItems(loadTableData());
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Save patient was failed").show();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    public void searchOnAction(ActionEvent event) {
        search(txtSearch.getText());

    }
    private void search(String value) {
        try {
            List<PcrPatient> pcrPatients= TestingPatientController.searchPcrPatient(value);
            ObservableList<PcrPatientTm> patientData = FXCollections.observableArrayList();
            for (PcrPatient pcrPatient : pcrPatients) {
                patientData.add(new PcrPatientTm(
                        pcrPatient.getNic(),
                        pcrPatient.getName(),
                        pcrPatient.getAddress(),
                        pcrPatient.getDate(),
                        pcrPatient.getPhone()

                ));
            }

            tblPcrPatient.getItems().setAll(patientData);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void nicKeyReleased(KeyEvent keyEvent) {
        String regex="^[0-9]{9,12}v?$";
        String typedText=txtNic.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtNic.setStyle("-fx-text-fill: green");
        }else {
            txtNic.setStyle("-fx-text-fill: red");
        }

    }

    public void addressKeyReleased(KeyEvent keyEvent) {
        String regex="^[A-z]{3,12}$";
        String typedText=txtAddress.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtAddress.setStyle("-fx-text-fill: green");
        }else {
            txtAddress.setStyle("-fx-text-fill: red");
        }
    }


    public void nameKeyReleased(KeyEvent keyEvent) {
        String regex="^[A-z]{3,12}$";
        String typedText=txtName.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtName.setStyle("-fx-text-fill: green");
        }else {
            txtName.setStyle("-fx-text-fill: red");
        }

    }

    public void phoneKeyReleased(KeyEvent keyEvent) {
        String regex="^(077|071|078|075)[-]?[0-9]{7}$";
        String typedText=txtPhoneNumber.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtPhoneNumber.setStyle("-fx-text-fill: green");
        }else {
            txtPhoneNumber.setStyle("-fx-text-fill: red");
        }
    }
}
