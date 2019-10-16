package com.example.car2go;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static android.provider.SettingsSlicesContract.KEY_LOCATION;

//import static com.example.car2go.retrieve.car_objectList;

public class retrieve extends AppCompatActivity {
    public static String URL_CAR_DATA = "http://172.20.10.2/android_login_api/car_data.php";
    //    RecyclerView recyclerView;
    //carAadapter aadapter;
    public static ListView listView;
    ArrayList<car_object> car_objectList = new ArrayList<>();
    CustomAdapter customAdapter;
    public static double lat;
    public static double longt;
    public static double x;
    public static   double y;
    public static String model_in_map;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    float[] results = new float[20];
   public String distance;
public static double lat_ofcar;
public static double longtt_ofcar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getDeviceLocation();
        listView = (ListView) findViewById(R.id.l_view);
        customAdapter = new CustomAdapter();
        System.out.println("oooooooooooooooooooooooooooooooooooooooooooooooo");
        loadCars();
        //loadCar();
        car_objectList = new ArrayList<car_object>();

    }private void getDeviceLocation() {
        Log.d("hh", "SSSSSSSSSSSSSSSSSSS ");

        /*                            System.out.print("SSSSSSSSSSSSSSSS"+lating.longitude);

         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            mLocationPermissionGranted =true;
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            lat=mLastKnownLocation.getLatitude();
                            longt=+mLastKnownLocation.getLongitude();
                        } else {
                            Log.d("hhh", "Current location is null. Using defaults.");
                        }
                    }
                });

            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * function to verify login details in mysql db
     */
    private void loadCars() {

        // Tag used to cancel the request
        String tag_string_req = "req_car";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CAR_DATA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("oufa", "Login Response: " + response.toString());


                try {
                    JSONArray cars = new JSONArray(response);
                    Log.i("oufa", response);
                    Log.i("oufa", "" + cars.length());

                    for (int i = 0; i < cars.length(); i++) {
                        JSONObject carObject = cars.getJSONObject(i);
                        String Model_Name = carObject.getString("Model_Name");
                        String Production_Year = carObject.getString("Production_Year");
                        String Latitude = carObject.getString("Latitude");
                        String Longitude = carObject.getString("Longitude");
                        String image = carObject.getString("image");
                        String fuel_Level = carObject.getString("Fuel_Level");
                        //x=Double.parseDouble(lat);
                       // y=Double.parseDouble(longt);
                        String ID = carObject.getString("ID");
                        Location.distanceBetween(lat,longt,Double.parseDouble(Latitude) ,Double.parseDouble(Longitude) , results);
                       // System.out.println(results[0]);
                       // Log.d(""+results[0], ""+results[0]);

                        String  distance="" +(results[0]);
                        System.out.println(distance);
                        car_object cars1 = new car_object(Model_Name, Production_Year, Latitude, Longitude, image, fuel_Level, ID,distance);
                        car_objectList.add(cars1);


                    }  Collections.sort(car_objectList, new Comparator<car_object>() {
                        @Override
                        public int compare(car_object o1, car_object o2) {
                            String a1=o1.getDistance()+"";
                            String a2=o2.getDistance()+"";
                           // o1.getDistance().compareTo(o2.getDistance());
                            return  a1.compareTo(a2);

                        }
                    });


                    listView.setAdapter(customAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
 lat_ofcar=Double.parseDouble(car_objectList.get(position).getLatitude()) ;
//x=Double.parseDouble(lat);
 longtt_ofcar=Double.parseDouble(car_objectList.get(position).getLongitude()) ;
//y=Double.parseDouble(longt);
model_in_map=car_objectList.get(position).getModel_Name();
                            Intent intent = new Intent(retrieve.this, MapsAct.class);
                            startActivity(intent);
                         //   System.out.println("SSSSSSS"+mylocation.MYlatitude);
                           // System.out.println("SSSSSSS"+mylocation.MY_longitude);
                        }
                    });

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RetrieveActivity", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
              /*  params.put("email", email);
                params.put("password", password);
*/
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }




    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return car_objectList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, null);
            //convertView = li.inflate(R.layout.your_xml_name,parent,false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            TextView textView1 = (TextView) convertView.findViewById(R.id.model);
            TextView textView2 = (TextView) convertView.findViewById(R.id.production_year);
            TextView textView3 = (TextView) convertView.findViewById(R.id.fuelLevel);
            TextView textView4 = (TextView) convertView.findViewById(R.id.distanceee);


textView4.setText(car_objectList.get(position).getFuel_Level());
            textView3.setText(car_objectList.get(position).getModel_Name());
            textView2.setText(car_objectList.get(position).getProduction_Year());
            textView1.setText(""+car_objectList.get(position).getDistance());
            String imagePath = "http://192.168.1.7/" + car_objectList.get(position).getImage();
            Picasso.get().load(imagePath).into(imageView);
            car_objectList.get(position);
            return convertView;
        }
    }
}