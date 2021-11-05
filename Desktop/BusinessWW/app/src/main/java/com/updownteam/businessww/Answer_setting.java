package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

public class Answer_setting extends AppCompatActivity {
    private String Category;
    private RecyclerView PostList;
    private Button AskAQuestion;
    private String Receiver,Uid,Post,Comment;
    private ArrayList<AskAndAnswer_2_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_setting);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(Answer_setting.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String MyUid = user.getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("Reaction").hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        Uid = dataSnapshot1.child("Reaction").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("uid").getValue().toString();
                        Receiver = dataSnapshot1.child("Reaction").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("receiver").getValue().toString();
                        Post = dataSnapshot1.child("Reaction").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("post").getValue().toString();
                        Comment =dataSnapshot1.child("Reaction").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("comment").getValue().toString();
                        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(Receiver).child("Question").child(Post);
                        db1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                AskAndAnswer_2_model model = dataSnapshot.getValue(AskAndAnswer_2_model.class);
                                list.add(model);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                AskAndAnswer_2_adapter adapter = new AskAndAnswer_2_adapter(Answer_setting.this,list);
                PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
