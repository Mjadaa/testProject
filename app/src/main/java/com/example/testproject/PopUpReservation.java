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

public class PopUpReservation extends AppCompatActivity {

    private ImageView carImageView;
    private TextView carNameTextView, carPriceTextView;
    private Button reserveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_reservation);

       setUpViews();

        Intent intent = getIntent();
        String factory = intent.getStringExtra("factory");
        String type = intent.getStringExtra("type");
        String img = intent.getStringExtra("img");
        String model = intent.getStringExtra("model");
        double price = intent.getDoubleExtra("price", 0);
        String email = intent.getStringExtra("email");
        String VIN = intent.getStringExtra("VIN");

        carNameTextView.setText(factory + " " + model + " " + type);
        carPriceTextView.setText("Price: $" + price);
        Glide.with(this).load(img).into(carImageView);

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserveCar(VIN, email);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity
            }
        });
    }

    private void setUpViews() {
        carImageView = findViewById(R.id.imageView6);
        carNameTextView = findViewById(R.id.textCarName);
        carPriceTextView = findViewById(R.id.textPrice);
        reserveButton = findViewById(R.id.button4Reserve);
        cancelButton = findViewById(R.id.button5Cancel);
    }

    private void reserveCar(String VIN, String email) {
        String url = "the ur" + VIN + "&email=" + email;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(PopUpReservation.this,reserved.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(PopUpReservation.this, "Try Again", Toast.LENGTH_SHORT).show();

                            }
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
