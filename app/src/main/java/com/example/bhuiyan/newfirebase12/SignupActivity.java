package com.example.bhuiyan.newfirebase12;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSignin;
    private EditText etTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        etTextName=(EditText)findViewById(R.id.etTextName);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);

        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        btnSignin=(Button)findViewById(R.id.btnSignin);
        textViewSignup=(TextView) findViewById(R.id.textViewSignup);
        progressDialog=new ProgressDialog(this);
        btnSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }
    private void userLogin(){
        String name=etTextName.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
           //name is empty
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_SHORT).show();
            //stopping function from excuting further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            //stopping function from excuting further
            return;
        }

//if validation are satisfied or ok
        //will show a progressbar first
        progressDialog.setMessage("Signing in User....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            //profile activity start
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }

                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view==btnSignin){
            userLogin();

        }
        if (view==textViewSignup){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
