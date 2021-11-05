package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Producer_5 extends AppCompatActivity {
    private EditText Team;
    private Button Submit;
    private String Character;
    private String Category;
    private String Price;
    private String Concept;
    private String TeamText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_5);
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
        Team = (EditText)findViewById(R.id.team);
        Submit = (Button)findViewById(R.id.submit);
        Character = getIntent().getStringExtra("character");
        Category = getIntent().getStringExtra("category");
        Price = getIntent().getStringExtra("price");
        Concept = getIntent().getStringExtra("concept");
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamText = Team.getText().toString();
                if(TeamText.matches("")){
                    Toast.makeText(Producer_5.this,"Sorry,you must write something",Toast.LENGTH_SHORT).show();
                    return;
                }
                final String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Producer")){
                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("Producer");
                            db1.child("team").setValue(TeamText);
                            Intent i =new Intent(Producer_5.this,InvestorMain2.class);
                            startActivity(i);
                        }else{
                            Intent i =new Intent(Producer_5.this,Producer_6.class);
                            i.putExtra("price",Price);
                            i.putExtra("character",Character);
                            i.putExtra("category",Category);
                            i.putExtra("concept",Concept);
                            i.putExtra("team",TeamText);
                            startActivity(i);
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
