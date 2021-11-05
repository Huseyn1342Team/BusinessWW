package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

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

public class investorfindersearch1 extends AppCompatActivity {
    private RecyclerView PostList;
    private String Amount;
    private String Category;
    private String Country;
    private ArrayList<investorfindersearch_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investorfindersearch1);
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
        final ProgressDialog csprogress=new ProgressDialog(investorfindersearch1.this);
        csprogress.setMessage("Please wait a moment...");
        csprogress.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                csprogress.dismiss();
            }
        }, 5000);
        PostList = (RecyclerView)findViewById(R.id.postlist);
        Amount = getIntent().getStringExtra("amount");
        Category = getIntent().getStringExtra("category");
        Country = getIntent().getStringExtra("country");
        Toast.makeText(investorfindersearch1.this, Country + Category + Amount, Toast.LENGTH_SHORT).show();
        PostDisplay();
    }
    private void PostDisplay(){
        PostList = (RecyclerView)findViewById(R.id.postlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(investorfindersearch1.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");
        db1.orderByChild("investorsearch").equalTo(Country+Category+Amount).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                  investorfindersearch_model model = dataSnapshot1.getValue(investorfindersearch_model.class);
                  list.add(model);
                  //  Activity_1_model model = dataSnapshot1.getValue(Activity_1_model.class);
                    // list.add(model);
                }
                investorfindersearch_adapter adapter = new investorfindersearch_adapter(investorfindersearch1.this,list);
                PostList.setAdapter(adapter);
                //Activity_1_adapter adapter = new Activity_1_adapter(SocialMedia1.this,list);
                //PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
