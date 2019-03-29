package com.example.ems;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.ems.Adapter.CustomExpandableListAdapter;
import com.example.ems.Helper.FragmentNavigationManager;
import com.example.ems.Interface.NavigationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    protected DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
  //  private NavigationManager navigationManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        //init view
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle=getTitle().toString();
        expandableListView=(ExpandableListView)findViewById(R.id.navlist);
       // navigationManager= FragmentNavigationManager.getInstance(this);

        initItems();

        View listHeaderView= getLayoutInflater().inflate(R.layout.nav_header_main,null,false);
        expandableListView.addHeaderView(listHeaderView);

        getData();

        addDrawerItem();
        setupDrawer();

        //if(savedInstanceState==null)
          //  selectFirstItemAsDefault();

        //set group indicator
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        expandableListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("EMS");

    }

    private int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {
       /* if(navigationManager!= null)
        {
            String firstItem = lstTitle.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }*/
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("EMS");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void addDrawerItem() {
        adapter=new CustomExpandableListAdapter(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString());//set tilte for toolbar when open menu

            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("EMS");

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                //change fragment
                String selecteditem=((List)(lstChild.get(lstTitle.get(groupPosition)))).get(childPosition).toString();
                getSupportActionBar().setTitle(selecteditem);
                //My info
                if(groupPosition==1){
                    //Personal details
                    if(childPosition==0){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                    //Contact detail
                    else if(childPosition==1){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                    //Emergency detail
                    else if(childPosition==2){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                    //Qualification
                    else if(childPosition==3){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                }
                //my tasks
                else if(groupPosition==2)
                {
                    //my tasks
                    if(childPosition==0){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                    //my projects
                    else if(childPosition==1){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                }
                //my leave
                else if(groupPosition==4)
                {
                    //apply leave
                    if(childPosition==0){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                    //leave history
                    if(childPosition==1){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                    //leave usage report
                    if(childPosition==2){
                        Intent intent= new Intent(getApplicationContext(),LeaveUsageActivity.class);
                        startActivity(intent);
                    }
                    //holiday
                    if(childPosition==3){
                        Intent intent= new Intent(getApplicationContext(),HolidayActivity.class);
                        startActivity(intent);
                    }
                    //work week
                    if(childPosition==4){
                        Intent intent= new Intent(getApplicationContext(),WeekdayActivity.class);
                        startActivity(intent);
                    }
                }
                //complains
                else if(groupPosition==6){
                    //raise complain
                    if(childPosition==0){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                    //complain history
                    if(childPosition==1){
                        Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(intent);
                    }
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }

        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long id) {
                //if Home is selected from navigation drawer
                if(groupPosition==0){
                    Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                    startActivity(intent);
                    return true;
                }

                //if My attendance is selected from navigation drawer
                if(groupPosition==3){
                    Intent intent= new Intent(getApplicationContext(),ViewAttendanceActivity.class);
                    startActivity(intent);
                    return true;
                }
                //if My payslip is selected from navigation drawer
                if(groupPosition==7){
                    Intent intent= new Intent(getApplicationContext(),DashboardActivity.class);
                    startActivity(intent);
                    return true;
                }
                //if My Performance is selected from navigation drawer
                if(groupPosition==5){
                    Intent intent= new Intent(getApplicationContext(),ViewReviewsActivity.class);
                    startActivity(intent);
                    return true;
                }



                return false;
            }
        });
    }

    private void getData() {

      //  List<String> title= Arrays.asList("Android Programing","Xamarin ","iOS");
        List<String> leavechilditem=Arrays.asList("Apply for leave","Leave History","Usage Report","Holidays","Work Week");
        List<String> infochild=Arrays.asList("Personal Details","Contact Detail","Emergency Contact","Qualification");
        List<String> taskchild=Arrays.asList("My Tasks","My Projects");
        List<String> complainchild=Arrays.asList("Raise Complain","Complain History");

        lstChild =new TreeMap<>();
        lstChild.put(lstTitle.get(4),leavechilditem);
        lstChild.put(lstTitle.get(1),infochild);
        lstChild.put(lstTitle.get(2),taskchild);
        lstChild.put(lstTitle.get(6),complainchild);

       // lstTitle = new ArrayList<>(lstChild.keySet());

    }

    private void initItems() {

        lstTitle = Arrays.asList("Home","My Info ","My Tasks","My Attendance", "My Leaves","My Performance","My Complains","My Payslip");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    
}