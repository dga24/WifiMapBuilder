package com.example.garci.positionsystemapp.model;

public class Posicion {
    double x;
    double y;
    double z;
    double pixelX;
    double pixelY;

    public Posicion(double x, double y, double z, double pixelX, double pixelY) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pixelX = pixelX;
        this.pixelY = pixelY;
    }

    public double getX() {        return x;
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

    public double getPixelX() {
        return pixelX;
    }

    public void setPixelX(double pixelX) {
        this.pixelX = pixelX;
    }

    public double getPixelY() {
        return pixelY;
    }

    public void setPixelY(double pixelY) {
        this.pixelY = pixelY;
    }
}
