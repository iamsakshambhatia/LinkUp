package com.example.linkup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class sign_up extends AppCompatActivity {
    TextView signinbutton;
    EditText firstname, lastname, email, pass, confirmpass;
    Button button;
    CircleImageView profile;
    FirebaseAuth auth;
    Uri imageURI;
    String profilepic;
    String emailPattern = "[a-z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-z0-9-]+(\\.[a-z0-9-]+)*";
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.signUpButton);
        firstname = findViewById(R.id.rgFirstName);
        lastname = findViewById(R.id.rgLastName);
        email = findViewById(R.id.rgEmail);
        pass = findViewById(R.id.rgPassword);
        confirmpass = findViewById(R.id.rgConfirmPassword);
        signinbutton = findViewById(R.id.signInButton);
        profile = findViewById(R.id.profile_image);

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sign_up.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = firstname.getText().toString();
                String lname = lastname.getText().toString();
                String mail = email.getText().toString();
                String pwd = pass.getText().toString();
                String cpwd = confirmpass.getText().toString();
                String status = "Hey! I'm using LinkUp!";

                if(TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(cpwd)){
                    progressDialog.dismiss();
                    Toast.makeText(sign_up.this,"Please enter required details.",Toast.LENGTH_SHORT).show();
                } else if(!mail.matches(emailPattern)) {
                    progressDialog.dismiss();
                    email.setError("Invalid Email!");
                } else if(pwd.length() < 8){
                    progressDialog.dismiss();
                    pass.setError("Password should be at least 8 characters long!");
                } else if(!pwd.equals(cpwd)){
                    progressDialog.dismiss();
                    pass.setError("Passwords doesn't match!");
                } else {
                    auth.createUserWithEmailAndPassword(mail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                               DatabaseReference reference = database.getReference().child("user").child(id);
                               StorageReference storageReference = storage.getReference().child("Upload").child(id);

                               if(imageURI != null){
                                   storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                       @Override
                                       public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                           if(task.isSuccessful()){
                                               storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                   @Override
                                                   public void onSuccess(Uri uri) {
                                                       profilepic = uri.toString();
                                                       Users users = new Users(fname,lname,mail,pwd,id,status,profilepic);
//                                                       fname, lname, mail, pass, userID, status, profilepic;
                                                       reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {
                                                               if(task.isSuccessful()){
                                                                   progressDialog.show();
                                                                   Intent intent = new Intent(sign_up.this,login.class);
                                                                   startActivity(intent);
                                                                   finish();
                                                               } else {
                                                                   Toast.makeText(sign_up.this, "User not created!", Toast.LENGTH_SHORT).show();
                                                               }
                                                           }
                                                       });
                                                   }
                                               });
                                           }
                                       }
                                   });
                               } else {
                                   String status = "Hey! I'm using LinkUp!";
                                   profilepic = "https://firebasestorage.googleapis.com/v0/b/linkup-343e1.appspot.com/o/default_user.png?alt=media&token=1188f982-aaed-4dc7-a741-b7f322dccbc3";
                                   Users users = new Users(fname,lname,mail,pwd,id,status,profilepic);
                                   reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                               progressDialog.show();
                                               Intent intent = new Intent(sign_up.this,login.class);
                                               startActivity(intent);
                                               finish();
                                           } else {
                                               Toast.makeText(sign_up.this, "User not created!", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   });
                               }
                           } else {
                               Toast.makeText(sign_up.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
            }
        });



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose from gallery"),10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(data != null){
                imageURI = data.getData();
                profile.setImageURI(imageURI);
            }
        }
    }
}