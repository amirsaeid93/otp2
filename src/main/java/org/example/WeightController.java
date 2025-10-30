package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class WeightController {

    @FXML
    private Label label;

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
        setLanguage(event, new Locale("fa", "IR"));
    }

    @FXML
    protected void onButton4Click(javafx.event.ActionEvent event) throws IOException {
        setLanguage(event, new Locale("fi", "FI"));
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
    public void initialize (javafx.event.ActionEvent actionEvent) {
        try {
            setLanguage(actionEvent, new Locale("en", "US"));
        } catch (IOException e) {

        }
    }
    public void initialize(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale);
        label.setText(bundle.getString("label.text"));
    }
    public void onENClick(javafx.event.ActionEvent actionEvent) {
       try {
           setLanguage(actionEvent, new Locale("en", "US"));
       } catch (IOException e) {

       }

    }
}
