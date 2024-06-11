package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class reserved extends AppCompatActivity {

    private ImageView carImageView;
    private TextView carNameTextView, carModelTextView, carPriceTextView, dateReservedTextView, timeReservedTextView;
    private Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved);
setupViews();


        reservationDetails();
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(reserved.this, "Done you Reserved The Car", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(reserved.this,MainActivity.class);//temp
                startActivity(intent);            }
        });
    }

    private void setupViews() {
        carImageView = findViewById(R.id.ImgView);
        carNameTextView = findViewById(R.id.carName);
        carModelTextView = findViewById(R.id.carModel);
        carPriceTextView = findViewById(R.id.carPrice);
        dateReservedTextView = findViewById(R.id.dateReserved);
        timeReservedTextView = findViewById(R.id.timeReserved);
        doneButton = findViewById(R.id.btnDone);
    }

    private void reservationDetails() {
        String url = "url"; // temp

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String carName = jsonObject.getString("car_name");
                            String carModel = jsonObject.getString("car_model");
                            double carPrice = jsonObject.getDouble("car_price");
                            String dateReserved = jsonObject.getString("date_reserved");
                            String timeReserved = jsonObject.getString("time_reserved");
                            String carImageUrl = jsonObject.getString("car_image_url");

                            // Set data to UI elements
                            carNameTextView.setText(carName);
                            carModelTextView.setText(carModel);
                            carPriceTextView.setText("Price: $" + carPrice);
                            dateReservedTextView.setText("Date: " + dateReserved);
                            timeReservedTextView.setText("Time: " + timeReserved);
                            Glide.with(reserved.this).load(carImageUrl).into(carImageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }
}
