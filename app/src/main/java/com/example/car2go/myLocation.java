package com.example.car2go;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class myLocation extends AppCompatActivity {

    private static  final int REQUEST_LOCATION=1;

    Button getlocationBtn;
    TextView showLocationTxt;

    LocationManager locationManager;
    String latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("oufa", "uuuuuuuuuuuuuuu");

        System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        //Add permission
        System.out.println(latitude+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        System.out.println(longitude+"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        showLocationTxt=findViewById(R.id.show_location);
        getlocationBtn=findViewById(R.id.getLocation);


                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    //Write Function To enable gps
                    System.out.println(latitude+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
                    System.out.println(longitude+"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
                    OnGPS();
                }
                else
                {
                    //GPS is already On then
                    System.out.println(latitude+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
                    System.out.println(longitude+"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
                    getLocation();
                }



    }

    private void getLocation() {
        System.out.println(latitude+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        System.out.println(longitude+"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(myLocation.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myLocation.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            System.out.println(latitude+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
            System.out.println(longitude+"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
            if (LocationGps !=null)
            {System.out.println(latitude+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
                System.out.println(longitude+"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                System.out.println(latitude+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
                System.out.println(longitude+"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                System.out.println(latitude+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
                System.out.println(longitude+"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);

                System.out.println(latitude+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
                System.out.println(longitude+"UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");     }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }

    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }}
