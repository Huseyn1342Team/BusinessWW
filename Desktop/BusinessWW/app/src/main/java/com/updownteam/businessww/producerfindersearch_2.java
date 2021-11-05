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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class producerfindersearch_2 extends AppCompatActivity {
    private ScrollView InfoScroll;
    private RecyclerView PostList;
    private LinearLayout ScaleInfo,ScaleRequest,Info,Request;
    private String ReceiverUid;
    private CircleImageView ProfilImage;
    private TextView Name;
    private ArrayList<Activity_1_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producerfindersearch_2);
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
        InfoScroll = (ScrollView)findViewById(R.id.infoscroll);
        PostList = (RecyclerView)findViewById(R.id.postlist);
        ScaleInfo = (LinearLayout)findViewById(R.id.scaleinfo);
        ScaleRequest = (LinearLayout)findViewById(R.id.scalerequest);
        Info = (LinearLayout)findViewById(R.id.info);
        Request = (LinearLayout)findViewById(R.id.request);
        ReceiverUid = getIntent().getStringExtra("uid");
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
                db1.child("Producer").child("Request").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Toast.makeText(producerfindersearch_2.this,"Process is finished",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setInfo(){
        final TextView Category = (TextView)findViewById(R.id.categorytext);
        final TextView Character = (TextView)findViewById(R.id.character);
        final TextView Experience = (TextView)findViewById(R.id.experience);
        final TextView Need = (TextView)findViewById(R.id.need);
        final TextView Concept = (TextView)findViewById(R.id.concept);
        final TextView Team = (TextView)findViewById(R.id.team);
        final TextView Marketing =(TextView)findViewById(R.id.marketing);
        final TextView Future = (TextView)findViewById(R.id.future);
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid).child("Producer");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String CategoryString  = dataSnapshot.child("category").getValue().toString();
               String CharacterStrign  = dataSnapshot.child("character").getValue().toString();
               String ExperienceString  = dataSnapshot.child("experience").getValue().toString();
               String NeedString  = dataSnapshot.child("need").getValue().toString();
               String ConceptString =dataSnapshot.child("concept").getValue().toString();
               String TeamString = dataSnapshot.child("team").getValue().toString();
               String MarketingString = dataSnapshot.child("marketing").getValue().toString();
               String FutureString  =dataSnapshot.child("future").getValue().toString();
               Category.setText(CategoryString);
               Character.setText(CharacterStrign);
               Experience.setText(ExperienceString);
               Need.setText(NeedString);
               Concept.setText(ConceptString);
               Team.setText(TeamString);
               Marketing.setText(MarketingString);
               Future.setText(FutureString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void Click(){
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoScroll.setVisibility(View.VISIBLE);
                ScaleInfo.setVisibility(View.VISIBLE);
                ScaleRequest.setVisibility(View.GONE);
                PostList.setVisibility(View.GONE);
            }
        });
        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleRequest.setVisibility(View.VISIBLE);
                PostList.setVisibility(View.VISIBLE);
                InfoScroll.setVisibility(View.GONE);
                ScaleInfo.setVisibility(View.GONE);
            }
        });
    }
    private void PostDisplay(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(producerfindersearch_2.this);
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
                Activity_1_adapter adapter = new Activity_1_adapter(producerfindersearch_2.this,list);
                PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
