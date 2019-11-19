package com.example.car2go;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class details extends AppCompatActivity {
public Button Navigate;
public Button Send_Review;
public TextView model_in_details;
public EditText review;
public Button Show_reviews;
private ProgressDialog pDialog;
private SessionManager session;
private SQLiteHandler db;
private String rev;
private String carid;
private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
Navigate=  (Button)(findViewById(R.id.navigate));
Send_Review=(Button)(findViewById(R.id.review));
db=new SQLiteHandler(getApplicationContext());
final HashMap<String,String> user=db.getUserDetails();
userid=user.get("uid");
review=(EditText)(findViewById(R.id.editText2));
        carid= retrieve.ID.toString().trim();

model_in_details=(TextView)(findViewById(R.id.textView));
Show_reviews=(Button)(findViewById(R.id.show_review));
//model_in_details=retrieve.model_in_map;
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());
Navigate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+retrieve.lat_ofcar+","+retrieve.longtt_ofcar + "&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
});
Show_reviews.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                reviews.class);
        i.putExtra("CarId",carid);
        startActivity(i);
    }
});
        Send_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rev = review.getText().toString().trim();
                userid= userid.trim();
                carid= retrieve.ID.toString().trim();
                System.out.println(rev);
                System.out.println(userid);
                System.out.println(carid);
                saveReview(rev,userid,carid);
                // String nationality=inputNationality.getText().toString().trim();
                if (!rev.isEmpty()){
                    //saveReview(rev,userid,carid);

                }else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }

        );


    }

    private void saveReview(final String rev, final String userid, final String carid) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REVIEW_FROMDB, new Response.Listener<String>() {
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Review ADDED", Toast.LENGTH_LONG).show();
                Log.d("", "review Response: " + response.toString());
                hideDialog();

                /*try {
                    JSONObject jObj = new JSONObject(response);

                    }catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }


        }, new Response.ErrorListener(){ @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("", "Review Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(),
                    error.getMessage(), Toast.LENGTH_LONG).show();
           hideDialog();
        }
        }



        ){@Override
            protected Map<String, String> getParams() {
                // Posting params to register url, mmn
                Map<String, String> params = new HashMap<String, String>();
                params.put("review", rev);
                params.put("user_id", userid);
                params.put("car_id", carid);


                return params;
            }};
        AppController.getInstance().addToRequestQueue(strReq,"");


    } private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
