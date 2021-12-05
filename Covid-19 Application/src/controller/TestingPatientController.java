package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.AntigenPatient;
import model.PcrPatient;
import view.tm.AntigenPatientTm;
import view.tm.PcrPatientTm;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestingPatientController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //--------------------------------------Antigen Patients--------------------------------------------------------------------

    public static boolean saveAntigenPatient(AntigenPatient antigenPatient) throws SQLException, ClassNotFoundException {

        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("INSERT INTO antigenPatient(nic,name,address,date,result) VALUES (?,?,?,?,?)");
        stm.setObject(1,antigenPatient.getNic());
        stm.setObject(2,antigenPatient.getName());
        stm.setObject(3,antigenPatient.getAddress());
        stm.setObject(4,antigenPatient.getDate());
        stm.setObject(5,antigenPatient.getResult());


        return stm.executeUpdate()>0;
    }
    public static ArrayList<AntigenPatient> getAntigenPatient() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM antigenPatient");
        ResultSet rst = stm.executeQuery();
        ObservableList<AntigenPatientTm> obList= FXCollections.observableArrayList();
        List<AntigenPatient> antigenPatients=new ArrayList<>();

        while (rst.next()){
            antigenPatients.add(new AntigenPatient(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                   LocalDate.parse(rst.getString(4)),
                    rst.getString(5)

            ));
        }
        return (ArrayList<AntigenPatient>) antigenPatients;
    }
    public static boolean updateAntigenPatient(AntigenPatientTm antigenPatient) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE antigenPatient SET name =?, address=?, date=?, result=? WHERE nic=?");
        stm.setObject(1,antigenPatient.getName());
        stm.setObject(2,antigenPatient.getAddress());
        stm.setObject(3,antigenPatient.getDate());
        stm.setObject(4,antigenPatient.getResult());
        stm.setObject(5,antigenPatient.getNic());

        return stm.executeUpdate()>0;
    }
    public static boolean deleteAntigenPatient(String nic) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("DELETE From antigenPatient WHERE nic=?");
        stm.setObject(1,nic);

        return stm.executeUpdate()>0;
    }
    public static List<AntigenPatient> searchPatient(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM antigenPatient WHERE  name LIKE '%" + value + "%'");
        ResultSet rst = stm.executeQuery();
        List<AntigenPatient> antigenPatients = new ArrayList<>();

        while (rst.next()) {
            antigenPatients.add(new AntigenPatient(
                    rst.getString("nic"),
                    rst.getString("name"),
                    rst.getString("address"),
                    LocalDate.parse(rst.getString("date")),
                    rst.getString("result")
            ));
        }
        return antigenPatients;
    }

    //-----------------------------------------------Pcr Patients---------------------------------------------------------------

    public static boolean savePcrPatient(PcrPatient pcrPatient) throws SQLException, ClassNotFoundException {

        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("INSERT INTO pcrPatient(nic,name,address,date,phone) VALUES (?,?,?,?,?)");
        stm.setObject(1,pcrPatient.getNic());
        stm.setObject(2,pcrPatient.getName());
        stm.setObject(3,pcrPatient.getAddress());
        stm.setObject(4,pcrPatient.getDate());
        stm.setObject(5,pcrPatient.getPhone());


        return stm.executeUpdate()>0;
    }

    public static ArrayList<PcrPatient> getPcrPatient() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM pcrPatient");
        ResultSet rst = stm.executeQuery();
        ObservableList<PcrPatientTm> obList= FXCollections.observableArrayList();
        List<PcrPatient> pcrPatient=new ArrayList<>();

        while (rst.next()){
            pcrPatient.add(new PcrPatient(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    LocalDate.parse(rst.getString(4)),
                    rst.getString(5)

            ));
        }
        return (ArrayList<PcrPatient>) pcrPatient;
    }
    public static boolean deletePcrPatient(String nic) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("DELETE From pcrPatient WHERE nic=?");
        stm.setObject(1,nic);

        return stm.executeUpdate()>0;
    }
    public static boolean updatePcrPatient(PcrPatientTm pcrPatient) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE pcrPatient SET name =?, address=?, date=?, phone=? WHERE nic=?");
        stm.setObject(1,pcrPatient.getName());
        stm.setObject(2,pcrPatient.getAddress());
        stm.setObject(3,pcrPatient.getDate());
        stm.setObject(4,pcrPatient.getPhone());
        stm.setObject(5,pcrPatient.getNic());

        return stm.executeUpdate()>0;
    }
    public static List<PcrPatient> searchPcrPatient(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM pcrPatient WHERE  name LIKE '%" + value + "%'");
        ResultSet rst = stm.executeQuery();
        List<PcrPatient> pcrPatients = new ArrayList<>();

        while (rst.next()) {
            pcrPatients.add(new PcrPatient(
                    rst.getString("nic"),
                    rst.getString("name"),
                    rst.getString("address"),
                    LocalDate.parse(rst.getString("date")),
                    rst.getString("phone")
            ));
        }
        return pcrPatients;
    }

}
