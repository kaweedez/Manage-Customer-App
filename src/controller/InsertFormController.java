package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    void btnAdd_OnAction(ActionEvent event) {

    }

    @FXML
    void btnNew_OnAction(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
