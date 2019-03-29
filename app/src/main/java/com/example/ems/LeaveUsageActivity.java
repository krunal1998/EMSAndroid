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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ems.Adapter.LeaveUsageAdapter;
import com.example.ems.models.ConsumedLeave;
import com.example.ems.models.JobTitle;
import com.example.ems.models.Leave;
import com.example.ems.models.LeaveAllocation;
import com.example.ems.models.LeaveType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class LeaveUsageActivity extends MainActivity {

    public List<Leave> leaves = new ArrayList<>();
    public List<LeaveType> leavetypes = new ArrayList<>();
    public List<LeaveAllocation> leaveallocations = new ArrayList<>();
    public List<ConsumedLeave> consumedleaves = new ArrayList<>();

    public static final String baseurl = "http://emp.navkarsoftware.com/api/";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;
    public ProgressDialog progressBar;
    public static RequestQueue queue;
    String employeeId;
    int jobtitleid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Leave Usage Report");
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_leave_usage, null, false);
        mDrawerLayout.addView(contentView, 0);

        queue = Volley.newRequestQueue(this);

        recyclerView= findViewById(R.id.LeaveUsageRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        showprogressbar();

        loadData();

    }

    private void loadData() {
        employeeId="18ymp12";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"leave/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Log.d("In response", "In response");
                        Gson gson = new GsonBuilder().create();
                        leaves.addAll(Arrays.asList(gson.fromJson(response, Leave[].class)));

                        List<Leave> removelist = new ArrayList<>();
                        for(Leave l: leaves)     {
                            //Log.d("In loop :", h.HolidayDate.substring(1,4));
                            //if leave record is not of logged in employee then add in remove list
                            if(! l.EmployeeId.equals(employeeId))
                                removelist.add(l);
                        }
                        //remove all leave record which are not of logged in employee
                        leaves.removeAll(removelist);

                        loadAllocatedLeaveData();

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

    private void loadAllocatedLeaveData()
    {
        jobtitleid=1;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"LeaveAllocation/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Log.d("In response", "In response");
                        Gson gson = new GsonBuilder().create();
                        leaveallocations.addAll(Arrays.asList(gson.fromJson(response, LeaveAllocation[].class)));

                        List<LeaveAllocation> removelist = new ArrayList<>();
                        for(LeaveAllocation l: leaveallocations)     {
                            //Log.d("In loop :", h.HolidayDate.substring(1,4));
                            //if record is not of logged in employee's jobtitle then add in remove list
                            if(l.JobTitleId != jobtitleid)
                                removelist.add(l);
                        }
                        //remove all leave record which are not of logged in employee
                        leaveallocations.removeAll(removelist);

                        loadConsumedLeaveData();

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

    private void loadConsumedLeaveData()
    {
        employeeId="18ymp12";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"EmployeeLeaves/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Log.d("In response", "In response");
                        Gson gson = new GsonBuilder().create();
                        consumedleaves.addAll(Arrays.asList(gson.fromJson(response, ConsumedLeave[].class)));

                        List<ConsumedLeave> removelist = new ArrayList<>();
                        for(ConsumedLeave l: consumedleaves)     {
                            //Log.d("In loop :", h.HolidayDate.substring(1,4));
                            //if record is not of logged in employee's then add in remove list
                            if(! l.EmployeeId.equals(employeeId))
                                removelist.add(l);
                        }
                        //remove all leave record which are not of logged in employee
                        consumedleaves.removeAll(removelist);

                        loadLeaveTypeData();

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

    private void loadLeaveTypeData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"LeaveType/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Log.d("In response", "In response");
                        Gson gson = new GsonBuilder().create();
                        leavetypes.addAll(Arrays.asList(gson.fromJson(response, LeaveType[].class)));

                        List<String> leaveTypeslist =new ArrayList<>();
                        List<Integer> entitlementslist =new ArrayList<>();
                        List<Integer> pendingleaveslist =new ArrayList<>();
                        List<Integer> scheduledleavelist =new ArrayList<>();
                        List<Integer> consumedleavelist =new ArrayList<>();
                        List<Integer> leavebalancelist =new ArrayList<>();

                        for(LeaveType lt: leavetypes){
                            leaveTypeslist.add(lt.LeaveTypeName);

                            int consumed=0;int pending=0;int scheduled=0; int used=0;int allocated=0;int balance=0;

                            for(LeaveAllocation la: leaveallocations){
                                if(lt.LeaveTypeId==la.LeaveTypeId){
                                    allocated=la.NumberOfLeave;
                                    break;
                                }
                            }

                            for(ConsumedLeave cl: consumedleaves){
                                if(cl.LeaveTypeId==lt.LeaveTypeId){
                                    used=(int) cl.NumberOfLeaves;
                                    break;
                                }
                            }

                            for(Leave leave:leaves){
                                if(leave.LeavetypeId==lt.LeaveTypeId){
                                    if(leave.LeaveStatus.equals("Pending")){
                                        pending = pending +(int) leave.NumberOfDays;
                                    }
                                    if (leave.LeaveStatus.equals("Approved")){
                                        scheduled = scheduled + (int) leave.NumberOfDays;
                                    }
                                    if(leave.LeaveStatus.equals("Consumed")){
                                        consumed = consumed + (int) leave.NumberOfDays;
                                    }
                                }
                            }
                            balance= allocated-used;

                            entitlementslist.add(allocated);
                            leavebalancelist.add(balance);
                            pendingleaveslist.add(pending);
                            scheduledleavelist.add(scheduled);
                            consumedleavelist.add(consumed);

                        }

                        adapter = new LeaveUsageAdapter(getApplicationContext(),leaveTypeslist,entitlementslist,pendingleaveslist,scheduledleavelist,leavebalancelist,consumedleavelist);
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
        progressBar.setMessage("Getting Data..");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();//displays the progress bar
    }

    private void hideprogressbar() {
        progressBar.hide();
    }

}
