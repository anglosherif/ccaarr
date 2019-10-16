package com.example.car2go;

public class Car {


    String[] model;
    String[] production_year;
    String[] fuel_level;
    String[] imagePaths;

    public Car(String[] model, String[] production_year, String[] fuel_level, String[] imagePaths) {
        this.model = model;
        this.production_year = production_year;
        this.fuel_level = fuel_level;
        this.imagePaths = imagePaths;
    }

    public String[] getModel() {
        return model;
    }

    public void setModel(String[] model) {
        this.model = model;
    }

    public String[] getProduction_year() {
        return production_year;
    }

    public void setProduction_year(String[] production_year) {
        this.production_year = production_year;
    }

    public String[] getFuel_level() {
        return fuel_level;
    }

    public void setFuel_level(String[] fuel_level) {
        this.fuel_level = fuel_level;
    }

    public String[] getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(String[] imagePaths) {
        this.imagePaths = imagePaths;
    }
}

