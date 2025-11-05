package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Locale;

public class WeightController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label lblWeight;
    @FXML
    private TextField weightField;
    @FXML
    private Label lblHeight;
    @FXML
    private TextField heightField;
    @FXML
    private Button btnCalculate;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblInvalid;


    @FXML
    private Button btnEnglish;
    @FXML
    private Button btnFrench;
    @FXML
    private Button btnUrdu;
    @FXML
    private Button btnVietnamese;

    private Locale currentLocale = new Locale("en", "US");

    @FXML
    public void initialize() {
        updateTexts();
    }

    private void updateTexts() {
        btnEnglish.setText(DatabaseManager.getTranslation("button1.text", currentLocale));
        btnFrench.setText(DatabaseManager.getTranslation("button2.text", currentLocale));
        btnUrdu.setText(DatabaseManager.getTranslation("button3.text", currentLocale));
        btnVietnamese.setText(DatabaseManager.getTranslation("button4.text", currentLocale));


        welcomeLabel.setText(DatabaseManager.getTranslation("text", currentLocale));
        lblWeight.setText(DatabaseManager.getTranslation("lblWeight.text", currentLocale));
        lblHeight.setText(DatabaseManager.getTranslation("lblHeight.text", currentLocale));
        btnCalculate.setText(DatabaseManager.getTranslation("btnCalculate.text", currentLocale));
        

        lblResult.setText("");
        lblInvalid.setText("");
    }

    @FXML
    protected void onButton1Click(ActionEvent event) {
        currentLocale = new Locale("en", "US");
        updateTexts();
    }

    @FXML
    protected void onButton2Click(ActionEvent event) {
        currentLocale = new Locale("fr", "FR");
        updateTexts();
    }

    @FXML
    protected void onButton3Click(ActionEvent event) {
        currentLocale = new Locale("ur", "PA");
        updateTexts();
    }

    @FXML
    protected void onButton4Click(ActionEvent event) {
        currentLocale = new Locale("vi", "VN");
        updateTexts();
    }

    @FXML
    protected void onCalculateBMI() {
        try {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());
            double bmi = weight / (height * height);
            lblResult.setText(String.format("BMI: %.2f", bmi));
            lblInvalid.setText("");
        } catch (NumberFormatException e) {
            lblInvalid.setText(DatabaseManager.getTranslation("error.invalidInput", currentLocale));
            lblResult.setText("");
        }
    }
}
