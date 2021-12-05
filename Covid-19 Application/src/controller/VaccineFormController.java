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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import model.VaccineTubes;
import view.tm.VaccineTubesTm;

import java.net.URL;
import java.sql.SQLException;;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class VaccineFormController implements Initializable {
    public AnchorPane vaccineContext;
    public TableView<VaccineTubesTm> tblVaccine;
    public TableColumn colCode;
    public TableColumn colVaccineName;
    public TableColumn colDescription;
    public TableColumn colDate;
    public TableColumn colQtyOnHand;
    public JFXTextField txtSearch;
    public TextField txtVaccineId;
    public TextField txtDescription;
    public TextField txtVaccineName;
    public DatePicker txtDate;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public TextField txtQtyOnHand;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colVaccineName.setCellValueFactory(new PropertyValueFactory<>("vaccineName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        tblVaccine.getColumns().setAll(colCode,colVaccineName,colDescription,colDate,colQtyOnHand);
       tblVaccine.setItems(loadTableData());

        btnSave.setDisable(true);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);

            }
        });

    }
    private VaccineTubesTm vaccineTube;
    public void onTableDataSelect(MouseEvent mouseEvent) {
        vaccineTube=tblVaccine.getSelectionModel().getSelectedItem();

        if(vaccineTube != null){
            txtVaccineId.setText(vaccineTube.getCode());
            txtVaccineName.setText(vaccineTube.getVaccineName());
            txtDescription.setText(vaccineTube.getDescription());
            txtDate.setValue(vaccineTube.getDate());
            txtQtyOnHand.setText(String.valueOf(Integer.parseInt(String.valueOf(vaccineTube.getQtyOnHand()))));

        }

    }

    public ObservableList<VaccineTubesTm>loadTableData(){
        try {
            List<VaccineTubes> allVaccine=VaccineController.getVaccine();
            ObservableList<VaccineTubesTm> tableData = FXCollections.observableArrayList();
            for (VaccineTubes vaccineTubes:allVaccine
            ) {
                tableData.add(new VaccineTubesTm(
                        vaccineTubes.getCode(),
                        vaccineTubes.getVaccineName(),
                        vaccineTubes.getDescription(),
                        vaccineTubes.getDate(),
                        vaccineTubes.getQtyOnHand()
                ));
            }
            return tableData;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    //------set table data to textField------------------
    private VaccineTubesTm vaccineTubes;
    public void onTableSelect(MouseEvent mouseEvent) {
        vaccineTubes=tblVaccine.getSelectionModel().getSelectedItem();

        if(vaccineTubes!=null){
            txtVaccineId.setText(vaccineTubes.getCode());
            txtVaccineName.setText(vaccineTubes.getVaccineName());
            txtDescription.setText(vaccineTubes.getDescription());
            txtDate.setValue(vaccineTubes.getDate());
            txtQtyOnHand.setText(String.valueOf(Integer.parseInt(String.valueOf(vaccineTubes.getQtyOnHand()))));

        }
    }
    public void clearFields(){
        txtVaccineId.clear();
        txtVaccineName.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
    }

    public void updateOnAction(ActionEvent actionEvent) {
        vaccineTubes.setCode(txtVaccineId.getText());
        vaccineTubes.setVaccineName(txtVaccineName.getText());
        vaccineTubes.setDescription(txtDescription.getText());
        vaccineTubes.setDate(txtDate.getValue());
        vaccineTubes.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));


        try {
            if(VaccineController.updateVaccine(vaccineTubes)){
                new Alert(Alert.AlertType.INFORMATION,"Vaccine details have been updated ").show();
                tblVaccine.setItems(loadTableData());
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update vaccine details were failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            String code = tblVaccine.getSelectionModel().getSelectedItem().getCode();
            boolean isDeleted = VaccineController.deleteVaccine(code);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Vaccine has been deleted ").show();
                tblVaccine.setItems(loadTableData());
            } else {
                new Alert(Alert.AlertType.WARNING, "Delete vaccine was failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING, "Try again").show();
        }
    }


    public void saveVaccineOnAction(ActionEvent event) {
        try {
            boolean isSaved = VaccineController.saveVaccineTubes(new VaccineTubes(
                    txtVaccineId.getText(),
                    txtVaccineName.getText(),
                    txtDescription.getText(),
                    txtDate.getValue(),
                    Integer.parseInt(txtQtyOnHand.getText())
            ));
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Vaccine has been saved").show();
                tblVaccine.setItems(loadTableData());
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Save vaccine was failed").show();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    public void vaccineSearchOnAction(ActionEvent event) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {
            List<VaccineTubes> vaccineTubes= VaccineController.searchVaccine(value);
            ObservableList<VaccineTubesTm> vaccineData = FXCollections.observableArrayList();
            for (VaccineTubes vaccine :vaccineTubes) {
                vaccineData.add(new VaccineTubesTm(
                        vaccine.getCode(),
                        vaccine.getVaccineName(),
                        vaccine.getDescription(),
                        vaccine.getDate(),
                        vaccine.getQtyOnHand()
                ));
            }

            tblVaccine.getItems().setAll(vaccineData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void vaccineIdKeyReleased(KeyEvent keyEvent) {

        String regex="^(Sp|Md|Sv|Az|Pz)[0-9]{4}$";
        String typedText=txtVaccineId.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();
            if(matches){
                txtVaccineId.setStyle("-fx-text-fill: green");
            }else {
                txtVaccineId.setStyle("-fx-text-fill: red");
        }

    }


    public void DescriptionKeyReleased(KeyEvent keyEvent) {
        String regex="^[A-z]{2,15}$";
        String typedText=txtDescription.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtDescription.setStyle("-fx-text-fill: green");
        }else {
            txtDescription.setStyle("-fx-text-fill: red");
        }
    }

    public void vaccineNameKeyReleased(KeyEvent keyEvent) {

        String regex="^(Pfizer|Moderna|AstraZeneca|Sputinik V|Sinopharm)$";
        String typedText=txtVaccineName.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtVaccineName.setStyle("-fx-text-fill: green");
        }else {
           txtVaccineName.setStyle("-fx-text-fill: red");
        }

    }

    public void qtyOnHandKeyReleased(KeyEvent keyEvent) {
        String regex="^[0-9]{2,5}$";
        String typedText=txtQtyOnHand.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtQtyOnHand.setStyle("-fx-text-fill: green");
            btnSave.setDisable(false);
        }else {
            txtQtyOnHand.setStyle("-fx-text-fill: red");
        }

    }
}
