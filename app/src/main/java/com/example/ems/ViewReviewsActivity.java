package com.example.ems;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class ViewReviewsActivity extends AppCompatActivity {
    public List<GeneratedReview> glist = new ArrayList<>();
    public List<Review> reviews = new ArrayList<>() ;

    public Employee e;

    public int n;
    public static final String baseurl = "http://emp.navkarsoftware.com/api/";

    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;

    private RecyclerView.Adapter adapter;

    public ProgressDialog progressBar;

    public static RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        queue = Volley.newRequestQueue(this);
        /*Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
             e = (Employee) bundle.getSerializable("employee");
        }*/


        e = new Employee();
        e.EmployeeId="18ymp12";

        showprogressbar();

        recyclerView = findViewById(R.id.reviewRecyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        queue = Volley.newRequestQueue(getApplicationContext());
        getreviews();

        getgeneratedreviews();


    }

    private void showprogressbar() {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Getting your reviews..");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressBar.setProgress(0);//initially progress is 0
        //progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar
    }

    public void getgeneratedreviews() {
        //RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"generatedreviews/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int n = 0;
                        Gson gson = new GsonBuilder().create();
                        glist.addAll(Arrays.asList(gson.fromJson(response, GeneratedReview[].class)));
                       // Log.d("TAG",glist.size()+" asdfg");
                        List<GeneratedReview> removelist = new ArrayList<>();
                        for(GeneratedReview g: glist)     {
                            if(g.Status.equals("Generated")) {
                               removelist.add(g);
                                // glist.remove(g);
                            }
                            else if(!g.EmployeeId.equals(e.EmployeeId)) {
                                removelist.add(g);
                                //glist.remove(g);
                            }
                            else {

                                for(Review r : reviews)     {
                                    if(r.GenerateReviewId == g.GenerateReviewId) {
                                        PerformanceParameter p = getParameter(r.PerameterId);
                                        Log.d("TAGgetavgratingpara",r.PerameterId+"");
                                        double rating =(double) r.Rating/(double)p.MaxRating;
                                        Log.d("rating",rating + "");
                                        g.AverageRating +=rating;
                                        Log.d("Avg rating", g.AverageRating+"");
                                        n++;

                                    }
                                }
                                g.AverageRating = (g.AverageRating/n)*5;
                            }
                        }
                        glist.removeAll(removelist);





                        adapter = new ReviewAdapter(getApplicationContext(),glist);
                        recyclerView.setAdapter(adapter);
                        hideprogressbar();

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
        queue.add(stringRequest);
    }

    public void getreviews() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"reviews/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new GsonBuilder().create();
                        reviews.addAll(Arrays.asList(gson.fromJson(response, Review[].class)));
                        // Log.d("TAG",glist.size()+" asdfg");
                        List<Review> removelist = new ArrayList<>();
                        for(Review r : reviews)     {

                            if(!r.EmployeeId.equals(e.EmployeeId)) {
                                removelist.add(r);
                                //glist.remove(g);
                            }
                            else {

                            }
                        }
                        reviews.removeAll(removelist);

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
        queue.add(stringRequest);
    }

    private void hideprogressbar() {
        progressBar.hide();
        Log.d("TAGpbh","Glist count: " + glist.size()+ "glist avg rating " + glist.get(0).AverageRating);
    }

    private double getAverageRating(final int generateReviewId) {
        final double  avg[] = {0.0};
        n=0;
     //   RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"reviews/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson =new Gson();
                        reviews.addAll(Arrays.asList(gson.fromJson(response, Review[].class)));
                        Log.d("TAGgetavgrating",reviews.size()+"");
                        for(Review r : reviews)     {
                            if(r.GenerateReviewId == generateReviewId) {
                                PerformanceParameter p = getParameter(r.PerameterId);
                                Log.d("TAGgetavgratingpara",r.PerameterId+"");
                                double rating =(double) r.Rating/(double)p.MaxRating;
                                Log.d("rating",rating + "");
                                avg[0] +=rating;
                                Log.d("Avg rating", avg[0]+"");
                                n++;

                            }
                        }
                        avg[0]= avg[0]/n;

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAGgetAverageRating",error.getMessage());

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

        queue.add(stringRequest);
        //Return average rating out of 5
        Log.d("return avg", avg[0]*5.0 + "");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        return avg[0]*5.0;
    }

    private PerformanceParameter getParameter(int parameterId) {
       /* RequestQueue queue = Volley.newRequestQueue(this);
        final PerformanceParameter[] performanceParameter = {null};

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"parameters/"+parameterId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        performanceParameter[0] = gson.fromJson(response, PerformanceParameter.class);
                        Log.d("TAGgetParameter",performanceParameter[0].ParameterName);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAGgetParameter",error.getMessage());

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

        queue.add(stringRequest);
        if (performanceParameter[0]==null)
            return new PerformanceParameter();
        else
            return performanceParameter[0];*/
        PerformanceParameter p = new PerformanceParameter();
        p.MaxRating = 5;


        return p;
    }

}
