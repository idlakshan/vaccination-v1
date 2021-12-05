package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.ToolKit;
import view.tm.ToolKitTm;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestingKitController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public static boolean saveToolKit(ToolKit toolKit) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("INSERT INTO testingkits(toolkitId,toolkitName,date,price,qtyOnhand) VALUES (?,?,?,?,?)");
        stm.setObject(1,toolKit.getId());
        stm.setObject(2,toolKit.getName());
        stm.setObject(3,toolKit.getDate());
        stm.setObject(4,toolKit.getPrice());
        stm.setObject(5,toolKit.getQtyOnHand());

        return stm.executeUpdate()>0;
    }
    public static ArrayList<ToolKit> toolKits() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM testingkits");
        ResultSet rst = stm.executeQuery();
        ObservableList<ToolKitTm> obList= FXCollections.observableArrayList();
        List<ToolKit> toolKit=new ArrayList<>();

        while (rst.next()){
            toolKit.add(new ToolKit(
                    rst.getString(1),
                    rst.getString(2),
                    LocalDate.parse(rst.getString(3)),
                    Double.parseDouble(rst.getString(4)),
                    Integer.parseInt(rst.getString(5))

            ));
        }
        return (ArrayList<ToolKit>) toolKit;
    }
    public static boolean deleteToolKit(String id) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("DELETE From testingkits WHERE toolkitId=?");
        stm.setObject(1,id);

        return stm.executeUpdate()>0;
    }
    public static boolean updateToolKit(ToolKitTm toolKit) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE testingkits SET toolkitName =?, date=?, price=?, qtyOnhand=? WHERE toolkitId=?");
        stm.setObject(1,toolKit.getName());
        stm.setObject(2,toolKit.getDate());
        stm.setObject(3,toolKit.getPrice());
        stm.setObject(4,toolKit.getQtyOnHand());
        stm.setObject(5,toolKit.getId());

        return stm.executeUpdate()>0;
    }
    public static List<ToolKit> searchToolKit(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM testingkits WHERE  toolkitName LIKE '%" + value + "%'");
        ResultSet rst = stm.executeQuery();
        List<ToolKit> toolKits = new ArrayList<>();

        while (rst.next()) {
            toolKits.add(new ToolKit(
                    rst.getString("toolKitId"),
                    rst.getString("toolKitName"),
                    LocalDate.parse(rst.getString("date")),
                    Double.parseDouble(rst.getString("price")),
                    Integer.parseInt(rst.getString("qtyOnHand"))
            ));
        }
        return toolKits;
    }
    public static ArrayList<ToolKit> getToolKit() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM testingkits");
            ResultSet rst = stm.executeQuery();
        ObservableList<ToolKitTm> obList= FXCollections.observableArrayList();
        List<ToolKit> toolKits=new ArrayList<>();
            while (rst.next()){
            toolKits.add(new ToolKit(
                    rst.getString(1),
                    rst.getString(2),
                    LocalDate.parse(rst.getString(3)),
                    Double.parseDouble(rst.getString(4)),
                    Integer.parseInt(rst.getString(5))
            ));
        }
        return (ArrayList<ToolKit>) toolKits;
    }
}
