package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.VaccinePatient;
import model.VaccineTubes;
import view.tm.VaccinationPatientTm;
import view.tm.VaccineTubesTm;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class VaccineController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    //---------------------------------------VaccinePatients------------------------------------------------------
    public static boolean saveVaccinePatient(VaccinePatient vaccinePatient) throws SQLException, ClassNotFoundException {

        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("INSERT INTO vaccinepatient(nic,name,address,nameOfVaccine) VALUES (?,?,?,?)");
        stm.setObject(1,vaccinePatient.getNic());
        stm.setObject(2,vaccinePatient.getName());
        stm.setObject(3,vaccinePatient.getAddress());
        stm.setObject(4,vaccinePatient.getNameOfVaccine());


        return stm.executeUpdate()>0;
    }
    public static ArrayList<VaccinePatient> getVaccinePatient() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM vaccinepatient");
        ResultSet rst = stm.executeQuery();
        ObservableList<VaccinationPatientTm> obList= FXCollections.observableArrayList();
        List<VaccinePatient> vaccinePatients=new ArrayList<>();

        while (rst.next()){
            vaccinePatients.add(new VaccinePatient(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)

                    ));
        }
        return (ArrayList<VaccinePatient>) vaccinePatients;
    }
    public static boolean deletePatient(String name) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("DELETE From vaccinepatient WHERE name=?");
        stm.setObject(1,name);
        return stm.executeUpdate()>0;
    }

    public static boolean updateVaccinePatient(VaccinationPatientTm vaccinePatient) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE vaccinepatient SET name =?, address=?, nameOfvaccine=? WHERE nic=?");
        stm.setObject(1,vaccinePatient.getName());
        stm.setObject(2,vaccinePatient.getAddress());
        stm.setObject(3,vaccinePatient.getNameOfVaccine());
        stm.setObject(4,vaccinePatient.getNic());

        return stm.executeUpdate()>0;
    }

    public static List<VaccinePatient> searchPatient(String value) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM vaccinepatient WHERE name LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        List<VaccinePatient> vaccinePatients=new ArrayList<>();

        while (rst.next()){
            vaccinePatients.add(new VaccinePatient(
                    rst.getString("nic"),
                    rst.getString("name"),
                    rst.getString("address"),
                    rst.getString("nameOfVaccine")

            ));

        }
        return vaccinePatients;

    }




    //---------------------------------------------VaccineTubes------------------------------------------------
    public static boolean saveVaccineTubes(VaccineTubes vaccineTubes) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("INSERT INTO vaccine(code,vaccine_name,description,date,qtyOnHand) VALUES (?,?,?,?,?)");
        stm.setObject(1,vaccineTubes.getCode());
        stm.setObject(2,vaccineTubes.getVaccineName());
        stm.setObject(3,vaccineTubes.getDescription());
        stm.setObject(4,vaccineTubes.getDate());
        stm.setObject(5,vaccineTubes.getQtyOnHand());

        return stm.executeUpdate()>0;
    }
    public static ArrayList<VaccineTubes> getVaccine() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM vaccine");
        ResultSet rst = stm.executeQuery();
        ObservableList<VaccineTubesTm> obList= FXCollections.observableArrayList();
        List<VaccineTubes> vaccines=new ArrayList<>();

        while (rst.next()){
            vaccines.add(new VaccineTubes(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    LocalDate.parse(rst.getString(4)),
                    Integer.parseInt(rst.getString(5))

            ));
        }
        return (ArrayList<VaccineTubes>) vaccines;
    }
    public static boolean deleteVaccine(String code) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("DELETE From vaccine WHERE code=?");
        stm.setObject(1,code);
        return stm.executeUpdate()>0;
    }



    public static List<VaccineTubes> searchVaccine(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM vaccine WHERE  vaccine_name LIKE '%" + value + "%'");
        ResultSet rst = stm.executeQuery();
        List<VaccineTubes> VaccineTube = new ArrayList<>();

        while (rst.next()) {
            VaccineTube.add(new VaccineTubes(
                    rst.getString("code"),
                    rst.getString("vaccine_Name"),
                    rst.getString("description"),
                    LocalDate.parse(rst.getString("date")),
                    Integer.parseInt(rst.getString("qtyOnHand"))
            ));
        }
        return VaccineTube;
    }
    public static boolean updateVaccine(VaccineTubesTm vaccineTube) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE vaccine SET vaccine_name =?, description=?, date=?, qtyOnHand=? WHERE code=?");
        stm.setObject(1,vaccineTube.getVaccineName());
        stm.setObject(2,vaccineTube.getDescription());
        stm.setObject(3,vaccineTube.getDate());
        stm.setObject(4,vaccineTube.getQtyOnHand());
        stm.setObject(5,vaccineTube.getCode());

        return stm.executeUpdate()>0;
    }

}

