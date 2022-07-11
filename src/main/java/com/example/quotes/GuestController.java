package com.example.quotes;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;


public class GuestController implements Initializable {

    @FXML
    private TableView<Quote> table_quotes;

    @FXML
    private TableColumn<Quote, Integer> col_id;

    @FXML
    private TableColumn<Quote, String> col_quote;

    @FXML
    private TableColumn<Quote, String> col_teacher;

    @FXML
    private TableColumn<Quote, String> col_subject;

    @FXML
    private TableColumn<Quote, String> col_date;

    @FXML
    private TableColumn<Quote, Integer> col_user;

    @FXML
    private Button exitButton;



    @FXML
    void Exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("autorization.fxml"));
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void QuoteTable(){
        Connection conn = DBconnection.ConnDB();
        ObservableList<Quote> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from " + Constants.TABLE_TEACHER_QUOTES);
            ResultSet rs = ps.executeQuery();
            {
                while (rs.next()) {
                    list.add(new Quote(Integer.parseInt(rs.getString(Constants.COLUMNS_TEACHER_QUOTES_ID)),
                            rs.getString(Constants.COLUMNS_TEACHER_QUOTES_QUOTE),
                            rs.getString(Constants.COLUMNS_TEACHER_QUOTES_TEACHER),
                            rs.getString(Constants.COLUMNS_TEACHER_QUOTES_SUBJECT),
                            rs.getString(Constants.COLUMNS_TEACHER_QUOTES_DATE),
                            Integer.parseInt(rs.getString(Constants.COLUMNS_TEACHER_QUOTES_USER))));
                }
            }
            table_quotes.setItems(list);
            col_id.setCellValueFactory(new PropertyValueFactory<Quote,Integer>("id"));
            col_quote.setCellValueFactory(new PropertyValueFactory<Quote,String>("quote"));
            col_teacher.setCellValueFactory(new PropertyValueFactory<Quote,String>("teacher"));
            col_subject.setCellValueFactory(new PropertyValueFactory<Quote,String>("subject"));
            col_date.setCellValueFactory(new PropertyValueFactory<Quote,String>("date"));
            col_user.setCellValueFactory(new PropertyValueFactory<Quote,Integer>("user"));
        } catch (Exception e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        QuoteTable();
    }
}