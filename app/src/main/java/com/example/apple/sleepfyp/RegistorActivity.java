package com.example.apple.sleepfyp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistorActivity extends AppCompatActivity {

    EditText username, phone, password;
    ImageView profile;
    Button btm1, btm2;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registor);

        username = (EditText) findViewById(R.id.username);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        profile = (ImageView) findViewById(R.id.imageView);
        btm1 = (Button) findViewById(R.id.signup);
        btm2 = (Button) findViewById(R.id.signin);
        //cpassword = (EditText) findViewById(R.id.cpassword);

        btm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });


        btm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(RegistorActivity.this,loginActivity.class));
                Intent intent =new Intent();
                intent.setClass(RegistorActivity.this,loginActivity.class);
                String text= username.getText().toString();
                intent.putExtra("username1",text);
                String data= phone.getText().toString();
                intent.putExtra("phone1",data);


                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upload();
            }
        });
    }


    private void upload() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 10);
    }

    private void signup() {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("users").child(uid);
                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {

                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String imageurl = task.toString();
                                        UserModel userModel = new UserModel();
                                        userModel.phone = phone.getText().toString();
                                        userModel.uid = uid;
                                        userModel.imageurl = imageurl;

                                       FirebaseDatabase.getInstance().getReference().child("username").child(uid).setValue(userModel);
                                        //FirebaseDatabase database =FirebaseDatabase.getInstance();
                                       //final DatabaseReference myRef= database.getReference();
                                        //myRef.child("username").child(uid).setValue(userModel);


                                    }
                                });
                            } else {
                                Toast.makeText(RegistorActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {

                    Toast.makeText(RegistorActivity.this, "error", Toast.LENGTH_SHORT).show();

                }
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK) {
            profile.setImageURI(data.getData());
            imageUri = data.getData();
        }


    }
}




