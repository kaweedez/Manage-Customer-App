package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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

public class SearchFormController implements Initializable {
    public static Connection connection = DBConnection.getInstance().getConnection();
    public AnchorPane root;
    public JFXTextField txtSearch;
    public TableView<CustomerTM> tblCustomer;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cusName"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("cusContact"));

        load();
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                load();
            }
        });
    }

    public void load() {
        String sql = "SELECT  * FROM Customer WHERE cusId LIKE ? OR cusName LIKE ? OR cusAddress LIKE ? OR cusContact LIKE ?";
        try {

            tblCustomer.getItems().clear();
            PreparedStatement pstmSeach = connection.prepareStatement(sql);
            pstmSeach.setString(1, "%" + txtSearch.getText() + "%");
            pstmSeach.setString(2, "%" + txtSearch.getText() + "%");
            pstmSeach.setString(3, "%" + txtSearch.getText() + "%");
            pstmSeach.setString(4, "%" + txtSearch.getText() + "%");

            ResultSet resultSet = pstmSeach.executeQuery();
            ObservableList<CustomerTM> items = tblCustomer.getItems();

            while (resultSet.next()) {
                CustomerTM customer = new CustomerTM(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4));

                items.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/DashboardForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
}


