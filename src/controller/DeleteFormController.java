package controller;

import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class DeleteFormController implements Initializable {

    public TableView<CustomerTM> tbl_remove;
    @FXML
    private AnchorPane root;
    Connection connection = DBConnection.getInstance().getConnection();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tbl_remove.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tbl_remove.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cusName"));
        tbl_remove.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        tbl_remove.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("cusContact"));


        loadtable();

//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql="SELECT * from Customer";
//        try {
//            PreparedStatement prst = connection.prepareStatement(sql);
//            ResultSet resultSet = prst.executeQuery();
//
//            ObservableList items = tbl_remove.getItems();
//
//            while (resultSet.next()){
//                String id = resultSet.getString(1);
//                String name = resultSet.getString(2);
//                String address = resultSet.getString(3);
//                String number = resultSet.getString(4);
//
//                CustomerTM customerTM = new CustomerTM(id, name, address, number);
//                items.add(customerTM);
//
//            }
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/DashboardForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btn_delete_onaction(ActionEvent actionEvent) {

//        Connection connection = DBConnection.getInstance().getConnection();
        String cusId = tbl_remove.getSelectionModel().getSelectedItem().getCusId();
        String sql = "DELETE from Customer where cusId=?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,cusId);
            int i = pst.executeUpdate();
            if(i>0){
                loadtable();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadtable(){
        tbl_remove.getItems().clear();
//        Connection connection = DBConnection.getInstance().getConnection();
        String sql="SELECT * from Customer";
        try {
            PreparedStatement prst = connection.prepareStatement(sql);
            ResultSet resultSet = prst.executeQuery();

            ObservableList<CustomerTM> items = tbl_remove.getItems();

            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String number = resultSet.getString(4);

                CustomerTM customerTM = new CustomerTM(id, name, address, number);
                items.add(customerTM);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
