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

public class AskAndAnswer_2_adapter extends RecyclerView.Adapter<AskAndAnswer_2_adapter.ViewHolder> {

    private List<AskAndAnswer_2_model> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    AskAndAnswer_2_adapter(Context context, List<AskAndAnswer_2_model> data) {
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
        final AskAndAnswer_2_model model= mData.get(position);
        holder.Time.setText(model.getTime());
        holder.Name.setText(model.getName());
        holder.Question.setText(model.getQuestion());
        Picasso.get().load(model.getProfil()).into(holder.ProfilImage);
        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mInflater.getContext(),AskAndAnswer_3.class);
                i.putExtra("uid",model.getUid());
                i.putExtra("question",model.getQuestion());
                i.putExtra("time",model.getTime());
                i.putExtra("profil",model.getProfil());
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
        CircleImageView ProfilImage;
        TextView Name;
        TextView Question;
        TextView Time;
        CardView Card;
        ViewHolder(View itemView) {
            super(itemView);
            Card = (CardView)itemView.findViewById(R.id.card);
            ProfilImage = (CircleImageView)itemView.findViewById(R.id.profilimage);
            Name = (TextView)itemView.findViewById(R.id.name);
            Question = (TextView)itemView.findViewById(R.id.question);
            Time = (TextView)itemView.findViewById(R.id.time);
        }
    }

    // convenience method for getting data at click position
    AskAndAnswer_2_model getItem(int id) {
        return mData.get(id);
    }

}