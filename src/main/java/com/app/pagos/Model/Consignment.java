package com.app.pagos.Model;

public class Consignment {

    private int id_consignment;
    private double value;
    private String date;
    private int id_aptm;

    public Consignment(int id_consignment, double value, String date, int id_aptm) {
        this.id_consignment = id_consignment;
        this.value = value;
        this.date = date;
        this.id_aptm = id_aptm;
    }

    public int getId_consignment() {
        return id_consignment;
    }

    public void setId_consignment(int id_consignment) {
        this.id_consignment = id_consignment;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId_aptm() {
        return id_aptm;
    }

    public void setId_aptm(int id_aptm) {
        this.id_aptm = id_aptm;
    }

    @Override
    public String toString() {
        return id_consignment +
                "," + value +
                "," + date +
                "," + id_aptm;
    }
}
