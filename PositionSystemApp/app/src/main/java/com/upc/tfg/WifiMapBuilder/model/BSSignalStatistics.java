package com.upc.tfg.WifiMapBuilder.model;

public interface BSSignalStatistics {
    BSSignalStatistics getInstance();

    double getMean();
    void setMean(double mean);
    double getVar();
    void setVar(double var);
    double getQuality();
    void setQuality(double quality);
    String getName();
    void setName(String name);
    String getAddress();
    void setAddress(String address);
}
