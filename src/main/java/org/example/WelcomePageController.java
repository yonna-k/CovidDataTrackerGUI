package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

/**
 * This class controls the user interface for the Welcome Page.
 * - Choice box to select a start and end date
 * - Display boxes for selected dates
 * - Label to show whether the input is valid or not
 * - Buttons to move to the next and the previous panel
 * - Text Flow to display the instructions
 */
public class WelcomePageController implements Initializable
{
    private ArrayList<CovidData> records;
    DataSingleton data = DataSingleton.getInstance(); //instance of the singleton set
    private String from;
    private String to;

    @FXML ChoiceBox<String> fromDate; //choice boxes
    @FXML ChoiceBox<String> toDate;
    @FXML Button next;
    @FXML Button previous;
    @FXML Label validLabel;
    @FXML TextField fromSelected;
    @FXML TextField toSelected;
    @FXML Label instructions;

    /**
     * Initialises the Welcome Page, and displays all the components.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        arrowVisible(false); //arrows are hidden

        // Loads the data, and the dates to be selected from
        CovidDataLoader cdl = new CovidDataLoader();
        records = cdl.load();
        ArrayList<String> dates = new ArrayList<String>();
        for (CovidData record : records)
        {
            dates.add(record.getDate()); //filters the records by the date range
        }
        Set<String> set = new HashSet<>(dates); //removes duplicates
        dates.clear();
        dates.addAll(set);
        Collections.sort(dates, new Comparator<String>() {

            public int compare(String o1, String o2) {
                // compare two instances of dates
                return o1.compareTo(o2); //from lowest to highest
            }
        });


        fromDate.getItems().addAll(dates); //displays date choices in choice boxes
        toDate.getItems().addAll(dates);

        if (data.getFromDate() != null){ //when going back to welcome page, saves the dates chosen
            this.from = data.getFromDate();
            fromDate.setValue(this.from);

        }
        if (data.getToDate() != null){ //when going back to welcome page, saves the dates chosen
            this.to = data.getToDate();
            toDate.setValue(this.to);
        }

        // displays any previously selected data when returning to the page
        setSelectedDates();
        welcomeInstructions();

        // on action events
        fromDate.setOnAction(this::dateChoice); //occurs when date changed in choice box
        toDate.setOnAction(this::dateChoice);
    }

    /**
     * Controls the On Action Event when the back button is clicked
     * @param mouseEvent
     * @throws Exception
     */
    @FXML
    private void backwardsClick(MouseEvent mouseEvent) throws Exception //occurs when back arrow clicked
    {
        ChallengeTask c = new ChallengeTask();
        data.setRecords(records);
        c.start((Stage)((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    /**
     * Controls the On Action Event when the forwards button is clicked
     * @param mouseEvent
     * @throws Exception
     */
    @FXML
    private void forwardsClick(MouseEvent mouseEvent) throws Exception //occurs when forward arrow clicked
    {
        MapScreen mapScreen = new MapScreen();
        data.setRecords(records);
        mapScreen.start((Stage)((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    /**
     * Allows the user to select a start and end date from the choice box.
     * @param event
     */
    @FXML
    private void dateChoice(ActionEvent event) //sets the date in the singleton class when the date is changed
    {
        String box = ((ChoiceBox) event.getSource()).getId();
        if (box.equals("fromDate"))
        {
            this.from = fromDate.getValue();
            data.setFromDate(from);
        }
        else if (box.equals("toDate"))
        {
            this.to = toDate.getValue();
            data.setToDate(to);
        }
        setSelectedDates();
    }

    /**
     * Checks to make sure that the selected dates are valid. ie. the start date occurs
     * before the end date.
     * @return
     */
    private boolean checkValid()
    {
        if ((from != null) && (to != null))
        {
            String temp_from = from.replaceAll("-", "");
            String temp_to = to.replaceAll("-", "");
            if (Integer.valueOf(temp_from) <= Integer.valueOf(temp_to))
            {
                arrowVisible(true);
                validLabel.setText("A valid date range has been selected.");
                return true;
            }
            else
            {
                arrowVisible(false);
                validLabel.setText("The starting date must occur before the end date!");
            }
            return false;

        }
        return false;
    }

    /**
     * Sets the selected dates on the text field at the bottom of the page
     */
    private void setSelectedDates ()
    {
        if(this.from != null) {
            fromSelected.setText(this.from); //sets the text field to the dates chosen when going back to welcome page
        }
        if(this.to != null){
        toSelected.setText(this.to);
        }
        if(this.from != null && this.to != null){
            checkValid();
        }
    }

    /**
     * Makes the arrows visible once a valid date range has been selected.
     * @param b
     */
    private void arrowVisible (boolean b) //sets the arrow visibility
    {
        if (b)
        {
            next.setVisible(true);
            previous.setVisible(true);
        }
        else
        {
            next.setVisible(false);
            previous.setVisible(false);
        }
    }

    /* Sets the welcome instructions within the text flow box.
     */
    private void welcomeInstructions (){
        String text = new String ("""
                                                               
                                                                - WELCOME TO COVID 19 DATA VIEWER -
                                                               
                                                            
                To begin, choose a 'from' and 'to' date using the choice boxes above. The range will be displayed below and arrows will appear which you can use to navigate through the panels!
               
                Panel 1. Borough Map : Boroughs are colour-coded based on  the number of deaths. Click on a borough to see specific records!
                
                Panel 2. Statistics Page : Use the arrows around the text on the page to see the 4 unique statistics! (average over the date range)
                
                Panel 3. Graph Page : choose from the graph options at the top left and click 'show' to load the graph. The data from all the boroughs will be displayed!
                """);
        instructions.setText(text);
    }

}
