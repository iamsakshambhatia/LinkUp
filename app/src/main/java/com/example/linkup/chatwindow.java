//package com.example.linkup;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.Firebase;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Objects;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class chatwindow extends AppCompatActivity {
//
//    String recImg, recUid, recname, sendUid;
//    CircleImageView profile;
//    TextView receiverName;
//    CardView sendbtn;
//    EditText txtmsg;
//    FirebaseAuth auth;
//    FirebaseDatabase database;
//    public static String senderImg;
//    public static String receiverImg;
//    String senderRoom, receiverRoom;
//    RecyclerView msngerAdp;
//    ArrayList<msgModelClass> messagesArrayList;
//    messagesAdapter messagesAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        FirebaseApp.initializeApp(this);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_chat_window);
//        database = FirebaseDatabase.getInstance();
//        auth = FirebaseAuth.getInstance();
//
//        recname = getIntent().getStringExtra("namee");
//        recImg = getIntent().getStringExtra("recimg");
//        recUid = getIntent().getStringExtra("uid");
//
//        sendUid = auth.getUid();
//        senderRoom = sendUid + recUid;
//        receiverRoom = recUid + sendUid;
//
//        messagesArrayList = new ArrayList<>();
//
//        msngerAdp = findViewById(R.id.msgAdapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);
//        msngerAdp.setLayoutManager(linearLayoutManager);
//        messagesAdapter = new messagesAdapter(chatwindow.this,messagesArrayList);
//        msngerAdp.setAdapter(messagesAdapter);
//
//        sendbtn = findViewById(R.id.sendbtnn);
//        txtmsg = findViewById(R.id.textmsg);
//
//        profile = findViewById(R.id.profile_chat);
//        receiverName = findViewById(R.id.rec_name);
//        Picasso.get().load(recImg).into(profile);
//        receiverName.setText(""+recname);
//
//        DatabaseReference reference = database.getReference().child("user").child(Objects.requireNonNull(auth.getUid()));
//        DatabaseReference msgReference = database.getReference().child("user").child(senderRoom).child("messages");
//
//
//        msgReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messagesArrayList.clear();
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    msgModelClass messages = dataSnapshot.getValue(msgModelClass.class);
//                    messagesArrayList.add(messages);
//                }
//                messagesAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("profilepic").getValue() != null) {
//                    senderImg = Objects.requireNonNull(snapshot.child("profilepic").getValue()).toString();
//                }
////                else {
////                    senderImg = ""; // or provide a default value or placeholder URL
////                }
//                receiverImg = recImg;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//        sendbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String message = txtmsg.getText().toString();
//                if(message.isEmpty()){
//                    Toast.makeText(chatwindow.this,"Cannot send an empty message!",Toast.LENGTH_SHORT).show();
//                }
//                txtmsg.setText("");
//                Date date = new Date();
//                msgModelClass msgs = new msgModelClass(message,sendUid,date.getTime());
//                database = FirebaseDatabase.getInstance();
//                database.getReference().child("chats").child("senderRoom").child("messages").push().setValue(msgs).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        database.getReference().child("chats").child("receiverRoom").child("messages").push().setValue(msgs).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//
//                            }
//                        });
//                    }
//                });
//            }
//        });
//
//    }
//}

package com.example.linkup;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatwindow extends AppCompatActivity {

    String recImg, recUid, recname, sendUid;
    CircleImageView profile;
    TextView receiverName;
    CardView sendbtn;
    EditText txtmsg;
    FirebaseAuth auth;
    FirebaseDatabase database;
    public static String senderImg;
    public static String receiverImg;
    String senderRoom, receiverRoom;
    RecyclerView msngerAdp;
    ArrayList<msgModelClass> messagesArrayList;
    messagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_chat_window);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        recname = getIntent().getStringExtra("namee");
        recImg = getIntent().getStringExtra("recimg");
        recUid = getIntent().getStringExtra("uid");

        sendUid = auth.getUid();
        senderRoom = sendUid + recUid;
        receiverRoom = recUid + sendUid;

        messagesArrayList = new ArrayList<>();

        msngerAdp = findViewById(R.id.msgAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        msngerAdp.setLayoutManager(linearLayoutManager);
        messagesAdapter = new messagesAdapter(chatwindow.this, messagesArrayList);
        msngerAdp.setAdapter(messagesAdapter);

        sendbtn = findViewById(R.id.sendbtnn);
        txtmsg = findViewById(R.id.textmsg);

        profile = findViewById(R.id.profile_chat);
        receiverName = findViewById(R.id.rec_name);
        Picasso.get().load(recImg).into(profile);
        receiverName.setText(recname);

        DatabaseReference reference = database.getReference().child("user").child(Objects.requireNonNull(auth.getUid()));
        DatabaseReference msgReference = database.getReference().child("chats").child(senderRoom).child("messages");

        // Loading messages from Firebase
        msgReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    msgModelClass messages = dataSnapshot.getValue(msgModelClass.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
                msngerAdp.scrollToPosition(messagesArrayList.size() - 1); // Scroll to latest message
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });

        // Loading sender and receiver images
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("profilepic").getValue() != null) {
                    senderImg = snapshot.child("profilepic").getValue().toString();
                }
                receiverImg = recImg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors
            }
        });

        // Send button listener
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = txtmsg.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(chatwindow.this, "Cannot send an empty message!", Toast.LENGTH_SHORT).show();
                    return;
                }
                txtmsg.setText("");
                Date date = new Date();
                msgModelClass msgs = new msgModelClass(message, sendUid, date.getTime());

                // Sending message to Firebase
                DatabaseReference senderRef = database.getReference().child("chats").child(senderRoom).child("messages");
                DatabaseReference receiverRef = database.getReference().child("chats").child(receiverRoom).child("messages");

                senderRef.push().setValue(msgs).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            receiverRef.push().setValue(msgs);
                        }
                    }
                });
            }
        });
    }
}
