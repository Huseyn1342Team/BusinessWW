package com.updownteam.businessww;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AskAndAnswer_3_adapter extends RecyclerView.Adapter<AskAndAnswer_3_adapter.ViewHolder> {

    private List<AskAndAnswer_3_model> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    AskAndAnswer_3_adapter(Context context, List<AskAndAnswer_3_model> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.askandanswer_2_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AskAndAnswer_3_model model= mData.get(position);
        holder.Question.setText(model.getComment());
        holder.Time.setText(model.getTime());
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(model.getUid());
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String NameString  = dataSnapshot.child("name").getValue().toString();
                String ProfilString = dataSnapshot.child("profil").getValue().toString();
                Picasso.get().load(ProfilString).into(holder.ProfilImage);
                holder.Name.setText(NameString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        TextView Time;
        TextView Question;
        ViewHolder(View itemView) {
            super(itemView);
            ProfilImage = (CircleImageView)itemView.findViewById(R.id.profilimage);
            Name = (TextView)itemView.findViewById(R.id.name);
            Time = (TextView)itemView.findViewById(R.id.time);
            Question = (TextView)itemView.findViewById(R.id.question);

        }
    }

    // convenience method for getting data at click position
    AskAndAnswer_3_model getItem(int id) {
        return mData.get(id);
    }

}
