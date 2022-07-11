package com.example.quotes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.annotations.Where;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.quotes.MainController.user;

public class EditRegistrationController implements Initializable {


    @FXML
    private TableView<User> table_users;

    @FXML
    private TableColumn<User, Integer> col_id;

    @FXML
    private TableColumn<User, String> col_surname;

    @FXML
    private TableColumn<User, String> col_name;

    @FXML
    private TableColumn<User, String> col_patronymic;

    @FXML
    private TableColumn<User, String> col_login;

    @FXML
    private TableColumn<User, String> col_password;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_login;

    @FXML
    private TextField txt_name;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_patronymic;

    @FXML
    private TextField txt_surname;

    @FXML
    private Button myQuoteButton;

    @FXML
    private Button allQuotesButton;

    @FXML
    private Button exitButton;

    @FXML
    void MyQuote(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage) myQuoteButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void AllQuotes(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("quote.fxml"));
        Stage stage = (Stage) allQuotesButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void Exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("autorization.fxml"));
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    int index = -1;

    Connection conn =null;
    PreparedStatement pst = null;

    @FXML
    void getSelected (MouseEvent event){
        index = table_users.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_surname.setText(col_surname.getCellData(index).toString());
        txt_name.setText(col_name.getCellData(index).toString());
        txt_patronymic.setText(col_patronymic.getCellData(index).toString());
        txt_login.setText(col_login.getCellData(index).toString());
        txt_password.setText(col_password.getCellData(index).toString());

    }

    public void Edit (){
        try {
            conn = DBconnection.ConnDB();
            String value1 = txt_id.getText();
            String value2 = txt_surname.getText();
            String value3 = txt_name.getText();
            String value4 = txt_patronymic.getText();
            String value5 = txt_login.getText();
            String value6 = txt_password.getText();
            String sql = "update users set surname= '"+value2+"',name= '"+
                    value3+"',patronymic= '"+value4+"',login= '"+value5+"',password_hash= '"+value6+"' where id='"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            UpdateTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void UpdateTable() {
        Connection conn = DBconnection.ConnDB();
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from " + Constants.TABLE_USERS + " where id = " + user.getId());
            ResultSet rs = ps.executeQuery();
            {
                while (rs.next()) {
                        list.add(new User(Integer.parseInt(rs.getString(Constants.COLUMNS_USERS_ID)),
                                rs.getString(Constants.COLUMNS_USERS_SURNAME),
                                rs.getString(Constants.COLUMNS_USERS_NAME),
                                rs.getString(Constants.COLUMNS_USERS_PATRONYMIC),
                                rs.getString(Constants.COLUMNS_USERS_LOGIN),
                                rs.getString(Constants.COLUMNS_USERS_PASSWORD_HASH)));
                }
            }
            table_users.setItems(list);
            col_id.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
            col_surname.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
            col_name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            col_patronymic.setCellValueFactory(new PropertyValueFactory<User, String>("patronymic"));
            col_login.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
            col_password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));

    }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
    }
}