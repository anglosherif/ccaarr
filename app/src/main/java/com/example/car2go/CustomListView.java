package com.example.car2go;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class CustomListView extends ArrayAdapter<String> {
    String[] model;
    String[] production_year;
    String[] fuel_level;
    String[] imagePaths;
    private Activity context;
    Bitmap bitmap;

    public CustomListView(Activity context, String[] model, String[] fuel_level, String[] production_year, String[] imagePaths) {
        super(context, R.layout.layout, model);
//        super(context, R.layout.layout,model);
        this.context = context;
        this.model = model;
        this.production_year = production_year;
        this.fuel_level = fuel_level;
        this.imagePaths = imagePaths;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.layout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }


        viewHolder.txtv1.setText(model[position]);
        viewHolder.txtv2.setText(fuel_level[position]);
        viewHolder.txtv3.setText(production_year[position]);
        new GetImageFromURL(viewHolder.imgv).execute(imagePaths[position]);
        return r;
    }

    class ViewHolder {
        TextView txtv1;
        TextView txtv2;
        TextView txtv3;
        ImageView imgv;

        ViewHolder(View v) {
            txtv1 = (TextView) v.findViewById(R.id.model);
            txtv3 = (TextView) v.findViewById(R.id.production_year);

            txtv2 = (TextView) v.findViewById(R.id.fuelLevel);
            imgv = (ImageView) v.findViewById(R.id.imageView);


        }

    }

    public class GetImageFromURL extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public GetImageFromURL(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urlDisplay = url[0];
            bitmap = null;
            try {
                InputStream ist = new java.net.URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(ist);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecut(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
