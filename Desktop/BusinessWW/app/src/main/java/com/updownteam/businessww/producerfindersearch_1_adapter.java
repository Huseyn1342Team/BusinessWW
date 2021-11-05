package com.updownteam.businessww;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class producerfindersearch_1_adapter extends RecyclerView.Adapter<producerfindersearch_1_adapter.ViewHolder> {

    private List<investorfindersearch_model> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    producerfindersearch_1_adapter(Context context, List<investorfindersearch_model> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.investorfindersearch, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final investorfindersearch_model model= mData.get(position);
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(model.getUid());
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String gender = dataSnapshot.child("gender").getValue().toString();
                String profil = dataSnapshot.child("profil").getValue().toString();
                holder.Name.setText(name);
                holder.Gender.setText(gender);
                Picasso.get().load(profil).into(holder.Profil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(mInflater.getContext(),producerfindersearch_2.class);
                i.putExtra("uid",model.getUid());
                mInflater.getContext().startActivity(i);
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
        TextView Name;
        TextView Gender;
        Button Accept;
        CardView Card;
        CircleImageView Profil;
        ViewHolder(View itemView) {
            super(itemView);
            Name = (TextView)itemView.findViewById(R.id.name);
            Card = (CardView)itemView.findViewById(R.id.card1);
            Gender = (TextView)itemView.findViewById(R.id.gender);
            Accept = (Button)itemView.findViewById(R.id.accept);
            Profil = (CircleImageView)itemView.findViewById(R.id.profilphoto);
        }
    }

    // convenience method for getting data at click position
    investorfindersearch_model getItem(int id) {
        return mData.get(id);
    }

}