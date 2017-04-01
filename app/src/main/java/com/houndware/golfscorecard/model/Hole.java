package com.houndware.golfscorecard.model;

public class Hole {

    //Instance Variables:

    private String label;
    private int strokeCount;
    private int par;

    public Hole (String label, int strokeCount, int par) {
        this.label = label;
        this.strokeCount = strokeCount;
        this.par = par;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStrokeCount() {
        return strokeCount;
    }

    public void setStrokeCount(int strokeCount) {
        this.strokeCount = strokeCount;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }
}
