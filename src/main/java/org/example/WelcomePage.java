package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class opens WelcomePage fxml
 * Controller class is WelcomePageController
 */

public class WelcomePage extends Application
{

    DataSingleton data = DataSingleton.getInstance();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        //loads the fxml file for the welcome page
        Parent root = FXMLLoader.load(getClass().getResource("/WelcomePage1.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Covid 19 Statistics Viewer");
        data.setWelcomePage(this); //sets the welcome page in the singleton set (may not need!!!)
        stage.show();
    }
}
