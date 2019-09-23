package com.example.apple.sleepfyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class MainActivity extends AppCompatActivity {

    Button sa,sbg,hsq;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sa=(Button)findViewById(R.id.sa);
        sbg=(Button)findViewById(R.id.sbg);
        hsq=(Button)findViewById(R.id.hsq);
        username=(TextView)findViewById(R.id.username);

        AndPermission.with(this)
                .runtime()
                .permission(
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION,
                        Permission.READ_EXTERNAL_STORAGE,
                        Permission.WRITE_EXTERNAL_STORAGE)
                .start();


        Intent intent=getIntent();
        String username2=intent.getStringExtra("username2");
        username.setText(username2);


        sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SleepQualityAnalysis.class));


            }
        });
        sbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,Sleepmonitor.class));

            }
        });
        hsq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainActivity.this, historydata1.class));


            }
        });

    }

}
