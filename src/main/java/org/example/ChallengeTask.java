package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This Panel, using the start and end dates, allows the user to choose a certain data value they would like to see on a graph.
 * The graph plots start and end date data for every  borough on the chart to show the change between both dates.
 */
public class ChallengeTask extends Application
{
    private CovidDataLoader cdl;
    private  HBox hbox = new HBox();
    private HBox hbox1 = new HBox();
    private BorderPane pane = new BorderPane();
    private Label instructions = new Label();

    /*
     * The start method is the main entry point for every JavaFX application.
     * It is called after the init() method has returned and after
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {

        stage.setTitle("Challenge Window");
        instructions.setText("Please select a graph using the buttons!");
        instructions.getStyleClass().add("instruction-label");

        //adding the buttons to the Hbox
        graphMenu(stage);

        //defining where each element is added within the pane
        pane.setTop(hbox);
        pane.setCenter(instructions);
        pane.setBottom(hbox1);
        addButtons(stage);

        //outputs the stage
        Scene scene = new Scene(pane, 900,600);
        scene.getStylesheets().add(getClass().getResource("/challengeTask.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /*
     * returns a menubar with the specific data values as menus used so a graph can be shown later
     */
    private void graphMenu(Stage s) {

        //create buttons for menu
        Button button1 = new Button("New Cases");
        Button button2 = new Button("Total Deaths");
        Button button3 = new Button("Total Cases");
        Button button4 = new Button("New Deaths");

        //add all menu options to bar
        hbox.getChildren().addAll(button1, button2, button3, button4);
        hbox.setPadding(new Insets(5));
        hbox.setSpacing(5);

        //specifies how to the handle the click event  for each button

        button1.setOnAction(new EventHandler<ActionEvent>(){
            //when clicked the below method is executed
            public void handle(ActionEvent e)
            {
                pane.setCenter(newCaseGraphCreation(s));
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>(){
            //when clicked the below method is executed
            public void handle(ActionEvent e)
            {
                pane.setCenter(totalDeathGraphCreation(s));
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>(){
            //when clicked the below method is executed
            public void handle(ActionEvent e)
            {
                pane.setCenter(totalCasesGraphCreation(s));
            }
        });

        button4.setOnAction(new EventHandler<ActionEvent>(){
            //when clicked the below method is executed
            public void handle(ActionEvent e)
            {
                pane.setCenter(newDeathGraphCreation(s));
            }
        });
    }


    /*
     * Creates two buttons used to navigate between panels
     */
    private void addButtons(Stage stage){
        // create a button
        Button backButton = new Button(" < ");
        Button forwardButton = new Button (">");

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
        hbox1.setPadding(new Insets(20,20,20,20));
        hbox1.setSpacing(734);

        backButton.setOnAction(new EventHandler<ActionEvent>(){
            //when clicked the below method is executed
            public void handle(ActionEvent e)
            {
                StatisticsPanel s = new StatisticsPanel();
                s.start((Stage)((Node) e.getSource()).getScene().getWindow());
            }
        });

        forwardButton.setOnAction(new EventHandler<ActionEvent>(){
            //when clicked the below method is executed
            public void handle(ActionEvent e)
            {
                WelcomePage w = new WelcomePage();
                try {
                    w.start((Stage)((Node) e.getSource()).getScene().getWindow());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }



    /*
     * returns a line chart of new cases at the start and end date of borough
     */
    private LineChart newCaseGraphCreation(Stage stage){
        //creating an object cdl inorder for the covid data loader to create the hash tables for each value.
        CovidDataLoader cdl = new CovidDataLoader();
        cdl.load();

        //defining the chart
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        // axis labels
        lineChart.setTitle("Change in New Cases");
        xAxis.setLabel("Boroughs");
        yAxis.setLabel("Number of Cases");

        //creating the line within the linechart
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Start Date");

        // iterates through the borough list uses each borough as an input key for the hash table inorder to get the number of new cases at start date for each borough
        for (int j = 0; j < cdl.getBoroughList().size(); j++){
            String boroughname = cdl.getBoroughList().get(j);
            series1.getData().add(new XYChart.Data(boroughname,cdl.getStartHashTableNewCase().get(boroughname) )); // plots this data onto the graph
        }

        // creating the second line within the line chart
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("End Date");
        for (int j = 0; j < cdl.getBoroughList().size(); j++){
            String boroughname = cdl.getBoroughList().get(j);
            series2.getData().add(new XYChart.Data(boroughname,cdl.getEndHashTableNewCase().get(boroughname) ));
        }

        // Apply CSS styles to the LineChart
        lineChart.getStylesheets().add(getClass().getResource("/challengeTask.css").toExternalForm());

        //defining the size
        lineChart.setMaxSize(700,400 );

        // adding the lines to the line chart
        lineChart.getData().addAll(series1, series2);
        return lineChart;
    }

    /*
     * returns a line chart of the total deaths at the start and end dates for every borough
     */
    private LineChart totalDeathGraphCreation(Stage stage){ //same as the other line charts
        CovidDataLoader cdl = new CovidDataLoader();
        cdl.load();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Change in Total Deaths");
        xAxis.setLabel("Boroughs");
        yAxis.setLabel("Number of Deaths");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Start Date");
        for (int j = 0; j < cdl.getBoroughList().size(); j++){
            String boroughname = cdl.getBoroughList().get(j);
            series1.getData().add(new XYChart.Data(boroughname,cdl.getStartHashTableTotDeaths().get(boroughname) ));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("End Date");
        for (int j = 0; j < cdl.getBoroughList().size(); j++){
            String boroughname = cdl.getBoroughList().get(j);
            series2.getData().add(new XYChart.Data(boroughname,cdl.getEndHashTableTotDeaths().get(boroughname) ));
        }

        // Apply CSS styles to the LineChart
        lineChart.getStylesheets().add(getClass().getResource("/challengeTask.css").toExternalForm());

        lineChart.setMaxSize(750,550 );
        lineChart.getData().addAll(series1, series2);
        return lineChart;
    }

    /*
     * returns a line chart of the total cases at the start and end dates for every borough
     */
    private LineChart totalCasesGraphCreation(Stage stage){ //same as the other line charts
        CovidDataLoader cdl = new CovidDataLoader();
        cdl.load();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Change in Total Cases");
        xAxis.setLabel("Boroughs");
        yAxis.setLabel("Number of Cases");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Start Date");
        for (int j = 0; j < cdl.getBoroughList().size(); j++){
            String boroughname = cdl.getBoroughList().get(j);
            series1.getData().add(new XYChart.Data(boroughname,cdl.getStartHashTableTotCase().get(boroughname) ));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("End Date");
        for (int j = 0; j < cdl.getBoroughList().size(); j++){
            String boroughname = cdl.getBoroughList().get(j);
            series2.getData().add(new XYChart.Data(boroughname,cdl.getEndHashTableTotCase().get(boroughname) ));
        }

        // Apply CSS styles to the LineChart
        lineChart.getStylesheets().add(getClass().getResource("/challengeTask.css").toExternalForm());

        lineChart.setMaxSize(750,550 );
        lineChart.getData().addAll(series1, series2);
        return lineChart;
    }

    /*
     * returns a line chart of the new deaths at the start and end dates for every borough
     */
    private LineChart newDeathGraphCreation(Stage stage){  //same as the other line charts
        CovidDataLoader cdl = new CovidDataLoader();
        cdl.load();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Change in New Deaths");
        xAxis.setLabel("Boroughs");
        yAxis.setLabel("Number of Deaths");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Start Date");
        for (int j = 0; j < cdl.getBoroughList().size(); j++){
            String boroughname = cdl.getBoroughList().get(j);
            series1.getData().add(new XYChart.Data(boroughname,cdl.getStartHashTableNewDeaths().get(boroughname) ));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("End Date");
        for (int j = 0; j < cdl.getBoroughList().size(); j++){
            String boroughname = cdl.getBoroughList().get(j);
            series2.getData().add(new XYChart.Data(boroughname,cdl.getEndHashTableNewDeaths().get(boroughname) ));
        }

        // Apply CSS styles to the LineChart
        lineChart.getStylesheets().add(getClass().getResource("/challengeTask.css").toExternalForm());

        lineChart.setMaxSize(750,550 );
        lineChart.getData().addAll(series1, series2);
        return lineChart;
    }

}
