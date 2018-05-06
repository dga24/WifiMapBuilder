package com.example.garci.positionsystemapp.model;

public class Coordenada {

    int coordenadaid;
    double x;
    double y;
    double z;


    public Coordenada(int coordenadaid, double x, double y, double z) {
        this.coordenadaid = coordenadaid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getCoordenadaid() {
        return coordenadaid;
    }

    public void setCoordenadaid(int coordenadaid) {
        this.coordenadaid = coordenadaid;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
