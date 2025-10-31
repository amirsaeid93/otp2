package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class WeightController {

    @FXML
    private TextField weightField;
    @FXML
    private TextField heightField;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblInvalid;

    private ResourceBundle bundle;

    @FXML
    public void initialize() {

        bundle = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
    }

    @FXML
    protected void onButton1Click(javafx.event.ActionEvent event) throws IOException {
        setLanguage(event, new Locale("en", "US"));
    }

    @FXML
    protected void onButton2Click(javafx.event.ActionEvent event) throws IOException {
        setLanguage(event, new Locale("fr", "FR"));
    }

    @FXML
    protected void onButton3Click(javafx.event.ActionEvent event) throws IOException {
        setLanguage(event, new Locale("ur", "PA"));
    }

    @FXML
    protected void onButton4Click(javafx.event.ActionEvent event) throws IOException {
        setLanguage(event, new Locale("vi", "VI"));
    }

    @FXML
    protected void onCalculateBMI() {
        try {
            lblInvalid.setText("");
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());

            if (height > 0) {
                double bmi = weight / (height * height);
                String resultText = bundle.getString("lblResult.text");
                lblResult.setText(String.format("%s %.2f", resultText, bmi));
            } else {
                lblInvalid.setText(bundle.getString("lblInvalid.text"));
            }
        } catch (NumberFormatException e) {
            lblInvalid.setText(bundle.getString("lblInvalid.text"));
        }
    }

    private void setLanguage(javafx.event.ActionEvent event, Locale locale) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale);
        FXMLLoader fxmlLoader = new FXMLLoader(WeightApplication.class.getResource("/Weight_changer.fxml"), bundle);
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
