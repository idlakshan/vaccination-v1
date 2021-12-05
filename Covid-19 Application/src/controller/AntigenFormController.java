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
import view.tm.AntigenPatientTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AntigenFormController implements Initializable {
    public AnchorPane antigenPatientContext;
    public TableView<AntigenPatientTm> tblAntigenPatient;
    public TableColumn colNic;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colDate;
    public TableColumn colResult;
    public JFXTextField txtSearch;
    public TextField txtNic;
    public TextField txtAddress;
    public TextField txtName;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public DatePicker txtDate;
    public ComboBox<String> cmbResult;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbResult.getItems().addAll("Positive", "Negative");

        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));

        tblAntigenPatient.getColumns().setAll(colNic,colName,colAddress,colDate,colResult);
        tblAntigenPatient.setItems(loadTableData());

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
        cmbResult.getItems().clear();
    }

    public ObservableList<AntigenPatientTm> loadTableData(){
        try {
            List<AntigenPatient> allAntigenPatient= TestingPatientController.getAntigenPatient();
            ObservableList<AntigenPatientTm> tableData = FXCollections.observableArrayList();
            for (AntigenPatient antigenPatient:allAntigenPatient
            ) {
                tableData.add(new AntigenPatientTm(
                        antigenPatient.getNic(),
                        antigenPatient.getName(),
                        antigenPatient.getAddress(),
                        antigenPatient.getDate(),
                        antigenPatient.getResult()
                ));
            }
            return tableData;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    private AntigenPatientTm antigenPatient;
    public void onTableDataSelect(MouseEvent mouseEvent) {
        antigenPatient= (AntigenPatientTm) tblAntigenPatient.getSelectionModel().getSelectedItem();

        if(antigenPatient != null){
            txtNic.setText(antigenPatient.getNic());
            txtName.setText(antigenPatient.getName());
            txtAddress.setText(antigenPatient.getAddress());
            //txtDate.setValue(antigenPatient.getDate());
            //cmbResult.setValue(antigenPatient.getResult());
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        antigenPatient.setNic(txtNic.getText());
        antigenPatient.setName(txtName.getText());
        antigenPatient.setAddress(txtAddress.getText());
        //antigenPatient.setDate(txtDate.getValue());
        //antigenPatient.setResult(cmbResult.getValue());


        try {
            if (TestingPatientController.updateAntigenPatient(antigenPatient)){
                new Alert(Alert.AlertType.INFORMATION,"Patient has been updated ").show();
                tblAntigenPatient.setItems(loadTableData());
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
            String nic = tblAntigenPatient.getSelectionModel().getSelectedItem().getNic();
            boolean isDeleted = TestingPatientController.deleteAntigenPatient(nic);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Patient has been deleted").show();
                tblAntigenPatient.setItems(loadTableData());
            } else {
                new Alert(Alert.AlertType.WARNING, "Delete patient was failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING, "Try again").show();
        }
    }

    public void savePatientOnAction(ActionEvent actionEvent) {
        try {
            boolean save = TestingPatientController.saveAntigenPatient(new AntigenPatient(
                    txtNic.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtDate.getValue(),
                    cmbResult.getValue()

            ));
            if(save){
                new Alert(Alert.AlertType.INFORMATION,"Patient has been saved").show();
               tblAntigenPatient.setItems(loadTableData());
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Save patient was failed").show();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }
    public void resultOnAction(ActionEvent actionEvent){

    }

    public void searchOnAction(ActionEvent event) {
        search(txtSearch.getText());
    }
    private void search(String value) {
        try {
            List<AntigenPatient> antigenPatients= TestingPatientController.searchPatient(value);
            ObservableList<AntigenPatientTm> patientData = FXCollections.observableArrayList();
            for (AntigenPatient antigenPatient : antigenPatients) {
                patientData.add(new AntigenPatientTm(
                        antigenPatient.getNic(),
                        antigenPatient.getName(),
                        antigenPatient.getAddress(),
                        antigenPatient.getDate(),
                        antigenPatient.getResult()

                ));
            }

           tblAntigenPatient.getItems().setAll(patientData);


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
            txtAddress.setStyle("-fx-text-fill: red");
        }

    }
}
