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
import java.util.zip.CheckedOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_1_adapter extends RecyclerView.Adapter<Activity_1_adapter.ViewHolder> {

    private List<Activity_1_model> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    Activity_1_adapter(Context context, List<Activity_1_model> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Activity_1_model model= mData.get(position);
        holder.Name.setText(model.getName());
        holder.Post.setText(model.getPost());
        holder.Date.setText(model.getDate());
        final String PostUid = model.getUid();
        final String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference a = FirebaseDatabase.getInstance().getReference().child("Users");
        a.addValueEventListener(new ValueEventListener() {
        @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.hasChild(model.getUid())) {
                if(dataSnapshot.child(model.getUid()).hasChild("FriendPost")) {
                    if (dataSnapshot.child(model.getUid()).child("FriendPost").child(model.getDate() + model.getPost().substring(0, 1)).hasChild("Comments")) {
                        int Comment = (int) dataSnapshot.child(model.getUid()).child("FriendPost").child(model.getDate() + model.getPost().substring(0, 1)).child("Comments").getChildrenCount();
                        holder.CommentText.setText(Comment + " " + "comments");
                    } else {
                        holder.CommentText.setText("0 comments");
                    }
                }
            }
            }

           @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference profilsekli = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
        holder.CommentSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Comment = holder.CommentEditText.getText().toString();
                if(Comment.matches("")){
                    Toast.makeText(mInflater.getContext(),"Please write some comment",Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseReference db1  = FirebaseDatabase.getInstance().getReference().child("Users").child(PostUid).child("FriendPost").child(model.getDate()+model.getPost().substring(0,1));
                DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(PostUid).child("MyPosts").child(model.getDate()+model.getPost().substring(0,1));
                HashMap<String,Object> put = new HashMap<>();
                put.put("name",holder.NameText);
                put.put("profil",holder.Profil);
                put.put("comment",Comment);
                db1.child("Comments").push().setValue(put);
                db2.child("Comments").push().setValue(put);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ProgressDialog csprogress=new ProgressDialog(mInflater.getContext());
                        csprogress.setMessage("Please wait a moment...");
                        csprogress.show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                csprogress.dismiss();
                                holder.CommentEditText.setText("");
                            }
                        }, 2000);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                db2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ProgressDialog csprogress=new ProgressDialog(mInflater.getContext());
                        csprogress.setMessage("Please wait a moment...");
                        csprogress.show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                csprogress.dismiss();
                                holder.CommentEditText.setText("");
                            }
                        }, 2000);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        profilsekli.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.Profil = dataSnapshot.child("profil").getValue().toString();
                Picasso.get().load(holder.Profil).into(holder.CommentProfilSekli);
                holder.NameText = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users");
        db2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.child(PostUid).child("FriendPost").child(model.getDate() + model.getPost().substring(0, 1)).hasChild("Likes")) {
                        int like = (int) dataSnapshot.child(PostUid).child("FriendPost").child(model.getDate()+model.getPost().substring(0,1)).child("Likes").getChildrenCount();
                        holder.AmountText.setText(String.valueOf(like) + " " + "likes");
                    } else {
                        holder.AmountText.setText("0 likes");
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.LikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(PostUid).child("MyPosts").child(model.getDate()+model.getPost().substring(0,1)).child("Likes");
                DatabaseReference db3 = FirebaseDatabase.getInstance().getReference().child("Users").child(PostUid).child("FriendPost").child(model.getDate()+model.getPost().substring(0,1)).child("Likes");
                db2.child(MyUid).setValue(MyUid);
                db3.child(MyUid).setValue(MyUid);
            }
        });
        holder.LikeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(PostUid).child("MyPosts").child(model.getDate()+model.getPost().substring(0,1));
                DatabaseReference db3 = FirebaseDatabase.getInstance().getReference().child("Users").child(PostUid).child("FriendPost").child(model.getDate()+model.getPost().substring(0,1));
                db2.child(MyUid).setValue(MyUid);
                db3.child(MyUid).setValue(MyUid);
            }
        });
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(PostUid).child("FriendPost").child(model.getDate() + model.getPost().substring(0, 1)).child("Likes").hasChild(MyUid)) {
                            holder.LikeButton.setImageResource(R.drawable.star);
                        } else {
                            holder.LikeButton.setImageResource(R.drawable.starborder);
                        }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.CommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.CommentList.setVisibility(View.VISIBLE);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mInflater.getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                holder.CommentList.setLayoutManager(layoutManager);
                holder.list = new ArrayList<>();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(PostUid).child("MyPosts").child(model.getDate()+model.getPost().substring(0,1)).child("Comments");
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.list.clear();
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            Comment_model model = dataSnapshot1.getValue(Comment_model.class);
                            if(model == null){
                                Toast.makeText(mInflater.getContext(),"There is no comment",Toast.LENGTH_LONG).show();
                                return;
                            }
                            holder.list.add(model);
                        }
                        Comment_adapter adapter = new Comment_adapter(mInflater.getContext(),holder.list);
                        holder.CommentList.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                holder.CommentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.CommentList.setVisibility(View.GONE);
                    }
                });
            }
        });
        if(model.getImage() == null){
            holder.Image.setVisibility(View.GONE);
        }else{
            holder.Image.setVisibility(View.VISIBLE);
            Picasso.get().load(model.getImage()).into(holder.Image);
        }
        Picasso.get().load(model.getProfilimage()).into(holder.ProfilImage);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Date;
        TextView Post;
        TextView CommentText;
        ImageView Image;
        ImageView LikeButton;
        TextView LikeText;
        CircleImageView ProfilImage;
        ImageView CommentButton;
        CircleImageView CommentProfilSekli;
        EditText CommentEditText;
        TextView AmountText;
        String NameText,Profil;
        RecyclerView CommentList;
        Button CommentSent;
        ArrayList<Comment_model> list;
        ViewHolder(View itemView) {
            super(itemView);
            CommentSent = (Button)itemView.findViewById(R.id.commentsent);
            Name = (TextView)itemView.findViewById(R.id.nameandsurname);
            CommentButton = (ImageView)itemView.findViewById(R.id.commentbutton);
            Date = (TextView)itemView.findViewById(R.id.date);
            CommentText = (TextView)itemView.findViewById(R.id.commenttext);
            AmountText = (TextView)itemView.findViewById(R.id.amounttext);
            Post = (TextView)itemView.findViewById(R.id.post);
            LikeButton=(ImageView)itemView.findViewById(R.id.likebutton);
            Image = (ImageView)itemView.findViewById(R.id.image);
            LikeText = (TextView)itemView.findViewById(R.id.liketext);
            ProfilImage = (CircleImageView) itemView.findViewById(R.id.profilimage);
            CommentList = (RecyclerView)itemView.findViewById(R.id.commentlist);
            CommentProfilSekli = (CircleImageView)itemView.findViewById(R.id.commentprofilsekli);
            CommentEditText = (EditText)itemView.findViewById(R.id.commentedittext);
        }
    }

    // convenience method for getting data at click position
    Activity_1_model getItem(int id) {
        return mData.get(id);
    }

}