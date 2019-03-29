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
import com.example.ems.Adapter.ViewAttendanceAdapter;
import com.example.ems.models.Attendance;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAttendanceActivity extends MainActivity implements AdapterView.OnItemSelectedListener {

    public List<Attendance> attendances = new ArrayList<>();
    public static final String baseurl = "http://emp.navkarsoftware.com/api/";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;
    public ProgressDialog progressBar;
    public static RequestQueue queue;
    String employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        employeeId="18ymp12";
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("My Attendance");

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_view_attendance, null, false);
        mDrawerLayout.addView(contentView, 0);

        queue = Volley.newRequestQueue(this);

        recyclerView= findViewById(R.id.AttendanceRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        showprogressbar();

        //current year
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month= Calendar.getInstance().get(Calendar.MONTH);
        // Spinner element
        Spinner yearspinner = (Spinner) findViewById(R.id.AttendanceYearSpinner);
        Spinner monthspinner = (Spinner) findViewById(R.id.AttendanceMonthSpinner);
        // Spinner click listener
        yearspinner.setOnItemSelectedListener(this);
        monthspinner.setOnItemSelectedListener(this);

        // Year Spinner Drop down elements
        List<String> years = new ArrayList<>();
        years.add((year-1)+"");
        years.add(year + "");
        years.add((year+1) + "");
        years.add((year+2)+"");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner

        yearspinner.setAdapter(dataAdapter);
        //set default selection for current year
        yearspinner.setSelection(1);

        // Month Spinner Drop down elements
        List<String> months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        // Creating adapter for spinner
        ArrayAdapter<String> monthdataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        monthspinner.setAdapter(monthdataAdapter);
        //set default selection for current year
        monthspinner.setSelection(month);

        loadAttendance();


    }

    private void loadAttendance() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"attendance/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       // Log.d("In response", "In response");
                        Gson gson = new GsonBuilder().create();
                        attendances.addAll(Arrays.asList(gson.fromJson(response, Attendance[].class)));
                       // Log.d("Attendance list size",attendances.size()+"");
                        //current year and month
                        String currentyear = Calendar.getInstance().get(Calendar.YEAR)+"";
                        Spinner monthspinner = (Spinner) findViewById(R.id.AttendanceMonthSpinner);
                        String month = monthspinner.getSelectedItem().toString();
                        String m_number = getMonthNumber(month);

                       // Log.d("year", currentyear);
                       // Log.d("Month",m_number);

                        List<Attendance> currentlist = new ArrayList<>();

                        for(Attendance a: attendances)     {
                            //Log.d("In loop :", h.HolidayDate.substring(1,4));
                            if(a.PunchInDate.substring(0,4).equals(currentyear) && a.PunchInDate.substring(5,7).equals(m_number) && a.EmployeeId.equals(employeeId))
                                currentlist.add(a);
                        }

                        adapter = new ViewAttendanceAdapter(getApplicationContext(),currentlist);
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
        progressBar.setMessage("Getting Attendance..");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();//displays the progress bar
    }

    private void hideprogressbar() {
        progressBar.hide();
        //Log.d("In hide progressbar", " in progressbar");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //get year spinner
        Spinner yearspinner = (Spinner) findViewById(R.id.AttendanceYearSpinner);
        Spinner monthspinner = (Spinner) findViewById(R.id.AttendanceMonthSpinner);
        // get selected spinner item
        String year= yearspinner.getSelectedItem().toString();
        String month = monthspinner.getSelectedItem().toString();

        String m_number = getMonthNumber(month);

        List<Attendance> currentAttendanceList = new ArrayList<>();
        for (Attendance a: attendances)
        {
            if(a.PunchInDate.substring(0,4).equals(year) && a.PunchInDate.substring(5,7).equals(m_number) && a.EmployeeId.equals(employeeId) )
                currentAttendanceList.add(a);
        }
        adapter = new ViewAttendanceAdapter(getApplicationContext(),currentAttendanceList);
        recyclerView.setAdapter(adapter);

    }

    private String getMonthNumber(String month) {
        Map<String, String> monthmap= new HashMap<>();
        monthmap.put("January","01");
        monthmap.put("February","02");
        monthmap.put("March","03");
        monthmap.put("April","04");
        monthmap.put("May","05");
        monthmap.put("June","06");
        monthmap.put("July","07");
        monthmap.put("August","08");
        monthmap.put("September","09");
        monthmap.put("October","10");
        monthmap.put("November","11");
        monthmap.put("December","12");


        return monthmap.get(month);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
