package com.example.quotes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField registrationSurname;

    @FXML
    private TextField registrationName;

    @FXML
    private TextField registrationPatronymic;

    @FXML
    private TextField registrationLogin;

    @FXML
    private PasswordField registrationPassword;

    @FXML
    private Button autorization;

    @FXML
    void initialize() {

        autorization.setOnAction(actionEvent -> {
            registrationNewUser();
        });

    }

    private void registrationNewUser() {
        DBconnection dbConnect = new DBconnection();
        String surname = registrationSurname.getText();
        String name = registrationName.getText();
        String patronymic = registrationPatronymic.getText();
        String login = registrationLogin.getText();
        String password = registrationPassword.getText();
        User user = new User(surname, name, patronymic, login, password);
        dbConnect.registrationUsers(user);
    }

}
