package com.example.ems;

import android.Manifest;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ems.Helper.GPStracker;

public class PunchInActivity extends AppCompatActivity {

    Button punchinbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_in);

        punchinbutton=(Button) findViewById(R.id.PunchInButton);
        ActivityCompat.requestPermissions(PunchInActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        punchinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPStracker tracker = new GPStracker(getApplicationContext());
                Location l = tracker.getLocation();

                if(l!= null)
                {
                    double lat=l.getLatitude();
                    double lon=l.getLongitude();
                    Toast.makeText(getApplicationContext(),"Lat:"+lat +"\n Long:"+lon,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
