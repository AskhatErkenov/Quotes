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
        registration.setOnAction(actionEvent -> {registration.getScene().getWindow().hide();
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/com/example/quotes/registration.fxml"));
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
