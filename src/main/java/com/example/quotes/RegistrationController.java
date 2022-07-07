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
    private Button autorization;

    @FXML
    private TextField registrationLogin;

    @FXML
    private TextField registrationName;

    @FXML
    private PasswordField registrationPassword;

    @FXML
    private TextField registrationPatronymic;

    @FXML
    private TextField registrationSurname;

    @FXML
    void initialize() {
        autorization.setOnAction(actionEvent -> {autorization.getScene().getWindow().hide();
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/com/example/quotes/avtorization.fxml"));

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

}
