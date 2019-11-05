package controller;

import com.jfoenix.controls.JFXTextField;
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
import java.util.ResourceBundle;

public class SearchFormController implements Initializable {
    public AnchorPane root;
public JFXTextField txtSearch;
    public TableView<CustomerTM> tblCustomer;

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/DashboardForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("cusId"));
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("cusName"));
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("cusContact"));




    }
}
