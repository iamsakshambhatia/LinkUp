//package com.example.linkup;
//
//import static com.example.linkup.chatwindow.receiverImg;
//import static com.example.linkup.chatwindow.senderImg;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class messagesAdapter extends RecyclerView.Adapter {
//
//    Context context;
//    ArrayList<msgModelClass> messagesAdapterArrayList;
//    int ITEM_SEND = 1;
//    int ITEM_RECEIVE = 2;
//
//    public messagesAdapter(Context context, ArrayList<msgModelClass> messagesAdpterArrayList) {
//        this.context = context;
//        this.messagesAdapterArrayList = messagesAdpterArrayList;
//    }
//
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == ITEM_SEND){
//            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
//            return new senderViewHolder(view);
//        }else {
//            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout, parent, false);
//            return new receiverViewHolder(view);
//        }
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        msgModelClass messages = messagesAdapterArrayList.get(position);
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                new AlertDialog.Builder(context).setTitle("Delete")
//                        .setMessage("Are you sure you want to delete this message?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        }).show();
//
//                return false;
//            }
//        });
//        if (holder.getClass() == senderViewHolder.class){
//            senderViewHolder viewHolder = (senderViewHolder) holder;
//            viewHolder.msgtext.setText(messages.getMessage());
//            Picasso.get().load(senderImg).into(viewHolder.circleImageView);
//        }else {
//            receiverViewHolder viewHolder = (receiverViewHolder) holder;
//            viewHolder.msgtext.setText(messages.getMessage());
//            Picasso.get().load(receiverImg).into(viewHolder.circleImageView);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        msgModelClass messages = messagesAdapterArrayList.get(position);
//        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderID())) {
//            return ITEM_SEND;
//        } else {
//            return ITEM_RECEIVE;
//        }
//
//    }
//
//    class senderViewHolder extends RecyclerView.ViewHolder {
//        CircleImageView circleImageView;
//        TextView msgtext;
//        public senderViewHolder(@NonNull View itemView) {
//            super(itemView);
//            circleImageView = itemView.findViewById(R.id.profilerggg);
//            msgtext = itemView.findViewById(R.id.msgsendertyp);
//        }
//    }
//
//    class receiverViewHolder extends RecyclerView.ViewHolder {
//        CircleImageView circleImageView;
//        TextView msgtext;
//        public receiverViewHolder(@NonNull View itemView) {
//            super(itemView);
//            circleImageView = itemView.findViewById(R.id.pro);
//            msgtext = itemView.findViewById(R.id.recivertextset);
//        }
//    }
//}

package com.example.linkup;

import static com.example.linkup.chatwindow.receiverImg;
import static com.example.linkup.chatwindow.senderImg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class messagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<msgModelClass> messagesAdapterArrayList;
    int ITEM_SEND = 1;
    int ITEM_RECEIVE = 2;

    public messagesAdapter(Context context, ArrayList<msgModelClass> messagesAdapterArrayList) {
        this.context = context;
        this.messagesAdapterArrayList = messagesAdapterArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return new senderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout, parent, false);
            return new receiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgModelClass messages = messagesAdapterArrayList.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Handle message deletion here
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                return false;
            }
        });

        if (holder.getClass() == senderViewHolder.class) {
            senderViewHolder viewHolder = (senderViewHolder) holder;
            viewHolder.msgtext.setText(messages.getMessage());
            Picasso.get().load(senderImg).into(viewHolder.circleImageView);
        } else {
            receiverViewHolder viewHolder = (receiverViewHolder) holder;
            viewHolder.msgtext.setText(messages.getMessage());
            Picasso.get().load(receiverImg).into(viewHolder.circleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return messagesAdapterArrayList.size();  // Return the size of the list
    }

    @Override
    public int getItemViewType(int position) {
        msgModelClass messages = messagesAdapterArrayList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderID())) {
            return ITEM_SEND;
        } else {
            return ITEM_RECEIVE;
        }
    }

    class senderViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgtext;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profilerggg);  // Ensure this ID matches your layout
            msgtext = itemView.findViewById(R.id.msgsendertyp);  // Ensure this ID matches your layout
        }
    }

    class receiverViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgtext;

        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.pro);  // Ensure this ID matches your layout
            msgtext = itemView.findViewById(R.id.recivertextset);  // Ensure this ID matches your layout
        }
    }
}

