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

public class Comment_adapter extends RecyclerView.Adapter<Comment_adapter.ViewHolder> {

    private List<Comment_model> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    Comment_adapter(Context context, List<Comment_model> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.comment_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Comment_model model= mData.get(position);
        holder.Name.setText(model.getName());
        holder.Comment.setText(model.getComment());
        Picasso.get().load(model.getProfil()).into(holder.Profil);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView Profil;
        TextView Name;
        TextView Comment;
        ViewHolder(View itemView) {
            super(itemView);
            Profil = (CircleImageView)itemView.findViewById(R.id.profil);
            Name = (TextView)itemView.findViewById(R.id.name);
            Comment=(TextView)itemView.findViewById(R.id.comment);
        }
    }

    // convenience method for getting data at click position
    Comment_model getItem(int id) {
        return mData.get(id);
    }

}
