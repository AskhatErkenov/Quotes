package com.example.quotes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        assert authorization != null : "fx:id=\"authorization\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert registration != null : "fx:id=\"registration\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

}
