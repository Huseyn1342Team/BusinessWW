package com.updownteam.businessww;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.CheckedOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {

    private List<FriendRequestModel> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    FriendRequestAdapter(Context context, List<FriendRequestModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.request_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FriendRequestModel model= mData.get(position);
        final String ReceiverUid = model.getUid();
        final String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(ReceiverUid).child("name").getValue().toString();
                String gender = dataSnapshot.child(ReceiverUid).child("gender").getValue().toString();
                holder.Name.setText(name);
                holder.Gender.setText(gender);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.Ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                db1.child("FriendRequest").child(ReceiverUid).removeValue();
            }
        });
        holder.Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid);
                db2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                        db1.child("Friends").child(ReceiverUid).child("uid").setValue(ReceiverUid);
                        db1.child("Friends").child(ReceiverUid).child("name").setValue(name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid);
                        db2.child("Friends").child(MyUid).child("uid").setValue(MyUid);
                        db2.child("Friends").child(MyUid).child("name").setValue(name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                db1.child("FriendRequest").child(ReceiverUid).removeValue();
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ProfilImage;
        TextView Name;
        TextView Gender;
        Button Agree;
        Button Ignore;
        ViewHolder(View itemView) {
            super(itemView);
            ProfilImage = (CircleImageView)itemView.findViewById(R.id.profilimage);
            Name = (TextView)itemView.findViewById(R.id.name);
            Gender = (TextView)itemView.findViewById(R.id.gender);
            Agree = (Button)itemView.findViewById(R.id.agree);
            Ignore = (Button)itemView.findViewById(R.id.ignore);
        }
    }

    // convenience method for getting data at click position
    FriendRequestModel getItem(int id) {
        return mData.get(id);
    }

}