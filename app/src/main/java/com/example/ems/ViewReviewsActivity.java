package com.example.ems;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ems.Adapter.ReviewAdapter;
import com.example.ems.models.Employee;
import com.example.ems.models.GeneratedReview;
import com.example.ems.models.PerformanceParameter;
import com.example.ems.models.Review;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class ViewReviewsActivity extends AppCompatActivity {
    public List<GeneratedReview> glist = new ArrayList<GeneratedReview>();
    public Employee e;

    public int n;
    public PerformanceParameter performanceParameter;
    public static final String baseurl = "http://192.168.0.109:62255/api/";

    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;

    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        /*Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
             e = (Employee) bundle.getSerializable("employee");
        }*/

        e = new Employee();
        e.EmployeeId="18ymp12";


        getgeneratedreviews();

        recyclerView = findViewById(R.id.reviewRecyclerView);
        adapter = new ReviewAdapter(getApplicationContext(),glist);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

    }
    public void getgeneratedreviews() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"generatedreviews/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-ddT'HH:MM:SS'").create();
                        glist = Arrays.asList(gson.fromJson(response, GeneratedReview[].class));
                        Log.d("TAG",glist.size()+" asdfg");
                        for(GeneratedReview g : glist)     {
                        //    if(g.Status.equals("Generated")) {
                           //     glist.remove(g);
                            //}
                       //     if(!g.EmployeeId.equals(e.EmployeeId)) {
                         //       glist.remove(g);
                           // }
                            g.AverageRating = getAverageRating(g.GenerateReviewId);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG",error.getMessage());

                    }
                });
        queue.add(stringRequest);
    }

    private double getAverageRating(final int generateReviewId) {
        final double  avg[] = {0.0};
        n=0;
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"reviews/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-ddT'HH:MM:SS'").create();
                        List<Review> reviews = Arrays.asList(gson.fromJson(response, Review[].class));
                        for(Review r : reviews)     {
                            if(r.GenerateReviewId == generateReviewId) {
                                PerformanceParameter p = getParameter(r.ParameterId);
                                double rating = r.Rating/p.MaxRating;
                                avg[0] +=rating;
                                n++;

                            }
                        }
                        avg[0]= avg[0]/n;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG",error.getMessage());

                    }
                });
        queue.add(stringRequest);
        //Return average rating out of 5
        return avg[0]*5.0;
    }

    private PerformanceParameter getParameter(int parameterId) {
        RequestQueue queue = Volley.newRequestQueue(this);
        performanceParameter = null;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"parameters/"+parameterId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        performanceParameter = (gson.fromJson(response, PerformanceParameter.class));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG",error.getMessage());

                    }
                });
        queue.add(stringRequest);
        return performanceParameter;
    }

}
