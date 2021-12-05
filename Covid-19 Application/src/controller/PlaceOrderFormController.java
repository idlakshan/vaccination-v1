package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.CartTm;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public JFXTextField aQty;
    public JFXTextField price;
    public JFXTextField description;
    public JFXComboBox<String> item;
    public JFXTextField qty;
    public JFXComboBox<String> antigenCus;
    public JFXComboBox<String> pcrCu;
    public JFXTextField name;
    public JFXTextField address;
    public Button addBtn;
    public TableView<CartTm> tblPlaceOrder;
    public TableColumn colId;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colPrice;
    public RadioButton radio;
    public Label lblPrice;
    public CheckBox cheackBox;
    public Button btnSave;
    private boolean isPcrOrAntigen = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadrdAllPcrCustomers();
        loardAlAntigenCustomers();
        lordAllIteams();

        colId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblPlaceOrder.getColumns().setAll(colId, colDescription, colQty, colPrice);
    }

    public void address(ActionEvent event) {
    }

    public void name(ActionEvent event) {
    }

    public void pcrCustomers(ActionEvent event) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM pcrpatient WHERE nic=?");
        stm.setObject(1, pcrCu.getValue());
        this.isPcrOrAntigen = true;
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            name.setText(rst.getString(1));
            address.setText(rst.getString(2));
        }
//
    }

    public void antigenCustomers(ActionEvent event) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM antigenpatient WHERE nic=?");
        stm.setObject(1, antigenCus.getValue());
        this.isPcrOrAntigen = false;
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            name.setText(rst.getString(1));
            address.setText(rst.getString(2));
        }
    }

    public void needQty(ActionEvent event) {
    }

    public void ItemAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM testingkits WHERE toolkitId=?");
        stm.setObject(1, item.getValue());

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            description.setText(rst.getString(1));
            price.setText(rst.getString(4));
            aQty.setText(rst.getString(2));

        }
    }

    public void descriptionAction(ActionEvent event) {
    }

    public void aQtyAction(ActionEvent event) {
    }

    public void priceAction(ActionEvent event) {
    }


    private void lordAllIteams() {
        PreparedStatement stm = null;
        try {
            stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM testingkits");
            ResultSet rst = stm.executeQuery();

            List<String> ids = new ArrayList<>();
            while (rst.next()) {
                ids.add(
                        rst.getString(1)
                );
            }
            item.getItems().addAll(ids);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loardAlAntigenCustomers() {
        PreparedStatement stm = null;
        try {
            stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM antigenpatient");
            ResultSet rst = stm.executeQuery();

            List<String> ids = new ArrayList<>();
            while (rst.next()) {
                ids.add(
                        rst.getString(1)
                );
            }
            antigenCus.getItems().addAll(ids);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadrdAllPcrCustomers() {
        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM pcrpatient");
            ResultSet rst = stm.executeQuery();

            List<String> ids = new ArrayList<>();
            while (rst.next()) {
                ids.add(
                        rst.getString(1)
                );
            }
            pcrCu.getItems().addAll(ids);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void addonAction(ActionEvent event) {

        String text = qty.getText();
        Double count = +Double.parseDouble(price.getText());
        if (text != null) {
            CartTm tm = new CartTm(
                    item.getValue(),
                    description.getText(),
                    Integer.parseInt(text),
                    Double.parseDouble(price.getText()) * Double.parseDouble(text + "")
            );
            obList.add(tm);
            tblPlaceOrder.setItems(obList);

            if (cheackBox.isSelected()) {
                Double discount = 0.0;
                for (CartTm cartTm :
                        obList) {
                    discount += cartTm.getPrice();
                }
                double v1 = ((discount / 100) * 10) - discount;
                lblPrice.setText(v1 + "");
            } else {
                Double d = 0.0;
                for (CartTm cartTm :
                        obList) {
                    d += cartTm.getPrice();
                }
                lblPrice.setText(d + "");
            }
        }
    }
    public void isSelected(ActionEvent event) {
        double text = Double.parseDouble(lblPrice.getText());
        double v1 =  text-((text / 100) * 10) ;
        lblPrice.setText(v1 + "");
    }

}






