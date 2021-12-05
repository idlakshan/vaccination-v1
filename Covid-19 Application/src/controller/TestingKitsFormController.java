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
import model.ToolKit;
import model.VaccinePatient;
import model.VaccineTubes;
import view.tm.ToolKitTm;
import view.tm.VaccinationPatientTm;
import view.tm.VaccineTubesTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class TestingKitsFormController implements Initializable {
    public AnchorPane toolKitContext;
    public TableView<ToolKitTm> tblKits;
    public TableColumn colToolKitID;
    public TableColumn colToolKitName;
    public TableColumn colDate;
    public TableColumn colPrice;
    public TableColumn colQtyOnHand;
    public JFXTextField txtSearch;
    public DatePicker txtDate;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public TextField txtQtyOnHand;
    public TextField txtPrice;
    public TextField txtKitId;
    public TextField txtKitName;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colToolKitID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colToolKitName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

       tblKits.getColumns().setAll(colToolKitID,colToolKitName,colDate,colPrice,colQtyOnHand);
        tblKits.setItems(loadTableData());
        btnSave.setDisable(true);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });
    }
    public void clearFields(){
        txtKitId.clear();
        txtKitName.clear();
        txtPrice.clear();
        txtQtyOnHand.clear();
    }
    public ObservableList<ToolKitTm> loadTableData(){
        try {
            List<ToolKit> toolKits=TestingKitController.getToolKit();
            ObservableList<ToolKitTm> tableData= FXCollections.observableArrayList();
            for (ToolKit toolKit:toolKits) {
                tableData.add(new ToolKitTm(
                        toolKit.getId(),
                        toolKit.getName(),
                        toolKit.getDate(),
                        toolKit.getPrice(),
                        toolKit.getQtyOnHand()
                ));
            }
            return tableData;

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    private ToolKitTm toolKit;
    public void onTableDataSelect(MouseEvent mouseEvent) {
        toolKit=tblKits.getSelectionModel().getSelectedItem();

        if(toolKit!=null){
            txtKitId.setText(toolKit.getId());
            txtKitName.setText(toolKit.getName());
            txtDate.setValue(toolKit.getDate());
            txtPrice.setText(String.valueOf(Double.parseDouble(String.valueOf(toolKit.getPrice()))));
            txtQtyOnHand.setText(String.valueOf(Integer.parseInt(String.valueOf(toolKit.getQtyOnHand()))));
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        toolKit.setId(txtKitId.getText());
        toolKit.setName(txtKitName.getText());
        toolKit.setDate(txtDate.getValue());
        toolKit.setPrice(Double.parseDouble(txtPrice.getText()));
        toolKit.setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));

        try {
            if(TestingKitController.updateToolKit(toolKit)){
                new Alert(Alert.AlertType.INFORMATION,"ToolKit has been updated ").show();
               tblKits.setItems(loadTableData());
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Update ToolKits was failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            String code = tblKits.getSelectionModel().getSelectedItem().getId();
            boolean isDeleted = TestingKitController.deleteToolKit(code);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "ToolKit has been deleted ").show();
                tblKits.setItems(loadTableData());
            } else {
                new Alert(Alert.AlertType.WARNING, "Delete ToolKit was failed").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            new Alert(Alert.AlertType.WARNING, "Try again").show();
        }
    }


    public void saveOnAction(ActionEvent event) {
        try {
            boolean save = TestingKitController.saveToolKit(new ToolKit(
                    txtKitId.getText(),
                    txtKitName.getText(),
                    txtDate.getValue(),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtQtyOnHand.getText())
            ));
            if(save){
                new Alert(Alert.AlertType.INFORMATION,"ToolKit has been saved").show();
                tblKits.setItems(loadTableData());
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Save ToolKit was failed").show();
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
            List<ToolKit> toolKits= TestingKitController.searchToolKit(value);
            ObservableList<ToolKitTm> toolData = FXCollections.observableArrayList();
            for (ToolKit toolKit :toolKits) {
                toolData.add(new ToolKitTm(
                        toolKit.getId(),
                        toolKit.getName(),
                        toolKit.getDate(),
                        toolKit.getPrice(),
                        toolKit.getQtyOnHand()
                ));
            }

            tblKits.getItems().setAll(toolData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void kitIdKeyReleased(KeyEvent keyEvent) {
        String regex="^(pcr|Pcr|anti|Anti)[0-9]{3}$";
        String typedText=txtKitId.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtKitId.setStyle("-fx-text-fill: green");
        }else {
            txtKitId.setStyle("-fx-text-fill: red");
        }
    }

    public void priceReleasedKey(KeyEvent keyEvent) {
        String regex="^[0-9]{3,4}$";
        String typedText=txtPrice.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtPrice.setStyle("-fx-text-fill: green");
        }else {
            txtPrice.setStyle("-fx-text-fill: red");
        }
    }

    public void kitNameReleaseKey(KeyEvent keyEvent) {
        String regex="^(pcr|Pcr|antigen|Antigen)$";
        String typedText=txtKitName.getText();

        Pattern compile=Pattern.compile(regex);
        boolean matches=compile.matcher(typedText).matches();

        if(matches){
            txtKitName.setStyle("-fx-text-fill: green");
        }else {
            txtKitName.setStyle("-fx-text-fill: red");
        }

    }

    public void qtyOnHandReleasedKey(KeyEvent keyEvent) {
        String regex="^[0-9]{2,6}$";
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
