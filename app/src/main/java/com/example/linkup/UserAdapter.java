package com.example.linkup;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {

    MainActivity mainActivity;
    ArrayList<Users> usersArrayList;

    public UserAdapter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity = mainActivity;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewholder holder, int position) {
        Users users = usersArrayList.get(position);
        String fullname = users.getFname() + " " + users.getLname();
        holder.username.setText(fullname);
        holder.userstatus.setText(users.getStatus());
        Picasso.get().load(users.profilepic).into(holder.profile_image);
        //redirecting the user to chat window, if he clicks on name, profile, or status
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, chatwindow.class);
                intent.putExtra("namee",users.getFname());
                intent.putExtra("recimg",users.getProfilepic());
                intent.putExtra("uid",users.getId());
                mainActivity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        CircleImageView profile_image;
        TextView username;
        TextView userstatus;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            userstatus = itemView.findViewById(R.id.userstatus);
        }
    }
}
