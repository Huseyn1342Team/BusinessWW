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

public class InvestorMain_Adapter extends RecyclerView.Adapter<InvestorMain_Adapter.ViewHolder> {

    private List<InvestorMain_model> mData;
    private LayoutInflater mInflater;
    // data is passed into the constructor
    InvestorMain_Adapter(Context context, List<InvestorMain_model> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.investor_request_layout, parent, false);
        return new ViewHolder(view);
    }
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final InvestorMain_model model = mData.get(position);
        final String ReceiverUid = model.getUid();
        final String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mInflater.getContext(),ProfilGuest.class);
                i.putExtra("uid",ReceiverUid);
                mInflater.getContext().startActivity(i);
            }
        });
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profil = dataSnapshot.child("profil").getValue().toString();
                String name = dataSnapshot.child("name").getValue().toString();
                String gender = dataSnapshot.child("gender").getValue().toString();
                Picasso.get().load(profil).into(holder.ProfilImage);
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
               db1.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      if(dataSnapshot.child("Investor").child("Request").hasChild(ReceiverUid)){
                          DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                          db1.child("Investor").child("Request").child(ReceiverUid).removeValue();
                      }
                       if(dataSnapshot.child("Producer").child("Request").hasChild(ReceiverUid)){
                           DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                           db1.child("Producer").child("Request").child(ReceiverUid).removeValue();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
            }
        });
        holder.Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Investor").child("Request").hasChild(ReceiverUid)){
                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                            db1.child("Investors").child(ReceiverUid).child("uid").setValue(ReceiverUid);
                            db1.child("Investor").child("Request").child(ReceiverUid).removeValue();
                        }
                        if(dataSnapshot.child("Producer").child("Request").hasChild(ReceiverUid)){
                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                            db1.child("Producers").child(ReceiverUid).child("uid").setValue(ReceiverUid);
                            db1.child("Producer").child("Request").child(ReceiverUid).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
        CardView Card;
        CircleImageView ProfilImage;
        TextView Name;
        TextView Gender;
        Button Ignore;
        Button Agree;
        ViewHolder(View itemView) {
            super(itemView);
            Card = (CardView)itemView.findViewById(R.id.card);
            ProfilImage = (CircleImageView)itemView.findViewById(R.id.profilimage);
            Name = (TextView)itemView.findViewById(R.id.name);
            Gender = (TextView)itemView.findViewById(R.id.gender);
            Ignore = (Button)itemView.findViewById(R.id.ignore);
            Agree = (Button)itemView.findViewById(R.id.agree);
        }
    }

    // convenience method for getting data at click position
    InvestorMain_model getItem(int id) {
        return mData.get(id);
    }
}

