package com.example.ems;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ems.Adapter.ReviewAdapter;
import com.example.ems.models.GeneratedReview;
import com.example.ems.models.PerformanceParameter;
import com.example.ems.models.Review;
import com.example.ems.models.Weekday;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeekdayActivity extends MainActivity {

    public static List<Weekday> weekdays = new ArrayList<>();
    public static final String baseurl = "http://emp.navkarsoftware.com/api/";
    public static RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_weekday);
        getSupportActionBar().setTitle("Week Days");

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_weekday, null, false);
        mDrawerLayout.addView(contentView, 0);

        queue = Volley.newRequestQueue(this);
        LoadWeekdays();


    }

    public void LoadWeekdays()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseurl+"weekday/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new GsonBuilder().create();
                        weekdays.clear();
                        weekdays.addAll(Arrays.asList(gson.fromJson(response, Weekday[].class)));
                        //Log.d("Size",weekdays.size()+"");
                        TextView WeekDay1 = (TextView) findViewById(R.id.WeekName1);
                        TextView WeekDay2 = (TextView) findViewById(R.id.WeekName2);
                        TextView WeekDay3 = (TextView) findViewById(R.id.WeekName3);
                        TextView WeekDay4 = (TextView) findViewById(R.id.WeekName4);
                        TextView WeekDay5 = (TextView) findViewById(R.id.WeekName5);
                        TextView WeekDay6 = (TextView) findViewById(R.id.WeekName6);
                        TextView WeekDay7 = (TextView) findViewById(R.id.WeekName7);

                        TextView WeekDuration1 = (TextView) findViewById(R.id.WeekDuration1);
                        TextView WeekDuration2 = (TextView) findViewById(R.id.WeekDuration2);
                        TextView WeekDuration3 = (TextView) findViewById(R.id.WeekDuration3);
                        TextView WeekDuration4 = (TextView) findViewById(R.id.WeekDuration4);
                        TextView WeekDuration5 = (TextView) findViewById(R.id.WeekDuration5);
                        TextView WeekDuration6 = (TextView) findViewById(R.id.WeekDuration6);
                        TextView WeekDuration7 = (TextView) findViewById(R.id.WeekDuration7);

                        WeekDay1.setText(weekdays.get(0).WeekDayName);
                        WeekDay2.setText(weekdays.get(1).WeekDayName);
                        WeekDay3.setText(weekdays.get(2).WeekDayName);
                        WeekDay4.setText(weekdays.get(3).WeekDayName);
                        WeekDay5.setText(weekdays.get(4).WeekDayName);
                        WeekDay6.setText(weekdays.get(5).WeekDayName);
                        WeekDay7.setText(weekdays.get(6).WeekDayName);

                        //for monday
                        if(weekdays.get(0).WeekDayDuration==8)
                            WeekDuration1.setText("Full Day");
                        else if(weekdays.get(0).WeekDayDuration==4)
                            WeekDuration1.setText("Half Day");
                        else
                            WeekDuration1.setText("Non-working Day");

                        //for tuesday
                        if(weekdays.get(1).WeekDayDuration==8)
                            WeekDuration2.setText("Full Day");
                        else if(weekdays.get(1).WeekDayDuration==4)
                            WeekDuration2.setText("Half Day");
                        else
                            WeekDuration2.setText("Non-working Day");

                        //for wednsday
                        if(weekdays.get(2).WeekDayDuration==8)
                            WeekDuration3.setText("Full Day");
                        else if(weekdays.get(2).WeekDayDuration==4)
                            WeekDuration3.setText("Half Day");
                        else
                            WeekDuration3.setText("Non-working Day");

                        //for thursday
                        if(weekdays.get(3).WeekDayDuration==8)
                            WeekDuration4.setText("Full Day");
                        else if(weekdays.get(3).WeekDayDuration==4)
                            WeekDuration4.setText("Half Day");
                        else
                            WeekDuration4.setText("Non-working Day");

                        //for friday
                        if(weekdays.get(4).WeekDayDuration==8)
                            WeekDuration5.setText("Full Day");
                        else if(weekdays.get(4).WeekDayDuration==4)
                            WeekDuration5.setText("Half Day");
                        else
                            WeekDuration5.setText("Non-working Day");

                        //for saturday
                        if(weekdays.get(5).WeekDayDuration==8)
                            WeekDuration6.setText("Full Day");
                        else if(weekdays.get(5).WeekDayDuration==4)
                            WeekDuration6.setText("Half Day");
                        else
                            WeekDuration6.setText("Non-working Day");

                        //for sunday
                        if(weekdays.get(6).WeekDayDuration==8)
                            WeekDuration7.setText("Full Day");
                        else if(weekdays.get(6).WeekDayDuration==4)
                            WeekDuration7.setText("Half Day");
                        else
                            WeekDuration7.setText("Non-working Day");
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
}
