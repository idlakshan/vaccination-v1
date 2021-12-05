package controller;

import db.DbConnection;
import javafx.fxml.Initializable;
import model.User;
import view.tm.UsersTm;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public String login(User userLog) throws SQLException, ClassNotFoundException {
        String username=userLog.getUserName();
        String password=userLog.getPassword();

        PreparedStatement stm= DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM userlogin WHERE  username=? AND password=? ");

        stm.setObject(1,username);
        stm.setObject(2,password);

        ResultSet rst = stm.executeQuery();

        if(rst.next()){
            return rst.getString(5);
        }else {
            return "";
        }

    }
    public static boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO userLogin(username,email,password,roles) VALUES (?,?,?,?)");
        stm.setObject(1,user.getUserName());
        stm.setObject(2,user.getEmail());
        stm.setObject(3,user.getPassword());
        stm.setObject(4,user.getRoles());

        return stm.executeUpdate()>0;

    }

    public static List<User> searchUsers(String value) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM userLogin WHERE username LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();
        List<User> users=new ArrayList<>();

        while (rst.next()){
            users.add(new User(
                    rst.getString("username"),
                    rst.getString("email"),
                    rst.getString("password"),
                    rst.getString("roles")
            ));
        }
        return users;
    }


    public static List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("SELECT * FROM userlogin");
        ResultSet rst = stm.executeQuery();
        List<User> users=new ArrayList<>();

        while (rst.next()){
            users.add(new User(
                    rst.getString("username"),
                    rst.getString("email"),
                    rst.getString("password"),
                    rst.getString("roles")
            ));

        }
        return users;
    }
    public static boolean deleteUser(String username) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("DELETE From userlogin WHERE username=?");
        stm.setObject(1,username);
        return stm.executeUpdate()>0;
    }
    public static boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        PreparedStatement stm=con.prepareStatement("UPDATE userlogin SET username=?, email=?, roles=? WHERE password=?");
        stm.setObject(1,user.getUserName());
        stm.setObject(2,user.getEmail());
        stm.setObject(3,user.getRoles());
        stm.setObject(4,user.getPassword());

       return stm.executeUpdate()>0;
    }
}
