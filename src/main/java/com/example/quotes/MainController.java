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
import javafx.scene.Node;

public class MainController {

    private Stage stage;
    private Scene scene;
    private Parent root;

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

    public static User user = new User();

    @FXML
    void initialize() throws SQLException{

        authorization.setOnAction(actionEvent -> {
            String log = login.getText().trim();
            String pass = password.getText().trim();
            if (!log.equals("") && !pass.equals("")) {
                try {
                    logUser(log, pass);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("Вы не заполнили логин и пароль");
        });

        registration.setOnAction(actionEvent -> {
            try {
                openNewScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void logUser(String log, String pass) throws SQLException, IOException {
        DBconnection dbConn = new DBconnection();
        User user = new User();
        user.setLogin(log);
        user.setPassword(pass);
        dbConn.getUser(user);
        ResultSet resultSet = dbConn.getUser(user);
        int count = 0;
        try {
            while (resultSet.next()) {
                this.user = user;
                user.setId(resultSet.getInt(1));
                user.setSurname(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setPatronymic(resultSet.getString(4));
                user.setLogin(resultSet.getString(5));
                user.setPassword(resultSet.getString(6));
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (count >= 1) {
            Parent root = FXMLLoader.load(getClass().getResource("quote.fxml"));
            Stage stage = (Stage) authorization.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    public void openNewScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registration.fxml"));
        Stage stage = (Stage) authorization.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
