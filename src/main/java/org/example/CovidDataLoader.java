package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class CovidDataLoader {
 
    /** 
     * Return an ArrayList containing the rows in the Covid London data set csv file.
     */

    // initializing all the hash- sets and tables
    private HashSet<String> boroughSet = new HashSet<>();
    private ArrayList<String> list = new ArrayList<>();
    private Hashtable<String, Integer> hashtableStartValueNewCase = new Hashtable<>();
    private Hashtable<String, Integer> hashtableEndValueNewCase = new Hashtable<>();
    private Hashtable<String, Integer> hashtableStartValueTotDeath = new Hashtable<>();
    private Hashtable<String, Integer> hashtableEndValueTotDeath = new Hashtable<>();
    private Hashtable<String, Integer> hashtableStartValueTotCase = new Hashtable<>();
    private Hashtable<String, Integer> hashtableEndValueTotCase = new Hashtable<>();
    private Hashtable<String, Integer> hashtableStartValueNewDeath = new Hashtable<>();
    private Hashtable<String, Integer> hashtableEndValueNewDeath = new Hashtable<>();

    DataSingleton data = DataSingleton.getInstance();
    public String startDate = data.getFromDate();
    public String endDate = data.getToDate();



    public ArrayList<CovidData> load() {
        //everytime load() is called the hashtables are reset. Each data-value has a start and end hash table.
        hashtableStartValueNewCase = new Hashtable<>();
        hashtableEndValueNewCase = new Hashtable<>();
        hashtableStartValueTotDeath = new Hashtable<>();
        hashtableEndValueTotDeath = new Hashtable<>();
        hashtableStartValueTotCase = new Hashtable<>();
        hashtableEndValueTotCase = new Hashtable<>();
        hashtableStartValueNewDeath = new Hashtable<>();
        hashtableEndValueNewDeath = new Hashtable<>();
        System.out.println("Begin loading Covid London dataset...");
        System.out.println("Begin loading Covid London dataset...");
        ArrayList<CovidData> records = new ArrayList<CovidData>();
        try{
            URL url = getClass().getResource("/covid_london.csv");
            InputStreamReader isr = new InputStreamReader(url.openStream());
            CSVReader reader = new CSVReader(isr);
            //CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            //CSVReader reader = new CSVReader(new FileReader(new File("C:\\Users\\k23005450\\Documents\\PPA\\Covid-19-Statistics-ammu\\cw4_covid_v1 - with Welcome Page\\ppa-assignment-4-starter\\covid_london.csv")));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                
                String date    = line[0];
                String borough    = line[1];
                boroughSet.add(borough);
                int retailRecreationGMR    = convertInt(line[2]);    
                int groceryPharmacyGMR    = convertInt(line[3]);    
                int parksGMR    = convertInt(line[4]);    
                int transitGMR    = convertInt(line[5]);    
                int workplacesGMR    = convertInt(line[6]);    
                int residentialGMR    = convertInt(line[7]);    
                int newCases    = convertInt(line[8]);    
                int totalCases    = convertInt(line[9]);    
                int newDeaths    = convertInt(line[10]);    
                int totalDeaths    = convertInt(line[11]);                

                CovidData record = new CovidData(date,borough,retailRecreationGMR,
                    groceryPharmacyGMR,parksGMR,transitGMR,workplacesGMR,
                    residentialGMR,newCases,totalCases,newDeaths,totalDeaths);
                records.add(record);
            }
        } catch(IOException e){ //| URISyntaxException
            System.out.println("Something Went Wrong?!");
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Number of Loaded Records: " + records.size());

        list = new ArrayList<>(boroughSet);
        // used to find the number of new cases for each borough at the start and end of the selected dates.
        calcChangeNewCases(startDate, endDate,records,list,hashtableStartValueNewCase,hashtableEndValueNewCase);
        calcChangeTotDeaths(startDate, endDate,records,list,hashtableStartValueTotDeath,hashtableEndValueTotDeath);
        calcChangeTotCases(startDate, endDate,records,list,hashtableStartValueTotCase,hashtableEndValueTotCase);
        calcChangeNewDeaths(startDate, endDate,records,list,hashtableStartValueNewDeath,hashtableEndValueNewDeath);

        return records;
    }

    /**
     * finds the number of new cases of every borough at the selected start and end date
     */
    public void calcChangeNewCases(String startDate, String endDate, ArrayList <CovidData> records,ArrayList <String> boroughlist, Hashtable hashtableStartValueNewCase, Hashtable hashtableEndValueNewCase ){
        // iterating through all the boroughs in the list.
        for (String borough: boroughlist){
            //iterates through every record in the records arraylist records
            for (CovidData cd: records){
                //finds the records which consist of the current borough and the startdate passed as a parameter
                if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(startDate)) && (startDate.equals(endDate))){
                    hashtableStartValueNewCase.put(borough, cd.getNewCases()); // from this record the number of new cases from that date is added to a hashtable with the borough as a key
                    hashtableEndValueNewCase.put(borough, cd.getNewCases());
                }
                else if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(startDate)) ){
                    hashtableStartValueNewCase.put(borough, cd.getNewCases()); // from this record the number of new cases from that date is added to a hashtable with the borough as a key
                }
                //finds the records which consist of the current borough and the enddate passed as a parameter
                else if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(endDate)) ){
                    hashtableEndValueNewCase.put(borough, cd.getNewCases());// from this record the number of new cases from that date is added to a hashtable with the borough as a key
                }
            }
        }
    }

    public void calcChangeTotCases(String startDate, String endDate, ArrayList <CovidData> records,ArrayList <String> boroughlist, Hashtable hashtableStartValueTotCase, Hashtable hashtableEndValueTotCase ){
        // iterating through all the boroughs in the list.
        for (String borough: boroughlist){
            //iterates through every record in the records arraylist records
            for (CovidData cd: records){
                //finds the records which consist of the current borough and the startdate passed as a parameter
                if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(startDate)) && (startDate.equals(endDate))){
                    hashtableStartValueTotCase.put(borough, cd.getTotalCases()); // from this record the number of new cases from that date is added to a hashtable with the borough as a key
                    hashtableEndValueTotCase.put(borough, cd.getTotalCases());
                }
                else if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(startDate)) ){
                    hashtableStartValueTotCase.put(borough, cd.getTotalCases()); // from this record the number of new cases from that date is added to a hashtable with the borough as a key
                }
                //finds the records which consist of the current borough and the enddate passed as a parameter
                else if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(endDate)) ){
                    hashtableEndValueTotCase.put(borough, cd.getTotalCases());// from this record the number of new cases from that date is added to a hashtable with the borough as a key
                }
            }
        }
    }

    /**
     * finds the number of new cases of every borough at the selected start and end date
     */
    public void calcChangeTotDeaths(String startDate, String endDate, ArrayList <CovidData> records,ArrayList <String> boroughlist, Hashtable hashtableStartValueTotDeath, Hashtable hashtableEndValueTotDeath ){
        // iterating through all the boroughs in the list.
        for (String borough: boroughlist){
            //iterates through every record in the records arraylist records
            for (CovidData cd: records){
                //finds the records which consist of the current borough and the startdate passed as a parameter
                if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(startDate)) && (startDate.equals(endDate)) ){
                    hashtableStartValueTotDeath.put(borough, cd.getTotalDeaths()); // from this record the number of new cases from that date is added to a hashtable with the borough as a key
                    hashtableEndValueTotDeath.put(borough, cd.getTotalDeaths());
                }
                else if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(startDate)) ){
                    hashtableStartValueTotDeath.put(borough, cd.getTotalDeaths()); // from this record the number of new cases from that date is added to a hashtable with the borough as a key
                }
                //finds the records which consist of the current borough and the enddate passed as a parameter
                else if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(endDate)) ){
                    hashtableEndValueTotDeath.put(borough, cd.getTotalDeaths());// from this record the number of new cases from that date is added to a hashtable with the borough as a key
                }
            }
        }
    }

    /**
     * finds the number of new cases of every borough at the selected start and end date
     */
    public void calcChangeNewDeaths(String startDate, String endDate, ArrayList <CovidData> records,ArrayList <String> boroughlist, Hashtable hashtableStartValueNewDeath, Hashtable hashtableEndValueNewDeath ){
        // iterating through all the boroughs in the list.
        for (String borough: boroughlist){
            //iterates through every record in the records arraylist records
            for (CovidData cd: records){
                //finds the records which consist of the current borough and the startdate passed as a parameter
                if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(startDate)) && (startDate.equals(endDate)) ){
                    hashtableStartValueNewDeath.put(borough, cd.getNewDeaths()); // from this record the number of new cases from that date is added to a hashtable with the borough as a key
                    hashtableEndValueNewDeath.put(borough, cd.getNewDeaths());
                }
                else if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(startDate)) ){
                    hashtableStartValueNewDeath.put(borough, cd.getNewDeaths()); // from this record the number of new cases from that date is added to a hashtable with the borough as a key
                }
                //finds the records which consist of the current borough and the enddate passed as a parameter
                else if ((cd.getBorough().equals(borough)) && (cd.getDate().equals(endDate)) ){
                    hashtableEndValueNewDeath.put(borough, cd.getNewDeaths());// from this record the number of new cases from that date is added to a hashtable with the borough as a key
                }
            }
        }
    }

    /**
     * gets hashtable which stores boroughs as keys which each have their hash values as the new case number at the start date.
     */
    public Hashtable getStartHashTableNewCase(){
        return hashtableStartValueNewCase;
    }

    /**
     * gets hashtable which stores boroughs as keys which each have their hash values as the new case number at the end date.
     */
    public Hashtable getEndHashTableNewCase(){
        return hashtableEndValueNewCase;
    }

    /**
     * gets hashtable which stores boroughs as keys which each have their hash values as the total case number at the start date.
     */
    public Hashtable getStartHashTableTotCase(){
        return hashtableStartValueTotCase;
    }

    /**
     * gets hashtable which stores boroughs as keys which each have their hash values as the total case number at the end date.
     */
    public Hashtable getEndHashTableTotCase(){
        return hashtableEndValueTotCase;
    }

    /**
     * gets hashtable which stores boroughs as keys which each have their hash values as the new death number at the start date.
     */
    public Hashtable getStartHashTableNewDeaths(){
        return hashtableStartValueNewDeath;
    }

    /**
     * gets hashtable which stores boroughs as keys which each have their hash values as the new death number at the end date.
     */
    public Hashtable getEndHashTableNewDeaths(){
        return hashtableEndValueNewDeath;
    }

    /**
     * gets hashtable which stores boroughs as keys which each have their hash values as the total death number at the start date.
     */
    public Hashtable getStartHashTableTotDeaths(){
        return hashtableStartValueTotDeath;
    }

    /**
     * gets hashtable which stores boroughs as keys which each have their hash values as the total death number at the end date.
     */
    public Hashtable getEndHashTableTotDeaths(){
        return hashtableEndValueTotDeath;
    }

    /**
     *  returns array list which contains all the boroughs without duplicates.
     */
    public ArrayList<String> getBoroughList(){
        return list;
    }

    /**
     *
     * @param doubleString the string to be converted to Double type
     * @return the Double value of the string, or -1.0 if the string is 
     * either empty or just whitespace
     */
    private Double convertDouble(String doubleString){
        if(doubleString != null && !doubleString.trim().equals("")){
            return Double.parseDouble(doubleString);
        }
        return 0d;
    }

    /**
     *
     * @param intString the string to be converted to Integer type
     * @return the Integer value of the string, or -1 if the string is 
     * either empty or just whitespace
     */
    private Integer convertInt(String intString){
        if(intString != null && !intString.trim().equals("")){
            return Integer.parseInt(intString);
        }
        return 0;
    }

}
