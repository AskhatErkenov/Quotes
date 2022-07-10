package com.example.quotes;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authorization;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button registration;

    @FXML
    void initialize() {

        authorization.setOnAction(actionEvent -> {
            String log = login.getText().trim();
            String pass = password.getText().trim();
            if (!log.equals("") && !pass.equals(""))
                logUser(log, pass);
            else
                System.out.println("Вы не заполнили логин и пароль");
        });

        registration.setOnAction(actionEvent -> {
            registration.getScene().getWindow().hide();
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/com/example/quotes/quote.fxml"));
            try {
                load.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent parent = load.getRoot();
            Stage st = new Stage();
            st.setScene(new Scene(parent));
            st.showAndWait();
        });
    }

    private void logUser(String log, String pass) {
        DBconnection dbConn = new DBconnection();
        User user = new User();
        user.setLogin(log);
        user.setPassword(pass);
        dbConn.getUser(user);
        ResultSet resultSet = dbConn.getUser(user);
        int count = 0;
        try {
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count >= 1) {
            System.out.println("Получилось");
        }
    }

}
