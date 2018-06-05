package com.example.garci.positionsystemapp.model;

public class Parameters {
    private double period;
    private double rep;
    private double temp;
    private int numSample;

    public Parameters(double period, double rep, double temp, int numSample) {
        this.period = period;
        this.rep = rep;
        this.temp = temp;
        this.numSample = numSample;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }

    public double getRep() {
        return rep;
    }

    public void setRep(double rep) {
        this.rep = rep;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getNumSample() {
        return numSample;
    }

    public void setNumSample(int numSample) {
        this.numSample = numSample;
    }
}
