package com.example.car2go;

public class car_object {
    private String Model_Name;
    private String Production_Year;
    private String Latitude;
    private String Longitude;
    private String image;
    private String fuel_Level;
    private String ID;
    private  String distance;
    public  car_object(String Model_Name,String Production_Year,String Latitude,String Longitude,String image,String fuel_Level,String ID,String distance){
        this.Model_Name=Model_Name;
        this.Production_Year=Production_Year;
        this.Latitude=Latitude;
        this.Longitude=Longitude;
        this.image=image;
        this.fuel_Level=fuel_Level;
        this.ID=ID;
        this.distance=distance;

    }

    public  String getModel_Name() {
        return Model_Name;
    }

    public String getProduction_Year() {
        return Production_Year;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getImage() {
        return image;
    }
    public String getFuel_Level() {
        return fuel_Level;
    }

    public String getID() {
        return ID;
    }

    public String getDistance(){return distance;}


}
