package controller;

import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import util.CustomerTM;

public class InsertFormController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtFieldID;

    @FXML
    private JFXTextField txtFieldName;

    @FXML
    private JFXTextField txtFieldAddress;

    @FXML
    private JFXTextField txtFieldContact;

    @FXML
    private TableView<CustomerTM> tblViewCustomerDetail;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnNew;

    public void initialize(URL url, ResourceBundle rb) {
        tblViewCustomerDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tblViewCustomerDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("cusName"));
        tblViewCustomerDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        tblViewCustomerDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("cusContact"));

        txtFieldID.setDisable(true);
        txtFieldName.setDisable(true);
        txtFieldAddress.setDisable(true);
        txtFieldContact.setDisable(true);

        try {
            loadAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void btnAdd_OnAction(ActionEvent event) {
        if (txtFieldID.getText().isEmpty() || txtFieldName.getText().isEmpty() || txtFieldAddress.getText().isEmpty()
                || txtFieldContact.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Empty Fields").show();
            return;
        }

//
        ObservableList<CustomerTM> customers = tblViewCustomerDetail.getItems();
        CustomerTM newCustomer = new CustomerTM(
                txtFieldID.getText(),
                txtFieldName.getText(),
                txtFieldAddress.getText(),
                txtFieldContact.getText()
        );
        try {
            saveCustomer(newCustomer);
            customers.add(newCustomer);
            btnNew_OnAction(event);
            new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully !!").show();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnNew_OnAction(ActionEvent event) {
        txtFieldName.setDisable(false);
        txtFieldAddress.setDisable(false);
        txtFieldContact.setDisable(false);
        txtFieldName.clear();
        txtFieldContact.clear();
        txtFieldAddress.clear();

        // Generate a new id
        int maxId = 0;

        try {
            String lastCustomerId = getLastCustomerId();

            if (lastCustomerId == null) {
                maxId = 0;
            } else {
                maxId = Integer.parseInt(lastCustomerId.replace("C", ""));
            }

            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            txtFieldID.setText(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private void loadAllCustomers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

        ObservableList<CustomerTM> customers = tblViewCustomerDetail.getItems();
        customers.clear();

        while (rst.next()) {
            customers.add(new CustomerTM(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)));
        }
    }

    public String getLastCustomerId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT cusId FROM Customer ORDER BY cusId DESC LIMIT 1");
        ResultSet rst = pstm.executeQuery();
        if (rst.next()) {
            return rst.getString(1);
        } else {
            return null;
        }
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//    }

    public void saveCustomer(CustomerTM customer) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
        pstm.setString(1, customer.getCusId());
        pstm.setString(2, customer.getCusName());
        pstm.setString(3, customer.getCusAddress());
        pstm.setString(4, customer.getCusContact());
        if (pstm.executeUpdate() == 0) {
            throw new RuntimeException("Something went wrong");
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
