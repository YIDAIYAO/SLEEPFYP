package com.example.apple.sleepfyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DatabaseActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        //myDb=new DatabaseHelper(ZHUCEActivity.this, DATABASE_NAME, this, DATABASE_VERSION);
    }
}
