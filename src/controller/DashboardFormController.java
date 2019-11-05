package controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardFormController {
    public AnchorPane root;
    public ImageView imgAddItems;
    public ImageView ImgPlaceOrder;
    public ImageView ImgSearchOrder;
    public ImageView imgAddCus;
    public Label lblMain;
    public Label lblDescription;

    public void initialize() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void navigate(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            Parent root = null;
            switch (icon.getId()) {
                case "imgAddCus":
                    root = FXMLLoader.load(this.getClass().getResource("/view/InsertForm.fxml"));
                    break;
                case "imgAddItems":
                    root = FXMLLoader.load(this.getClass().getResource("/view/UpdateForm.fxml"));
                    break;
                case "ImgPlaceOrder":
                    root = FXMLLoader.load(this.getClass().getResource("/view/DeleteForm.fxml"));
                    break;
                case "ImgSearchOrder":
                    root = FXMLLoader.load(this.getClass().getResource("/view/SearchForm.fxml"));
                    break;
            }
            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();
            }
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            switch (icon.getId()) {
                case "imgAddCus":
                    lblMain.setText("Manage Inserts");
                    lblDescription.setText("Click to add customers");
                    break;
                case "imgAddItems":
                    lblMain.setText("Manage Updates");
                    lblDescription.setText("Click to edit customers");
                    break;
                case "ImgPlaceOrder":
                    lblMain.setText("Manage Delete");
                    lblDescription.setText("Click to delete customer");
                    break;
                case "ImgSearchOrder":
                    lblMain.setText("Search Customer");
                    lblDescription.setText("Click if you want to search customer");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMain.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
}
