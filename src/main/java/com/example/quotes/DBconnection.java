package com.example.quotes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBconnection extends Сonfigurations {
    Connection connect;

    public Connection getConnectionDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionsString = "jdbc:mysql://" + hostDB + ":" + portDB + "/" + nameDB;

        connect = DriverManager.getConnection(connectionsString, userDB, passwordDB);
        return connect;
    }

    public void registrationUsers(User user) {
        String insert = "INSERT INTO " + Constants.TABLE_USERS + "(" + Constants.COLUMNS_USERS_SURNAME + "," + Constants.COLUMNS_USERS_NAME + "," +
                Constants.COLUMNS_USERS_PATRONYMIC + "," + Constants.COLUMNS_USERS_LOGIN + "," + Constants.COLUMNS_USERS_PASSWORD_HASH + ")" +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = getConnectionDB().prepareStatement(insert);
            prSt.setString(1, user.getSurname());
            prSt.setString(2, user.getName());
            prSt.setString(3, user.getPatronymic());
            prSt.setString(4, user.getLogin());
            prSt.setString(5, user.getPassword());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
