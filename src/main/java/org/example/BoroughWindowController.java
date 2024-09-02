package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * The controller class for the Borough Window page, executes when a user clicks
 * a specific borough for more information
 */

public class BoroughWindowController implements Initializable
{
    private String borough;
    DataSingleton singleton = DataSingleton.getInstance();

    // Stores the data within the start and end dates
    private ArrayList<CovidData> date_records;
    private String from, to;

    @FXML Label name;
    @FXML Label record_label;
    @FXML ChoiceBox<String> choiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        borough = singleton.getBorough();
        name.setText(borough.toUpperCase());

        choiceBox.getItems().addAll("Date", "Retail(GMR)", "Grocery(GMR)", "Parks(GMR)", "Transit(GMR)", "Workplaces(GMR)", "Residential(GMR)", "New Cases", "Total Cases", "New Deaths", "Total Deaths");
        choiceBox.setOnAction(this::sortChoice);

        //Retrieves the data from within the start and end dates
        this.date_records = singleton.getDateRecords();

        outputRecords();
    }

    private void outputRecords() //initial display (borough-specific data)
    {
        String out = "";
        int count = 0;
        for (CovidData data : date_records)
        {
            if (data.getBorough().equals(borough))
            {
                count++;
                out = out + data.toString() + "\n";
            }
        }
        record_label.setText(out);
    }

    //displayed data is set to the specified arraylist
    private void setLabel(ArrayList<CovidData> borough_records)
    {
        String out = "";
        for(CovidData data : borough_records)
        {
            out = out + data.toString() + "\n";
        }
        record_label.setText(out);
    }

    //gets the specific data for a certain borough
    private ArrayList<CovidData> getBoroughRecords()
    {
        ArrayList<CovidData> borough_records = new ArrayList<CovidData>();
        for(CovidData data : date_records)
        {
            if (data.getBorough().equals(borough))
            {
                borough_records.add(data);
            }
        }
        return borough_records;
    }

    //user clicks an option from drop down menu, data is sorted by that choice
    private void sortChoice(ActionEvent event)
    {
        String choice = choiceBox.getValue();
        if (choice.equals("Date"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return o2.getDate().compareTo(o1.getDate()); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("Retail(GMR)"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getRetailRecreationGMR()).compareTo(Integer.valueOf(o1.getRetailRecreationGMR())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("Grocery(GMR)"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getGroceryPharmacyGMR()).compareTo(Integer.valueOf(o1.getGroceryPharmacyGMR())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("Parks(GMR)"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getParksGMR()).compareTo(Integer.valueOf(o1.getParksGMR())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("Transit(GMR)"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getTransitGMR()).compareTo(Integer.valueOf(o1.getTransitGMR())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("Workplaces(GMR)"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getWorkplacesGMR()).compareTo(Integer.valueOf(o1.getWorkplacesGMR())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("Residential(GMR)"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getResidentialGMR()).compareTo(Integer.valueOf(o1.getResidentialGMR())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("New Cases"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getNewCases()).compareTo(Integer.valueOf(o1.getNewCases())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("Total Cases"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getTotalCases()).compareTo(Integer.valueOf(o1.getTotalCases())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("New Deaths"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getNewDeaths()).compareTo(Integer.valueOf(o1.getNewDeaths())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }
        else if (choice.equals("Total Deaths"))
        {
            ArrayList<CovidData> borough_records = getBoroughRecords();

            Collections.sort(borough_records, new Comparator<CovidData>() {

                public int compare(CovidData o1, CovidData o2) {
                    // compare two instances of CovidData
                    return Integer.valueOf(o2.getTotalDeaths()).compareTo(Integer.valueOf(o1.getTotalDeaths())); //from highest to lowest
                }
            });
            setLabel(borough_records);
        }

    }


}
