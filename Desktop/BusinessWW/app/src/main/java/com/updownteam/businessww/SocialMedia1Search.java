package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SocialMedia1Search extends AppCompatActivity {
    private RecyclerView PostList;
    private String name;
    private ArrayList<SocialMediaSearch_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media1_search);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(SocialMedia1Search.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList = (RecyclerView)findViewById(R.id.postlist);
        PostList.setLayoutManager(layoutManager);
        name = getIntent().getStringExtra("name");
        if(name == null){
            list = new ArrayList<>();
            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");
            db1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        SocialMediaSearch_model model = dataSnapshot1.getValue(SocialMediaSearch_model.class);
                        list.add(model);
                    }
                    SocialMediaSearch_adapter adapter = new SocialMediaSearch_adapter(SocialMedia1Search.this,list);
                    PostList.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else{
            list = new ArrayList<>();
            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");
            db1.orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        SocialMediaSearch_model model = dataSnapshot1.getValue(SocialMediaSearch_model.class);
                        list.add(model);
                    }
                    SocialMediaSearch_adapter adapter = new SocialMediaSearch_adapter(SocialMedia1Search.this,list);
                    PostList.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
