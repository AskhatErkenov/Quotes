package com.example.quotes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class DBconnection{

    public static Connection ConnDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://std-mysql:3306/std_1950_quote","std_1950_quote","FitMosPol8909");
            return conn;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void registrationUsers(String surname, String name, String patronymic, String login, String password) {
        String insert = "INSERT INTO " + Constants.TABLE_USERS + "(" + Constants.COLUMNS_USERS_SURNAME + "," + Constants.COLUMNS_USERS_NAME + "," +
                Constants.COLUMNS_USERS_PATRONYMIC + "," + Constants.COLUMNS_USERS_LOGIN + "," + Constants.COLUMNS_USERS_PASSWORD_HASH + ")" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = ConnDB().prepareStatement(insert);
            prSt.setString(1, surname);
            prSt.setString(2, name);
            prSt.setString(3, patronymic);
            prSt.setString(4, login);
            prSt.setString(5, password);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user) {
        ResultSet rSet = null;
        String select = "SELECT * FROM " + Constants.TABLE_USERS + " WHERE " + Constants.COLUMNS_USERS_LOGIN + "=? AND " + Constants.COLUMNS_USERS_PASSWORD_HASH + "=?";
        try {
            PreparedStatement prSt = ConnDB().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            rSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rSet;
    }
}
