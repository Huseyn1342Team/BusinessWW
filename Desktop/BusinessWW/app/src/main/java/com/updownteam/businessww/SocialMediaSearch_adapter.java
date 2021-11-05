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
import java.util.jar.Attributes;
import java.util.zip.CheckedOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class SocialMediaSearch_adapter extends RecyclerView.Adapter<SocialMediaSearch_adapter.ViewHolder> {

    private List<SocialMediaSearch_model> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    SocialMediaSearch_adapter(Context context, List<SocialMediaSearch_model> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SocialMediaSearch_model model= mData.get(position);
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(model.getUid());
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(model.getUid().matches(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    holder.AddFriend.setVisibility(View.GONE);
                }
                if(dataSnapshot.child("FriendRequest").hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    holder.AddFriend.setText("Request Sented");
                    holder.AddFriend.setClickable(false);
                }
                if(dataSnapshot.child("Friends").hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    holder.AddFriend.setText("Your friend");
                    holder.AddFriend.setClickable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Picasso.get().load(model.getProfil()).into(holder.ProfilImage);
        holder.Name.setText(model.getName()+ " "+ model.getSurname());
        holder.Gender.setText(model.getGender());
        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mInflater.getContext(),ProfilGuest.class);
                i.putExtra("uid",model.getUid());
                mInflater.getContext().startActivity(i);
            }
        });
        holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i =new Intent(mInflater.getContext(),ProfilGuest.class);
                i.putExtra("uid",model.getUid());
                mInflater.getContext().startActivity(i);
            }
        });
        String text = holder.AddFriend.getText().toString();
        if(text.matches("Your friend")){
            Toast.makeText(mInflater.getContext(),"You send request!",Toast.LENGTH_LONG).show();
            return;
        }
        if(text.matches("Request sented")){
            Toast.makeText(mInflater.getContext(),"You are friend of him or her!",Toast.LENGTH_LONG).show();
            return;
        }
        holder.AddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ReceiverUid = model.getUid();
                String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");
                db1.child(ReceiverUid).child("FriendRequest").child(MyUid).child("uid").setValue(MyUid);
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
        CardView Card;
        Button AddFriend;
        ViewHolder(View itemView) {
            super(itemView);
            ProfilImage = (CircleImageView)itemView.findViewById(R.id.profilimage);
            Name = (TextView) itemView.findViewById(R.id.name);
            Gender = (TextView)itemView.findViewById(R.id.gender);
            AddFriend = (Button)itemView.findViewById(R.id.addfriend);
            Card = (CardView)itemView.findViewById(R.id.card1);
        }
    }

    // convenience method for getting data at click position
    SocialMediaSearch_model getItem(int id) {
        return mData.get(id);
    }

}

