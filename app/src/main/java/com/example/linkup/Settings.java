package com.example.linkup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings extends AppCompatActivity {

    CircleImageView setprofile;
    EditText setname, setlname, setstatus;
    Button donebut;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri setImageUri;
    String email,password;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        setprofile = findViewById(R.id.settingprofile);
        setname = findViewById(R.id.settingname);
        setlname = findViewById(R.id.settinglname);
        setstatus = findViewById(R.id.settingstatus);
        donebut = findViewById(R.id.donebut);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);

        // for fetching data from database & storage
        DatabaseReference reference = database.getReference().child("user").child(Objects.requireNonNull(auth.getUid()));
        StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Adding null checks to ensure no NullPointerExceptions
                if (snapshot.exists()) {
                    email = snapshot.child("mail").getValue(String.class);
                    password = snapshot.child("password").getValue(String.class);
                    String name = snapshot.child("fname").getValue(String.class);
                    String lname = snapshot.child("lname").getValue(String.class);
                    String profile = snapshot.child("profilepic").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);

                    // Setting UI components only if values are not null
                    setname.setText(name != null ? name : "");
                    setlname.setText(lname != null ? lname : "");
                    setstatus.setText(status != null ? status : "");
                    if (profile != null && !profile.isEmpty()) {
                        Picasso.get().load(profile).into(setprofile);
                    } else {
                        setprofile.setImageResource(R.drawable.cat_profile); // Use a default placeholder image
                    }
                } else {
                    Toast.makeText(Settings.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // to fetch image from the gallery
        setprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
            }
        });
        donebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                String name = setname.getText().toString();
                String lname = setlname.getText().toString();
                String Status = setstatus.getText().toString();
                if (setImageUri!=null){
                    storageReference.putFile(setImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String finalImageUri = uri.toString();
//                                    Users users = new Users(auth.getUid(), name,lname,email,password,finalImageUri,Status);
                                    Users users = new Users(name,lname,email,password,auth.getUid(),Status,finalImageUri);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressDialog.dismiss();
                                                Toast.makeText(Settings.this, "Data Is saved successfully.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Settings.this,MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                progressDialog.dismiss();
                                                Toast.makeText(Settings.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }else {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String finalImageUri = uri.toString();
//                            Users users = new Users(auth.getUid(), name,lname,email,password,finalImageUri,Status);
                            Users users = new Users(name,lname,email,password,auth.getUid(),Status,finalImageUri);
                            reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(Settings.this, "Data is saved successfully.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Settings.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Settings.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                setImageUri = data.getData();
                setprofile.setImageURI(setImageUri);
            }
        }
    }

}