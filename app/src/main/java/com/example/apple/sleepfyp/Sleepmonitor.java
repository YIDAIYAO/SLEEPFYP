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

/*import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;*/


public class Sleepmonitor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /*LineChart linechart;

    ArrayList<String> xAxes=new ArrayList<>();
    ArrayList<Entry>yAxes=new ArrayList<>();
    ArrayList<ILineDataSet> lineDataSets=new ArrayList<>();*/

   // private ArrayList<Entry> Heartrate[];
   // int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //registerReceiver(mReceiver, filter);
        setContentView(R.layout.activity_sleepmonitor);
       /* linechart = (LineChart) findViewById(R.id.linechart);


        xAxes.add("Monday");
        xAxes.add("Tuesday");
        xAxes.add("Wednesday");
        xAxes.add("Thursday");
        xAxes.add("Friday");
        xAxes.add("Saturday");
        xAxes.add("Sunday");

        yAxes.add(new Entry(40,0));
        yAxes.add(new Entry(20,1));
        yAxes.add(new Entry(23,2));
        yAxes.add(new Entry(34,3));
        yAxes.add(new Entry(32,4));
        yAxes.add(new Entry(14,5));
        yAxes.add(new Entry(45,6));
        String[] xaxes = new String[xAxes.size()];
        for (int i = 0; i < xAxes.size(); i++) {
            xaxes[i] = xAxes.get(i).toString();
        }
        LineDataSet lineDataSet = new LineDataSet(yAxes, "values");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setColor(Color.BLUE);
        lineDataSets.add(lineDataSet);
        linechart.setData(new LineData(xaxes,lineDataSets));
        linechart.setVisibleXRangeMaximum(65f);
        linechart.setTouchEnabled(true);
        linechart.setDragEnabled(true);*/


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
        getMenuInflater().inflate(R.menu.sleepmonitor, menu);
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

        if (id == R.id.nav_walk) {
            startActivity(new Intent(Sleepmonitor.this,RealTimeChart.class));
            // Handle the camera action
        } else if (id == R.id.nav_spo2) {
            Intent intent =new Intent();
            intent.setClass(Sleepmonitor.this,RealTimeChart.class);
            String text= "SPO2";
            intent.putExtra("from",text);
            startActivity(intent);
            //startActivity(new Intent(Sleepmonitor.this,RealTimeChart.class));
        } else if (id == R.id.nav_res) {
            Intent intent =new Intent();
            intent.setClass(Sleepmonitor.this,RealTimeChart.class);
            String text= "Respiratory Rate";
            intent.putExtra("from",text);
            startActivity(intent);

           // startActivity(new Intent(Sleepmonitor.this,RealTimeChart.class));
        } else if (id == R.id.nav_heart) {
            Intent intent =new Intent();
            intent.setClass(Sleepmonitor.this,RealTimeChart.class);
            String text= "Heart Rate";
            intent.putExtra("from",text);
            startActivity(intent);
            //startActivity(new Intent(Sleepmonitor.this,RealTimeChart.class));
        } else if (id == R.id.nav_bp) {
            startActivity(new Intent(Sleepmonitor.this,RealTimeChart.class));
        } else if (id == R.id.nav_length) {
            startActivity(new Intent(Sleepmonitor.this,RealTimeChart.class));
        }else if (id==R.id.nav_return){
            startActivity(new Intent(Sleepmonitor.this,MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}


