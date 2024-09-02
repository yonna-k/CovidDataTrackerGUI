package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class StatisticsPanel extends Application {
    private int currentStatisticIndex = 0;

    private HBox hbox1 = new HBox();

    private Text statisticText;
    // Helps us access the data
    DataSingleton singleton = DataSingleton.getInstance();

    // Stores data within the start and end dates from the data singleton
    private ArrayList<CovidData> records = new ArrayList<CovidData>();
    private String[] statistics = {"Average Retail/ Recreation GMR", "Average Grocery/ Pharmacy GMR", "Total number of (total) deaths",
            "Average of total cases"};

    /**
     * The application's entry point
     */
    @Override
    public void start(Stage primaryStage) {
        // Loads the covid data from the data loader before starting the application
        CovidDataLoader cdl = new CovidDataLoader();
        records = cdl.load();

        // Sets up the main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20, 20, 20, 20));

        //Adds an info panel at the top
        HBox infoBox = new HBox();
        Label info = new Label();
        infoBox.setStyle("-fx-background-color: #ffe4e1;");
        infoBox.setPadding(new Insets(20,20,20,20));
        infoBox.getChildren().add(info);
        info.setText("""
        This panel shows some key statistics and averages between the dates selected.
        Please use the arrows to move between the figures!""");
        info.setAlignment(Pos.CENTER);
        root.setTop(infoBox);

        // Creation of stats buttons
        Button statLeft = new Button("Prev. Stat");
        Button statRight = new Button("Next Stat");
        statLeft.setPrefWidth(180);
        statLeft.setPrefHeight(40);
        statRight.setPrefWidth(200);
        statRight.setPrefHeight(40);

        // Event handlers for the stats buttons
        statLeft.setOnAction(e -> {
            currentStatisticIndex = (currentStatisticIndex - 1 + statistics.length) % statistics.length;
            updateStatisticText();
        });

        statRight.setOnAction(e -> {
            currentStatisticIndex = (currentStatisticIndex + 1) % statistics.length;
            updateStatisticText();
        });

        // Adds navigation buttons to the layout
        root.setBottom(hbox1);
        addButtons(primaryStage);

        // Sets up container for displaying statistics, with its layout and buttons
        VBox statisticBox = new VBox();
        HBox arrows = new HBox();
        statisticBox.setAlignment(Pos.CENTER);
        statisticText = new Text();
        statisticBox.getChildren().addAll(statisticText, arrows);

        arrows.getChildren().addAll(statLeft, statRight);
        arrows.setSpacing(90);
        arrows.setPadding(new Insets(40, 180, 0, 180));
        statisticText.getStyleClass().add("statistic-text");
        root.setCenter(statisticBox);

        // Sets up the scene
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stats.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Statistics Panel");
        primaryStage.show();

        //Retrieves data from within the start and end dates
        this.records = singleton.getDateRecords();

        // Updates displayed statistic to the first one in the arraylist
        updateStatisticText();
    }

    /**
     * Updates displayed statistic, by calling the compute statistic method on the current statisic, and setting it as text on the panel
     */
    private void updateStatisticText() {
        String statistic = statistics[currentStatisticIndex];
        String computedStatistic = computeStatistic(statistic, records);
        statisticText.setText(computedStatistic);
    }

    /**
     * Computes statistic based on the one selected
     */
    public String computeStatistic(String statistic, ArrayList<CovidData> data){
        if (data.size() == 0) {
            return "Data not loaded";
        }

        String result;

        if (statistic.equals("Average Retail/ Recreation GMR")) {
            double averageRetailRecreationGMR = data.stream().mapToInt(CovidData::getRetailRecreationGMR).average().orElse(0);
            Text text = new Text("AVERAGE RETAIL/ RECREATION GMR:" + "\n" + "\n" + averageRetailRecreationGMR);
            result = text.getText();
        } else if (statistic.equals("Average Grocery/ Pharmacy GMR")) {
            double averageGroceryPharmacyGMR = data.stream().mapToInt(CovidData::getGroceryPharmacyGMR).average().orElse(0);
            Text text = new Text("AVERAGE GROCERY/ PHARMACY GMR:" + "\n" + "\n" + averageGroceryPharmacyGMR);
            result = text.getText();
        } else if (statistic.equals("Total number of (total) deaths")) {
            int totalDeaths = data.stream().mapToInt(CovidData::getTotalDeaths).sum();
            Text text = new Text("TOTAL DEATHS:" + "\n" + "\n" + totalDeaths);
            result = text.getText();
        } else if (statistic.equals("Average of total cases")) {
            double averageTotalCases = data.stream().mapToInt(CovidData::getTotalCases).average().orElse(0);
            Text text = new Text("AVERAGE TOTAL CASES:" + "\n" + "\n" + averageTotalCases);
            result = text.getText();
        } else {
            result = "Statistic not implemented";
        }

        return result;
    }

    /*
     * Creates two buttons used to navigate between panels
     */
    private void addButtons(Stage stage) {
        // create a button
        Button backButton = new Button(" < ");
        Button forwardButton = new Button(">");

        //setting the dimensions of the buttons
        backButton.setPrefWidth(80);
        backButton.setPrefHeight(25);
        backButton.getStyleClass().add("custom-button");

        forwardButton.setPrefWidth(80);
        forwardButton.setPrefHeight(25);
        forwardButton.getStyleClass().add("custom-button");

        //add the buttons to the pane
        hbox1.getChildren().add(backButton);
        hbox1.getChildren().add(forwardButton);
        hbox1.setSpacing(734);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            //when clicked the below method is executed
            public void handle(ActionEvent e) {
                MapScreen m = new MapScreen();
                try {
                    m.start((Stage) ((Node) e.getSource()).getScene().getWindow());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        forwardButton.setOnAction(new EventHandler<ActionEvent>() {
            //when clicked the below method is executed
            public void handle(ActionEvent e) {
                ChallengeTask c = new ChallengeTask();
                c.start((Stage) ((Node) e.getSource()).getScene().getWindow());
            }
        });
    }

    public ArrayList<CovidData> getRecords()
    {
        return records;
    }
    
    public void setRecords(ArrayList<CovidData> records)
    {
        this.records = records;
    }
    
}