package org.example;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StatisticsPanelTest {
    StatisticsPanel statisticsPanel = new StatisticsPanel();
    ArrayList<CovidData> data = new ArrayList<CovidData>();
    String result;
    
    private void addToData()
    {
        data.add(new CovidData("2023-01-01", "Borough1", 5, 10, 15, 20, 25, 30, 100, 200, 5, 10));
        data.add(new CovidData("2023-01-01", "Borough2", 8, 17, 23, 47, 57, 60, 100, 220, 9, 20));
        data.add(new CovidData("2023-01-02", "Borough3", 8, 12, 18, 22, 28, 32, 120, 210, 87, 15));
    }
    
    @Test
    public void testRetailStat() {
        addToData();
        result = statisticsPanel.computeStatistic("Average Retail/ Recreation GMR", data);
        assertEquals("AVERAGE RETAIL/ RECREATION GMR:" + "\n" + "\n" + 7.0, result);
    }
    
    @Test
    public void testGroceryStat()
    {
        addToData();
        result = statisticsPanel.computeStatistic("Average Grocery/ Pharmacy GMR", data);
        assertEquals("AVERAGE GROCERY/ PHARMACY GMR:"+ "\n" + "\n" + 13.0, result);
    }
    
    @Test
    public void testDeathStat()
    {
        addToData();
        result = statisticsPanel.computeStatistic("Total number of (total) deaths", data);
        assertEquals("TOTAL DEATHS:"+ "\n" + "\n" + 45, result);
    }
    
    @Test
    public void testCasesStat()
    {
        addToData();
        result = statisticsPanel.computeStatistic("Average of total cases", data);
        assertEquals("AVERAGE TOTAL CASES:"+ "\n" + "\n" + 210.0, result);
    }
    
    @Test
    public void testUnknownStat()
    {
        addToData();
        result = statisticsPanel.computeStatistic("Unknown Statistic", data);
        assertEquals("Statistic not implemented", result);
    }
    
    @Test
    public void testEmptyStat()
    {
        data.clear();
        result = statisticsPanel.computeStatistic("", data);
        assertEquals("Data not loaded", result);
    }
}