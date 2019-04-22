package edu.wpi.cs3733.d19.teamL.Reports;

public class PieChartData {

    private String serie;
    private double value;


    public PieChartData(String serie, double value) {
        super();
        this.serie = serie;
        this.value = value;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
