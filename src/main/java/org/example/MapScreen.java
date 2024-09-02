package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class opens MapScreen fxml page
 * Controller class is the MapScreenController
 */

public class MapScreen extends Application
{
    public MapScreen()
    {

    }

    public void start(Stage stage) throws Exception
    {
        //loads the fxml file for the map screen
        Parent root = FXMLLoader.load(getClass().getResource("/MapScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Map Screen");
        stage.show();
    }
}
