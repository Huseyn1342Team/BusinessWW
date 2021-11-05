package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InvestorFinder1 extends AppCompatActivity {
    private CardView Investor;
    private CardView Producer;
    private BottomNavigationView Navigetion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_finder1);
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
                        Intent  i =new Intent(InvestorFinder1.this,SocialMedia1.class);
                        startActivity(i);
                        return false;
                    case R.id.questions:
                        Intent av =new Intent(InvestorFinder1.this,AskAndAnswer_1.class);
                        startActivity(av);
                        return false;
                    case R.id.investors:
                        Intent c = new Intent(InvestorFinder1.this,InvestorFinder1.class);
                        startActivity(c);
                        return false;
                    case R.id.profile:
                        Intent cdf = new Intent(InvestorFinder1.this,ProfilGuest.class);
                        cdf.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        startActivity(cdf);
                        return false;
                    case R.id.settings:
                        Intent b =new Intent(InvestorFinder1.this,Settings.class);
                        startActivity(b);
                        return false;
                }
                return false;
            }
        });
        Investor = (CardView)findViewById(R.id.investors);
        Producer = (CardView)findViewById(R.id.producer);
        Investor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MyUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUID);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Investor")){
                            Intent i = new Intent(InvestorFinder1.this,InvestorMain.class);
                            startActivity(i);
                        }
                        else{
                            Intent i = new Intent(InvestorFinder1.this,Investor_1.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        Producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MyUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUID);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Producer")){
                            Intent i = new Intent(InvestorFinder1.this,InvestorMain2.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Intent i = new Intent(InvestorFinder1.this,Producer_1.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
