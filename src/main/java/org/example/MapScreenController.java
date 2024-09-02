package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controller class for the MapScreen fxml page
 */
public class MapScreenController implements Initializable
{
    private ArrayList<CovidData> records;
    private HashMap<String, String> b_names = new HashMap<String, String>();
    private ArrayList<Polygon> hexagons = new ArrayList<Polygon>();

    private String from;
    private String to;

    DataSingleton data = DataSingleton.getInstance(); //instance of singleton set

    //all the polygons for the map
    @FXML Polygon enfi;
    @FXML Polygon barn;
    @FXML Polygon hrgy;
    @FXML Polygon walt;
    @FXML Polygon hrrw;
    @FXML Polygon bren;
    @FXML Polygon camd;
    @FXML Polygon isli;
    @FXML Polygon hack;
    @FXML Polygon redb;
    @FXML Polygon have;
    @FXML Polygon hill;
    @FXML Polygon eali;
    @FXML Polygon kens;
    @FXML Polygon wstm;
    @FXML Polygon towh;
    @FXML Polygon newh;
    @FXML Polygon bark;
    @FXML Polygon houn;
    @FXML Polygon hamm;
    @FXML Polygon wand;
    @FXML Polygon city;
    @FXML Polygon gwch;
    @FXML Polygon bexl;
    @FXML Polygon rich;
    @FXML Polygon mert;
    @FXML Polygon lamb;
    @FXML Polygon sthw;
    @FXML Polygon lews;
    @FXML Polygon king;
    @FXML Polygon sutt;
    @FXML Polygon croy;
    @FXML Polygon brom;



    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        //adds all the hexagons to the arraylist
        hexagons.add(enfi);
        hexagons.add(barn);
        hexagons.add(hrgy);
        hexagons.add(walt);
        hexagons.add(hrrw);
        hexagons.add(bren);
        hexagons.add(camd);
        hexagons.add(isli);
        hexagons.add(hack);
        hexagons.add(redb);
        hexagons.add(have);
        hexagons.add(hill);
        hexagons.add(eali);
        hexagons.add(kens);
        hexagons.add(wstm);
        hexagons.add(towh);
        hexagons.add(newh);
        hexagons.add(bark);
        hexagons.add(houn);
        hexagons.add(hamm);
        hexagons.add(wand);
        hexagons.add(city);
        hexagons.add(gwch);
        hexagons.add(bexl);
        hexagons.add(rich);
        hexagons.add(mert);
        hexagons.add(lamb);
        hexagons.add(sthw);
        hexagons.add(lews);
        hexagons.add(king);
        hexagons.add(sutt);
        hexagons.add(croy);
        hexagons.add(brom);
        //maps all the abbreviations to their full names
        b_names.put("enfi", "Enfield");
        b_names.put("barn", "Barnet");
        b_names.put("hrgy", "Haringey");
        b_names.put("walt", "Waltham Forest");
        b_names.put("hrrw", "Harrow");
        b_names.put("bren", "Brent");
        b_names.put("camd", "Camden");
        b_names.put("isli", "Islington");
        b_names.put("hack", "Hackney");
        b_names.put("redb", "Redbridge");
        b_names.put("have", "Havering");
        b_names.put("hill", "Hillingdon");
        b_names.put("eali", "Ealing");
        b_names.put("kens", "Kensington And Chelsea");
        b_names.put("wstm", "Westminster");
        b_names.put("towh", "Tower Hamlets");
        b_names.put("newh", "Newham");
        b_names.put("bark", "Barking And Dagenham");
        b_names.put("houn", "Hounslow");
        b_names.put("hamm", "Hammersmith And Fulham");
        b_names.put("wand", "Wandsworth");
        b_names.put("city", "City Of London");
        b_names.put("gwch", "Greenwich");
        b_names.put("bexl", "Bexley");
        b_names.put("rich", "Richmond Upon Thames");
        b_names.put("mert", "Merton");
        b_names.put("lamb", "Lambeth");
        b_names.put("sthw", "Southwark");
        b_names.put("lews", "Lewisham");
        b_names.put("king", "Kingston Upon Thames");
        b_names.put("sutt", "Sutton");
        b_names.put("croy", "Croydon");
        b_names.put("brom", "Bromley");
        records = data.getRecords(); //
        from = data.getFromDate();
        to = data.getToDate();
        determineColour();
    }

    public void determineColour() //changes the colour according to the deaths
    {
        for (Polygon p : hexagons)
        {
            //for all hexagons
            String borough = b_names.get(p.getId());
            String temp_from = from.replaceAll("-", "");
            String temp_to = to.replaceAll("-", "");
            int total = 0;
            for (CovidData d : records)
            {
                //for all covid data
                String b_date = d.getDate().replaceAll("-", "");
                Integer temp_date = Integer.valueOf(b_date);
                if ((d.getBorough().equals(borough)) && (temp_date >= Integer.valueOf(temp_from)) && (temp_date <= Integer.valueOf(temp_to)))
                {
                    //if the data is within the date range
                    total = total + d.getNewDeaths();
                }
            }
            colourSetter(total, p);
        }
    }



    public void colourSetter(int total, Polygon p) //changes colour of the polygon depending on the deaths
    {
        if ((total > 0) && (total <= 250)) //0-500
        {
            p.setFill(Color.web("#d8e2dc")); //light blue
        }
        else if ((total > 250) && (total <= 500)) //500-600
        {
            p.setFill(Color.web("#ffe5d9")); //cream
        }
        else if ((total > 500) && (total <= 750)) //600-800
        {
            p.setFill(Color.web("#ffd6de")); //light pink
        }
        else if ((total > 750) && (total <= 1000)) //800-1000
        {
            p.setFill(Color.web("#f4acb7")); //dark pink
        }
        else if (total > 1000)
        {
            p.setFill(Color.web("#9d8189")); //brown
        }
    }



    @FXML
    public void click(MouseEvent mouseEvent) //occurs when the polygon is clicked
    {
        String borough = "";
        if (mouseEvent.getSource() instanceof Polygon)
        {
            borough = b_names.get(((Polygon) mouseEvent.getSource()).getId()); //gets the borough name of the clicked polygon
        }
        else if (mouseEvent.getSource() instanceof Label)
        {
            borough = b_names.get(((Label) mouseEvent.getSource()).getText().toLowerCase()); //if label of polygon clicked instead
        }

        data.setBorough(borough);
        data.setRecords(records);
        //open new window
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/BoroughWindow.fxml")); //loads the borough window
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root1));
            stage1.setResizable(false);
            stage1.setTitle("Borough Data");
            stage1.show();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @FXML
    public void backwardsClick(MouseEvent mouseEvent) throws Exception //occurs when the back arrow is clicked
    {
        WelcomePage w = data.getWelcomePage();
        w.start((Stage)((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    @FXML
    public void forwardsClick(MouseEvent mouseEvent) throws Exception //occurs when the forward arrow is clicked
    {
        StatisticsPanel s = new StatisticsPanel();
        s.start((Stage)((Node) mouseEvent.getSource()).getScene().getWindow());
    }

}
