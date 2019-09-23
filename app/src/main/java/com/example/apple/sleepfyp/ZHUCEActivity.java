package com.example.apple.sleepfyp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class ZHUCEActivity extends AppCompatActivity {
    private EditText edittext1,edittext2,edittext3;
    private Button button;
    private DatabaseHelper DatabaseHelper;
    //数据库名称
    private static final String DATABASE_NAME="User.db";
    //数据库版本号
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_NAME="username";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zhuce);
        edittext1=(EditText)findViewById(R.id.editview1);
        edittext2=(EditText)findViewById(R.id.editview2);
        edittext3=(EditText)findViewById(R.id.editview3);
        button=(Button)findViewById(R.id.tijiao);
        button.setOnClickListener(new OnClickListener(){


            @Override
            public void onClick(View v) {
              // TODO Auto-generated method stub
                String namestring = edittext1.getText().toString();
                String passstring = edittext2.getText().toString();
                String repassstring=edittext3.getText().toString();
                if(passstring.equals(repassstring))
                {
                    DatabaseHelper=new DatabaseHelper(ZHUCEActivity.this,DATABASE_NAME,null,DATABASE_VERSION);
                    db =  DatabaseHelper.getReadableDatabase();
                    db.execSQL("insert into username (name,password) values(?,?)",new String[]{namestring,passstring});

                    Toast.makeText(ZHUCEActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                    Intent b=new Intent(ZHUCEActivity.this,dengluActivity.class);
                    startActivity(b);
                }
                else
                {
                    Toast.makeText(ZHUCEActivity.this,"两次密码不一致", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    }

