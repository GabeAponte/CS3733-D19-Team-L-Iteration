package edu.wpi.cs3733.d19.teamL.Reports;

public class BarGraphChartData {

    private String serie;
    private String category;
    private double value;


    public BarGraphChartData(String serie, String category, double value) {
        super();
        this.serie = serie;
        this.category = category;
        this.value = value;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

