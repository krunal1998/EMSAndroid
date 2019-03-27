package com.example.ems;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ems.Adapter.HolidayAdapter;
import com.example.ems.models.Holiday;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HolidayActivity extends MainActivity implements AdapterView.OnItemSelectedListener {

    public List<Holiday> holidays = new ArrayList<>();
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
        //setContentView(R.layout.activity_holiday);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_holiday, null, false);
        mDrawerLayout.addView(contentView, 0);

        queue = Volley.newRequestQueue(this);



        recyclerView = findViewById(R.id.holidayRecyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        showprogressbar();
        //current year
        int year = Calendar.getInstance().get(Calendar.YEAR);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.holidayYearSpinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add((year-1)+"");
        categories.add(year + "");
        categories.add((year+1) + "");
        categories.add((year+2)+"");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        //set default selection for current year
        spinner.setSelection(1);


        loadHolidays();

    }

    private void loadHolidays() {
        //RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"holiday/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("In response", "In response");
                        int n = 0;
                        Gson gson = new GsonBuilder().create();
                        holidays.addAll(Arrays.asList(gson.fromJson(response, Holiday[].class)));
                        // Log.d("TAG",glist.size()+" asdfg");
                        List<Holiday> currentlist = new ArrayList<>();
                        //current year
                        String currentyear = Calendar.getInstance().get(Calendar.YEAR)+"";
                        //Log.d("year", currentyear);
                        for(Holiday h: holidays)     {
                            //Log.d("In loop :", h.HolidayDate.substring(1,4));
                            if(h.HolidayDate.substring(0,4).equals(currentyear)) {
                               // Log.d("In if :", h.HolidayDate);
                                currentlist.add(h);
                            }
                        }

                        adapter = new HolidayAdapter(getApplicationContext(),currentlist);
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

    private void showprogressbar() {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Getting Holidays..");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressBar.setProgress(0);//initially progress is 0
        //progressBar.setMax(100);//sets the maximum value 100
        progressBar.show();//displays the progress bar
    }

    private void hideprogressbar() {
        progressBar.hide();
        //Log.d("In hide progressbar", " in progressbar");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(position).toString();
        List<Holiday> currentlist = new ArrayList<>();
        for(Holiday h: holidays)     {
            //Log.d("In loop :", h.HolidayDate.substring(1,4));
            if(h.HolidayDate.substring(0,4).equals(item)) {
                // Log.d("In if :", h.HolidayDate);
                currentlist.add(h);
            }
        }

        adapter = new HolidayAdapter(getApplicationContext(),currentlist);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
