package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilGuest extends AppCompatActivity {
    private CircleImageView Profil;
    private TextView Name;
    private RecyclerView PostList;
    private ArrayList<Activity_1_model> list;
    private Button AddFriend;
    private String Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_guest);
        AdView adView1 = (AdView)findViewById(R.id.adview1);
        AdView adView2 = (AdView)findViewById(R.id.adview2);
        MobileAds.initialize(this,"ca-app-pub-1659692064472439~8567553602");
        AdRequest adRequest = new AdRequest.Builder().build();
        // adView1 = new AdView(this);
        //adView2 = new AdView(this);
        //adView1.setAdSize(AdSize.BANNER);
        //adView1.setAdUnitId("ca-app-pub-3940256099942544/6300978111");//
        //adView2.setAdSize(AdSize.BANNER);
        //adView2.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView1.loadAd(adRequest);
        adView2.loadAd(adRequest);
        BottomNavigationView BotttomNavigationview = (BottomNavigationView)findViewById(R.id.navigation);
        BotttomNavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.socialmedia:
                        Intent i =new Intent(ProfilGuest.this,SocialMedia1.class);
                        startActivity(i);
                        return false;
                    case R.id.questions:
                        Intent av =new Intent(ProfilGuest.this,AskAndAnswer_1.class);
                        startActivity(av);
                        return false;
                    case R.id.investors:
                        Intent c = new Intent(ProfilGuest.this,InvestorFinder1.class);
                        startActivity(c);
                        return false;
                    case R.id.profile:
                        Intent cdf = new Intent(ProfilGuest.this,ProfilGuest.class);
                        cdf.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        startActivity(cdf);
                        return false;
                    case R.id.settings:
                        Intent b =new Intent(ProfilGuest.this,Settings.class);
                        startActivity(b);
                        return false;
                }
                return false;
            }
        });
        Uid = getIntent().getStringExtra("uid");
        Profil = (CircleImageView)findViewById(R.id.profil);
        Name = (TextView)findViewById(R.id.name);
        PostList = (RecyclerView)findViewById(R.id.postlist);
        AddFriend = (Button)findViewById(R.id.addfriend);
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(Uid.matches(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    AddFriend.setVisibility(View.GONE);
                }
                if(dataSnapshot.child("FriendRequest").hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    AddFriend.setText("Request Sented");
                    AddFriend.setClickable(false);
                }
                if(dataSnapshot.child("Friends").hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    AddFriend.setText("Your friend");
                    AddFriend.setClickable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Name();
        Profil();
        AddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriend();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProfilGuest.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String MyUid = user.getUid();
        DatabaseReference db10 = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid).child("MyPosts");
        db10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Activity_1_model model = dataSnapshot1.getValue(Activity_1_model.class);
                    list.add(model);
                }
                Activity_1_adapter adapter = new Activity_1_adapter(ProfilGuest.this,list);
                PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void Name(){
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("name")){
                    String Namet = dataSnapshot.child("name").getValue().toString();
                    Name.setText(Namet);
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void Profil(){
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("profil")) {
                    String Namet = dataSnapshot.child("profil").getValue().toString();
                    Picasso.get().load(Namet).into(Profil);
                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void AddFriend(){
        String MyUid = FirebaseAuth.getInstance().getCurrentUser().toString();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        db1.child("FriendRequest").child(MyUid).child("uid").setValue(MyUid);
    }
}
