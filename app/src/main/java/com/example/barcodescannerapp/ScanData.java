package com.example.barcodescannerapp;

public class ScanData {

    private int id;
    private String data;
    private String dateOfScan;

    public ScanData(int id, String data, String dateOfScan) {
        this.id = id;
        this.data = data;
        this.dateOfScan = dateOfScan;
    }

    @Override
    public String toString() {
        return "ScanData{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", dateOfScan=" + dateOfScan +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDateOfScan() {
        return dateOfScan;
    }

    public void setDateOfScan(String dateOfScan) {
        this.dateOfScan = dateOfScan;
    }
}
