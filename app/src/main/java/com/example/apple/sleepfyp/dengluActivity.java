package com.example.apple.sleepfyp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.database.SQLException;

public class dengluActivity extends AppCompatActivity {
    private TextView textview;
    private static final String DATABASE_NAME="User.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="username";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Button button1;
    private EditText nameText,passText;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_denglu);
        nameText=(EditText)findViewById(R.id.username);
        passText=(EditText)findViewById(R.id.pasw);

        button1=(Button)findViewById(R.id.tijiao);
        textview=(TextView)findViewById(R.id.zhuce);

        textview.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                intent=new Intent(dengluActivity.this,ZHUCEActivity.class);
                startActivity(intent);
            }
        });

        TextView textview=(TextView)findViewById(R.id.zhuce);
        textview.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent a=new Intent(dengluActivity.this,ZHUCEActivity.class);
                startActivity(a);
            }

        });
        TextView textview1=(TextView)findViewById(R.id.suibian);
        textview1.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent a=new Intent(dengluActivity.this,MainActivity.class);
                startActivity(a);
            }

        });
        button1.setOnClickListener(new LoginListener());
    }
    class LoginListener implements OnClickListener{
        public void onClick(View v){
            String nameString =nameText.getText().toString();
            String passString=passText.getText().toString();
            if(nameString.equals("")||passString.equals(""))
            {
                //弹出消息框
                new AlertDialog.Builder(dengluActivity.this).setTitle("Error")
                        .setMessage("Username or password cannot be empty").setPositiveButton("Confirm", null)
                        .show();
            }else{
                isUserinfo(nameString,passString);
            }
        }
    }
    public Boolean isUserinfo(String name,String pass)
    {
        String nameString=name;
        String passString=pass;
        databaseHelper=new DatabaseHelper((Context) dengluActivity.this,"User.db",null,1);
        db =  databaseHelper.getReadableDatabase();
        try{
            Cursor cursor=db.query(TABLE_NAME, new String[]{"name","password"},"name=?",new String[]{nameString},null,null,"password");
            while(cursor.moveToNext())
            {
                String password=cursor.getString(cursor.getColumnIndex("password"));


                if(passString.equals(password))
                {
                    new AlertDialog.Builder(dengluActivity.this).setTitle("Correct")
                            .setMessage("Login Successful").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Intent a=new Intent(dengluActivity.this,MainActivity.class);
                            startActivity(a);
                        }
                    }).show();

                    break;
                }
                else
                {
                    Toast.makeText(this, "Wrong password or username",Toast.LENGTH_LONG).show();
                    break;
                }
            }

        }catch(SQLiteException e){
            CreatTable();
        }
        return false;
    }



    private void CreatTable() {
        // TODO Auto-generated method stub
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (name varchar(30) primary key,password varchar(30));";
        try{
            db.execSQL(sql);
        }catch(SQLException ex){}
    }


   /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu., menu);
        return true;
    }
*/



    }
