package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.VaccinePatient;
import view.tm.VaccinationPatientTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class VaccinationFormController implements Initializable {
    public AnchorPane VaccinatePaneContext;


    public TableView<VaccinationPatientTm> tblVaccinePatient;
    public TableColumn colNic;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colNameVaccine;
    public TableColumn colDose1;
    public TableColumn colDose2;
    public TableColumn colDose3;
    public TextField txtNic;
    public TextField txtAddress;
    public TextField txtName;
    public ComboBox<String> cmbVaccineName;
    public JFXButton btnNewRegistration;
    public JFXTextField txtSearch;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnSave;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbVaccineName.getItems().addAll("AstraZeneca", "Pfizer", "Moderna", "Sinopharm", "Sputnik V");


        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNameVaccine.setCellValueFactory(new PropertyValueFactory<>("nameOfVaccine"));
        colDose1.setCellValueFactory(new PropertyValueFactory<>("checkBox1"));
        colDose2.setCellValueFactory(new PropertyValueFactory<>("checkBox2"));
        colDose3.setCellValueFactory(new PropertyValueFactory<>("checkBox3"));

        tblVaccinePatient.getColumns().setAll(colNic,colName,colAddress,colNameVaccine,colDose1,colDose2,colDose3);
        tblVaccinePatient.setItems(loadTableData());



        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);

            }
        });
    }

    public ObservableList<VaccinationPatientTm> loadTableData(){
        try {
            List<VaccinePatient> vaccinePatients=VaccineController.getVaccinePatient();
            ObservableList<VaccinationPatientTm> tableData=FXCollections.observableArrayList();
            for (VaccinePatient vaccinePatient:vaccinePatients) {
                CheckBox checkBox1=new CheckBox();
                CheckBox checkBox2=new CheckBox();
                CheckBox checkBox3=new CheckBox();


                tableData.add(new VaccinationPatientTm(
                        vaccinePatient.getNic(),
                        vaccinePatient.getName(),
                        vaccinePatient.getAddress(),
                        vaccinePatient.getNameOfVaccine(),
                        checkBox1,
                        checkBox2,
                        checkBox3
                ));

            }
            return tableData;

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void clearFields(){
        txtNic.clear();
        txtName.clear();
        txtAddress.clear();
        cmbVaccineName.getItems().clear();
    }

    public void newRegistrationOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/AgeCalculateForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private VaccinationPatientTm vaccinePatient;
    public void onTableDataSelect(MouseEvent mouseEvent) {
        vaccinePatient=tblVaccinePatient.getSelectionModel().getSelectedItem();

        if(vaccinePatient != null){
            txtNic.setText(vaccinePatient.getNic());
            txtName.setText(vaccinePatient.getName());
            txtAddress.setText(vaccinePatient.getAddress());
            cmbVaccineName.setValue(vaccinePatient.getNameOfVaccine());
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {

        vaccinePatient.setNic(txtNic.getText());
        vaccinePatient.setName(txtName.getText());
        vaccinePatient.setAddress(txtAddress.getText());
        vaccinePatient.setNameOfVaccine(cmbVaccineName.getValue());


        try {
            if (VaccineController.updateVaccinePatient(vaccinePatient)){
                System.out.println("yes");
                new Alert(Alert.AlertType.INFORMATION,"Patient has been updated ").show();
                tblVaccinePatient.setItems(loadTableData());
                  clearFields();
            }else {
                System.out.println("noo");
                new Alert(Alert.AlertType.WARNING,"Update patient was failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING,"Update patient was failed").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            String username = tblVaccinePatient.getSelectionModel().getSelectedItem().getName();
            boolean isDeleted = VaccineController.deletePatient(username);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Patient has been deleted").show();
                tblVaccinePatient.setItems(loadTableData());
            } else {
                new Alert(Alert.AlertType.WARNING, "Delete patient was failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING, "Try again").show();
        }
    }

    public void savePatientOnAction(ActionEvent actionEvent) {
        try {
            boolean save = VaccineController.saveVaccinePatient(new VaccinePatient(

                    txtNic.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    cmbVaccineName.getValue()

                    ));
            if(save){
                new Alert(Alert.AlertType.INFORMATION,"Patient has been saved").show();
                tblVaccinePatient.setItems(loadTableData());
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Save patient was failed").show();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    public void searchPatientOnAction(ActionEvent event) {
        search(txtSearch.getText());
    }
    private void search(String value) {
        try {
            List<VaccinePatient> vaccinePatients = VaccineController.searchPatient(value);
            ObservableList<VaccinationPatientTm> patientData = FXCollections.observableArrayList();
            for (VaccinePatient vaccinePatient : vaccinePatients) {
                patientData.add(new VaccinationPatientTm(
                        vaccinePatient.getNic(),
                        vaccinePatient.getName(),
                        vaccinePatient.getAddress(),
                        vaccinePatient.getNameOfVaccine(),
                        vaccinePatient.getCheckBox1(),
                        vaccinePatient.getCheckBox2(),
                        vaccinePatient.getCheckBox3()
                ));
            }

            tblVaccinePatient.getItems().setAll(patientData);

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
}
