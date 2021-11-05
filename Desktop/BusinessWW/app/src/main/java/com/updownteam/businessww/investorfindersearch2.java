package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class investorfindersearch2 extends AppCompatActivity {
    private RecyclerView PostList;
    private String ReceiverUid;
    private CircleImageView ProfilImage;
    private TextView Name;
    private ArrayList<Activity_1_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investorfindersearch2);
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
        PostList = (RecyclerView)findViewById(R.id.postlist);
        ReceiverUid = getIntent().getStringExtra("uid");
        ProfilImage = (CircleImageView)findViewById(R.id.profilimage);
        Name = (TextView)findViewById(R.id.name);
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ProfilString  = dataSnapshot.child("profil").getValue().toString();
                String NameString =dataSnapshot.child("name").getValue().toString();
                Picasso.get().load(ProfilString).into(ProfilImage);
                Name.setText(NameString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Click();
        PostDisplay();
        setInfo();
        sendRequest();
    }
    private void sendRequest(){
        Button Submit = (Button)findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid);
                db1.child("Investor").child("Request").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Toast.makeText(investorfindersearch2.this,"Process is finished",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Click(){
        LinearLayout Info = (LinearLayout)findViewById(R.id.info);
        LinearLayout Request = (LinearLayout)findViewById(R.id.request);
        final LinearLayout ScaleInfo = (LinearLayout)findViewById(R.id.scaleinfo);
        final LinearLayout ScaleRequest = (LinearLayout)findViewById(R.id.scalerequest);
        final ScrollView InfoScroll = (ScrollView)findViewById(R.id.infoscroll);
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleInfo.setVisibility(View.VISIBLE);
                InfoScroll.setVisibility(View.VISIBLE);
                ScaleRequest.setVisibility(View.GONE);
                PostList.setVisibility(View.GONE);
            }
        });
        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleInfo.setVisibility(View.GONE);
                InfoScroll.setVisibility(View.GONE);
                ScaleRequest.setVisibility(View.VISIBLE);
                PostList.setVisibility(View.VISIBLE);
            }
        });
    }
    private void PostDisplay(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(investorfindersearch2.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid).child("MyPosts");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Activity_1_model model = dataSnapshot1.getValue(Activity_1_model.class);
                    list.add(model);
                }
                Activity_1_adapter adapter = new Activity_1_adapter(investorfindersearch2.this,list);
                PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setInfo(){
        final TextView Category = (TextView)findViewById(R.id.categorytext);
        final TextView Character = (TextView)findViewById(R.id.character);
        final TextView Experience = (TextView)findViewById(R.id.experience);
        final TextView Price = (TextView)findViewById(R.id.price);
        final TextView Country = (TextView)findViewById(R.id.country);
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid).child("Investor");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String CategoryString  = dataSnapshot.child("category").getValue().toString();
                String CharacterString  = dataSnapshot.child("character").getValue().toString();
                String ExperienceString  = dataSnapshot.child("experience").getValue().toString();
                String PriceString  = dataSnapshot.child("price").getValue().toString();
                String CountryString =dataSnapshot.child("country").getValue().toString();
                Category.setText(CategoryString);
                Character.setText(CharacterString);
                Experience.setText(ExperienceString);
                Price.setText(PriceString);
                Country.setText(CountryString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
