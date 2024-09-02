package org.example;
import java.util.ArrayList;

public class DataSingleton
{
    //only one instance of the class is instantiated
    private static final DataSingleton instance = new DataSingleton();

    private String borough;

    private ArrayList<CovidData> records;

    private String fromDate; //stores the from date
    private String toDate; //stores the to date

    private WelcomePage welcomePage; //may not need this!!!

    private DataSingleton()
    {

    }

    public static DataSingleton getInstance()
    {
        return instance;
    }

    public String getBorough()
    {
        return borough;
    }

    public void setBorough(String b)
    {
        borough = b;
    }

    public ArrayList<CovidData> getRecords()
    {
        return records;
    }

    public void setRecords(ArrayList<CovidData> r)
    {
        records = r;
    }

    public String getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(String d)
    {
        fromDate = d;
    }

    public String getToDate()
    {
        return toDate;
    }

    public void setToDate(String d)
    {
        toDate = d;
    }

    public ArrayList<CovidData> getDateRecords(){
        ArrayList<CovidData> date_records = new ArrayList<CovidData>();

        String temp_from = this.fromDate.replaceAll("-", "");
        String temp_to = this.toDate.replaceAll("-", "");
        for (CovidData d : records) {
            String temp_date = d.getDate().replaceAll("-", "");
            if ((Integer.valueOf(temp_date) >= Integer.valueOf(temp_from)) && (Integer.valueOf(temp_date) <= Integer.valueOf(temp_to))) {
                date_records.add(d);
            }
        }
        return date_records;
    }

    public void setWelcomePage (WelcomePage page) //may not need this!!
    {
        welcomePage = page;
    }
    public WelcomePage getWelcomePage() //may not need this!!
    {
        return welcomePage;
    }


}
