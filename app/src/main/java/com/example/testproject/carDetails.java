package com.example.testproject;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testproject.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Date;

public class carDetails extends AppCompatActivity {
    private ImageView ImgView;
    private TextView dFactory, dType, dModel, dPrice;
    private Button dAddFav, dAddRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        setupViews();
        int carId = getIntent().getIntExtra("CAR_ID", -1);
        if (carId != -1) {
            getCarDetails(carId);
        }


    }

    private void getCarDetails(int carId) {
        String url = "http://yourserver.com/get_car_details.php?car_id=" + carId;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        dFactory.setText(jsonObject.getString("factory"));
                        dType.setText(jsonObject.getString("type"));
                        dModel.setText(jsonObject.getString("model"));
                        dPrice.setText(jsonObject.getString("price"));
                        Glide.with(this).load(jsonObject.getString("photo_url")).into(ImgView);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                    error.printStackTrace();
                });
    }

    private void setupViews() {
        ImgView= findViewById(R.id.ImgView);
        dFactory = findViewById(R.id.dFactory);
        dType = findViewById(R.id.dType);
        dModel = findViewById(R.id.dModel);
        dPrice = findViewById(R.id.dPrice);
        dAddFav = findViewById(R.id.dAddFav);
        dAddRes = findViewById(R.id.dAddRes);

    }}
