package com.example.quotes;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import static com.example.quotes.MainController.user;


public class QuoteController implements Initializable {

    @FXML
    private Button btn;

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
    private TextField txt_id;

    @FXML
    private TextField txt_quote;

    @FXML
    private TextField txt_teacher;

    @FXML
    private TextField txt_subject;

    @FXML
    private TextField txt_date;

    @FXML
    private Button editDataButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button myQuoteButton;



    int index = -1;

    Connection conn =null;
    PreparedStatement pst = null;

    @FXML
    void EditData(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("editRegistration.fxml"));
        Stage stage = (Stage) editDataButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void Exit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("autorization.fxml"));
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void MyQuote(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("profile.fxml"));
        Stage stage = (Stage) myQuoteButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    public void Add (){
        conn = DBconnection.ConnDB();
        String sql = "insert into "+ Constants.TABLE_TEACHER_QUOTES + " (" + Constants.COLUMNS_TEACHER_QUOTES_QUOTE + "," + Constants.COLUMNS_TEACHER_QUOTES_TEACHER + "," + Constants.COLUMNS_TEACHER_QUOTES_SUBJECT + "," + Constants.COLUMNS_TEACHER_QUOTES_DATE +"," + Constants.COLUMNS_TEACHER_QUOTES_USER +")values(?,?,?,?,"+user.getId() +")";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_quote.getText());
            pst.setString(2, txt_teacher.getText());
            pst.setString(3, txt_subject.getText());
            pst.setString(4, txt_date.getText());
            pst.execute();
            QuoteTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    @FXML
    void getSelected (MouseEvent event){
        index = table_quotes.getSelectionModel().getSelectedIndex();
        if (index <= -1){

            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_quote.setText(col_quote.getCellData(index).toString());
        txt_teacher.setText(col_teacher.getCellData(index).toString());
        txt_subject.setText(col_subject.getCellData(index).toString());
        txt_date.setText(col_date.getCellData(index).toString());

    }

    public void Edit (){
        try {
            conn = DBconnection.ConnDB();
            String value1 = txt_id.getText();
            String value2 = txt_quote.getText();
            String value3 = txt_teacher.getText();
            String value4 = txt_subject.getText();
            String value5 = txt_date.getText();
            String sql = "update "+ Constants.TABLE_TEACHER_QUOTES +" set quote= '"+value2+"',teacher= '"+
                    value3+"',subject= '"+value4+"',date= '"+value5+"' where id='"+value1+"' and access = " +user.getAccess();
            pst= conn.prepareStatement(sql);
            pst.execute();
            QuoteTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Delete(){
        conn = DBconnection.ConnDB();
        String sql = "delete from " + Constants.TABLE_TEACHER_QUOTES + " where " + Constants.COLUMNS_TEACHER_QUOTES_ID + " = ? AND access ="+user.getAccess();
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
            QuoteTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }


    public void QuoteTable(){
        Connection conn = DBconnection.ConnDB();
        ObservableList<Quote> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from teacher_quotes where access <="+user.getAccess());
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