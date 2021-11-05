package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

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

public class AskAndAnswer_2 extends AppCompatActivity {
    private String Category;
    private RecyclerView PostList;
    private Button AskAQuestion;
    private ArrayList<AskAndAnswer_2_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_and_answer_2);
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
        final ProgressDialog csprogress=new ProgressDialog(AskAndAnswer_2.this);
        csprogress.setMessage("Please wait a moment...");
        csprogress.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                csprogress.dismiss();
            }
        }, 5000);
        AskAQuestion = (Button)findViewById(R.id.askaquestion);
        PostList = (RecyclerView)findViewById(R.id.postlist);
        Category = getIntent().getStringExtra("category");
        PostDisplay();
        AskAQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AskAndAnswer_2.this,CreateQuestion_1.class);
                startActivity(i);
            }
        });
    }
    private void PostDisplay(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(AskAndAnswer_2.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String MyUid = user.getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");//.child(MyUid).child("Question");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot2:dataSnapshot1.child("Question").getChildren()) {
                        AskAndAnswer_2_model model = dataSnapshot2.getValue(AskAndAnswer_2_model.class);
                        list.add(model);
                    }
                }
                AskAndAnswer_2_adapter adapter = new AskAndAnswer_2_adapter(AskAndAnswer_2.this,list);
                PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
