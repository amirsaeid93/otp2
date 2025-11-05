package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WeightApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        DatabaseManager.initializeDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(WeightApplication.class.getResource("/Weight_changer.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Weight Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
