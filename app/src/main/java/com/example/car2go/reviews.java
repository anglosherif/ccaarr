package com.example.car2go;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class reviews extends AppCompatActivity {
    ArrayList<String> reviwList = new ArrayList<>();
    CustomAdapter customAdapter;
    ListView listView;
    String carid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        listView = (ListView) findViewById(R.id.reviewlist);
        customAdapter = new CustomAdapter();
        reviwList = new ArrayList<String>();
        carid=getIntent().getStringExtra("CarId");
        loadcomments();
    }

    private void loadcomments() {

        // Tag used to cancel the request
        String tag_string_req = "com_car";


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REVIEW_FROMDB, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("oufa", "Comments Response: " + response.toString());


                try {
                    JSONArray comments = new JSONArray(response);
                    Log.i("oufa", response);
                    Log.i("oufa", "" + comments.length());

                    for (int i = 0; i < comments.length(); i++) {
                        JSONObject commentObject = comments.getJSONObject(i);
                        String getreview = commentObject.getString("review");
                        reviwList.add(getreview);
                    }
                    listView.setAdapter(customAdapter);

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RetrieveActivity", "Comment Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to log
                //in url
                Map<String, String> params = new HashMap<String, String>();
                params.put("carid",carid);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reviwList.size();
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutcomment, null);
            TextView textView1 = (TextView) convertView.findViewById(R.id.commentTextview);
            textView1.setText(reviwList.get(position));
            return convertView;
        }
    }
}
