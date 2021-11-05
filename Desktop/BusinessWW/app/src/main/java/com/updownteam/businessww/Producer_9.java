package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Producer_9 extends AppCompatActivity {
    private EditText Future;
    private Button Submit;
    private String FutureText;
    private String Category;
    private String Character;
    private String Price;
    private String Experience;
    private String Need;
    private String Concept;
    private String Team;
    private String Marketing;
    private String Country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_9);
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
        Future = (EditText)findViewById(R.id.future);
        Submit = (Button)findViewById(R.id.submit);//category,character,price,experience,need,concept,team,marketing,future
        Category = getIntent().getStringExtra("category");
        Character = getIntent().getStringExtra("character");
        Price = getIntent().getStringExtra("price");
        Country = getIntent().getStringExtra("country");
        Experience = getIntent().getStringExtra("experience");
        Need = getIntent().getStringExtra("need");
        Concept = getIntent().getStringExtra("concept");
        Team = getIntent().getStringExtra("team");
        Marketing = getIntent().getStringExtra("marketing");
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FutureText = Future.getText().toString();
                if(FutureText.matches("")){
                    Toast.makeText(Producer_9.this,"Sorry, you must write something!",Toast.LENGTH_SHORT);
                    return;
                }
                final String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Producer")){
                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("Producer");
                            db1.child("future").setValue(FutureText);
                            Intent i =new Intent(Producer_9.this,InvestorMain2.class);
                            startActivity(i);
                        }else{
                            String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                            db1.child("producersearch").setValue(Country+Category+Price);
                            db1.child("Producer").child("category").setValue(Category);//
                            db1.child("Producer").child("character").setValue(Character);//
                            db1.child("Producer").child("price").setValue(Price);
                            db1.child("Producer").child("country").setValue(Country);
                            db1.child("Producer").child("experience").setValue(Experience);//
                            db1.child("Producer").child("need").setValue(Need);//
                            db1.child("Producer").child("concept").setValue(Concept);//
                            db1.child("Producer").child("team").setValue(Team);//
                            db1.child("Producer").child("marketing").setValue(Marketing);//
                            db1.child("Producer").child("future").setValue(FutureText);//
                            Intent i =new Intent(Producer_9.this,InvestorMain2.class);
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
