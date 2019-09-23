package com.example.apple.sleepfyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CloudLogin extends AppCompatActivity {
    EditText password, username;
    Button login;
    TextView signup,lookaround;
    private Intent intent;
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        lookaround=findViewById(R.id.lookaround);
        login.setOnClickListener(new LoginListener());
        signup.setOnClickListener(new RegisterListener());
        lookaround.setOnClickListener(new DirectintoListener());

    }

    class LoginListener implements View.OnClickListener {
        public void onClick(View view) {
            String namestring = username.getText().toString();
            String Passwordstring = password.getText().toString();
            LoginRequest(namestring,Passwordstring);
        }
    }

    public void LoginRequest(final String accountNumber, final String password) {
        //请求地址
        String url = "http://106.14.119.203:8080/Sleeping/LoginServlet";    //注①
        String tag = "Login";    //注②

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");  //注③
                            String result = jsonObject.getString("Result");  //注④
                            if (result.equals("success")) {  //注⑤
                                startActivity(new Intent(CloudLogin.this,MainActivity.class));
                                Toast.makeText(CloudLogin.this, "Success",Toast.LENGTH_LONG).show();//做自己的登录成功操作，如页面跳转
                            } else {
                                Toast.makeText(CloudLogin.this, "Wrong username or password",Toast.LENGTH_LONG).show();

//做自己的登录失败操作，如Toast提示
                            }
                        } catch (JSONException e) {
                            Toast.makeText(CloudLogin.this, "NO INTERNET CONNECTION",Toast.LENGTH_LONG).show();
                            //做自己的请求异常操作，如Toast提示（“无网络连接”等）
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CloudLogin.this, "No response, try later",Toast.LENGTH_LONG).show();
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", accountNumber);  //注⑥
                params.put("Password", password);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }


    private class RegisterListener implements View.OnClickListener {
        public void onClick(View view) {
            startActivity(new Intent(CloudLogin.this,ZHUCEActivity.class));
        }
    }

    private class DirectintoListener implements View.OnClickListener {
        public void onClick(View view) {
            startActivity(new Intent(CloudLogin.this,MainActivity.class));
        }
    }
}


