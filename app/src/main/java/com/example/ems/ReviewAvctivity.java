package com.example.ems;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ems.models.GeneratedReview;
import com.example.ems.models.Review;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewAvctivity extends AppCompatActivity {

    public List<Review>reviews = new ArrayList<>();
    public GeneratedReview generatedReview;
    public static final String baseurl = "http://emp.navkarsoftware.com/api/";
    public RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_avctivity);
        generatedReview = (GeneratedReview) getIntent().getSerializableExtra("GR");
        TextView textView = findViewById(R.id.textView4);
        textView.setText("Due Date:" + generatedReview.DueDate);

        queue = Volley.newRequestQueue(this);
        getReviews();


    }

    public void getReviews() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"reviews/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new GsonBuilder().create();
                        reviews.addAll(Arrays.asList(gson.fromJson(response, Review[].class)));
                        List<Review> removelist = new ArrayList<>();
                        for(Review r : reviews)     {

                            if(r.GenerateReviewId != generatedReview.GenerateReviewId) {
                                removelist.add(r);
                            }
                            else {

                            }
                        }
                        reviews.removeAll(removelist);
                        loadReviews();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAGgetgeneratedreviews",error.toString());

                    }
                });

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        stringRequest.setShouldCache(false);
        queue.getCache().clear();
        queue.add(stringRequest);
    }

    private void loadReviews() {


    }

}
