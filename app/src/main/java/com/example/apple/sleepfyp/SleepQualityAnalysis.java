package com.example.apple.sleepfyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SleepQualityAnalysis extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleep_quality_analysis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sleep_quality_analysis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_day) {
            //startActivity(new Intent(SleepQualityAnalysis.this,chartofpie.class));
            Intent intent =new Intent();
            intent.setClass(SleepQualityAnalysis.this,chartofpie.class);
            String text= "day";
            intent.putExtra("om",text);
            startActivity(intent);
        } else if (id == R.id.nav_week) {
            //startActivity(new Intent(SleepQualityAnalysis.this,chartofpie.class));
            Intent intent =new Intent();
            intent.setClass(SleepQualityAnalysis.this,chartofpie.class);
            String text= "week";
            intent.putExtra("om",text);
            startActivity(intent);
        } else if (id == R.id.nav_month) {
           // startActivity(new Intent(SleepQualityAnalysis.this,chartofpie.class));
            Intent intent =new Intent();
            intent.setClass(SleepQualityAnalysis.this,chartofpie.class);
            String text= "month";
            intent.putExtra("om",text);
            startActivity(intent);
        } else if (id == R.id.nav_year) {
            /*Intent intent =new Intent();
            intent.setClass(SleepQualityAnalysis.this,chartofpie.class);
            /*String text= username.getText().toString();
            intent.putExtra("username2",text);
            startActivity(intent);*/
            //startActivity(new Intent(SleepQualityAnalysis.this,chartofpie.class));
            Intent intent =new Intent();
            intent.setClass(SleepQualityAnalysis.this,chartofpie.class);
            String text= "year";
            intent.putExtra("om",text);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id==R.id.nav_return){
            startActivity(new Intent(SleepQualityAnalysis.this,MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
