package com.example.bhuiyan.newfirebase12;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    //private TextView textViewUserName;
    private Button btnLogout;
    private Button btnDublin;
    private Button btnImage;

   private  Button btnTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,SignupActivity.class));
        }
        //firebase user objecty
        FirebaseUser user=firebaseAuth.getCurrentUser();
        btnDublin=(Button)findViewById(R.id.btnDublin);
        btnTrip=(Button)findViewById(R.id.btnTrip);
        btnImage=(Button)findViewById(R.id.btnImage);
         textViewUserEmail=(TextView)findViewById(R.id.textViewUserEmail);
     //textViewUserName=(TextView)findViewById(R.id.textViewUserName);

        //textViewUserName.setText("Welcome:" +user.getName());
        textViewUserEmail.setText("Welcome: "+user.getEmail());
        btnLogout=(Button)findViewById(R.id.btnLogout);


        //listener Attached
        btnLogout.setOnClickListener(this);
        btnTrip.setOnClickListener(this);
        btnDublin.setOnClickListener(this);
        btnImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==btnLogout){
            firebaseAuth.signOut();
            startActivity(new Intent(this,SignupActivity.class));
        }
        if(view==btnTrip){
            //will open login activity here
            startActivity(new Intent(this,TripActivity.class));
    }
       // if(view==btnImage){
       //     startActivity(new Intent(this,RetrieveActivity.class));
        //}
}}