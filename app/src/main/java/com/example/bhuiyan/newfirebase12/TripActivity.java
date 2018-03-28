package com.example.bhuiyan.newfirebase12;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class TripActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHOOSE_IMAGE =10;
    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    //can store data with this reference to firebase database
    private DatabaseReference databaseReference;
    private EditText editTextStart,editTextEnd;
    private EditText editTextComments;
    private EditText editTextDate;
    private Button btnSave;
    private  Button btnLogout;
    private Button btnTripList;
    ImageView imageView;
    Uri uriProfileImage;
    ProgressBar progressBar;
    String profileImageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,SignupActivity.class));
        }
        //initialize views
        databaseReference= FirebaseDatabase.getInstance().getReference("Fav Trip");
        editTextStart=(EditText)findViewById(R.id.editTextStart);
        editTextEnd=(EditText)findViewById(R.id.editTextEnd);
        editTextComments=(EditText)findViewById(R.id.editTextComments);
        editTextDate=(EditText)findViewById(R.id.editTextDate);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnTripList=(Button)findViewById(R.id.btnTripList);
        imageView=(ImageView)findViewById(R.id.imageView);
        loadTripInformation();

        progressBar=findViewById(R.id.progressBar);
        //firebase user object
        FirebaseUser user=firebaseAuth.getCurrentUser();
        textViewUserEmail=(TextView)findViewById(R.id.textViewUserEmail);
//displaying logged in user name
        textViewUserEmail.setText("Welcome: "+user.getEmail());
        btnLogout=(Button)findViewById(R.id.btnLogout);
        //listener Attached to butto
        btnLogout.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnTripList.setOnClickListener(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });
    }

    private void loadTripInformation() {
    }
    private void saveTripInformation() {
        String start=editTextStart.getText().toString().trim();
        String end=editTextEnd.getText().toString().trim();
        String date=editTextDate.getText().toString().trim();
        String comments=editTextComments.getText().toString().trim();


        if (start.isEmpty()){
            editTextStart.setError("Name Required");
            editTextStart.requestFocus();
            return;
        }
        TripInformation tripInformation=new TripInformation(start,end,date,comments);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).push().child("trip").setValue(tripInformation);//ref.child(user.getUid().setValue...to change the database name

        Toast.makeText(this,"Information saved...",Toast.LENGTH_LONG).show();
        if (user!=null && profileImageUrl!=null){
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName(start).setPhotoUri(Uri.parse(profileImageUrl)).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(TripActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    @Override

    public void onClick(View view) {
        if (view==btnLogout){
            firebaseAuth.signOut();
            startActivity(new Intent(this,SignupActivity.class));
        }

        if (view==btnSave){
            saveTripInformation();
        }
if(view==btnTripList){
           startActivity(new Intent(this,RetrieveActivity.class));
       }

    }


    //override method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
           uriProfileImage= data.getData();

            try {
               Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                imageView.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();


           } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef= FirebaseStorage.getInstance().getReference
                ("profilepics/"+System.currentTimeMillis()+".jpg");
        if (uriProfileImage!=null){
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    profileImageUrl=taskSnapshot.getDownloadUrl().toString();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(TripActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
  private  void showImageChooser(){
        Intent intent=new Intent();

       intent.setType("Image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent,"Select Profile Image"), CHOOSE_IMAGE);
    }

    }



