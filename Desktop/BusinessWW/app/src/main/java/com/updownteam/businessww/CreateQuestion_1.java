package com.updownteam.businessww;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class CreateQuestion_1 extends AppCompatActivity {
    private CardView ComputerScience,Art,Social,Policy,Education,Environment,History,Hacking,Medicine,Work,University,Admission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question_1);
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
        ComputerScience = (CardView)findViewById(R.id.computerscience);//
        Art = (CardView)findViewById(R.id.art);//
        Social = (CardView)findViewById(R.id.social);//
        Policy = (CardView)findViewById(R.id.policy);//
        Education = (CardView)findViewById(R.id.education);
        Environment = (CardView)findViewById(R.id.environment);
        History = (CardView)findViewById(R.id.history);
        Hacking = (CardView)findViewById(R.id.hacking);
        Medicine =(CardView)findViewById(R.id.medicine);
        Work = (CardView)findViewById(R.id.work);
        University = (CardView)findViewById(R.id.university);
        Admission = (CardView)findViewById(R.id.admission);
        ComputerScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","computerscience");
                startActivity(i);
            }
        });
        Art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","art");
                startActivity(i);
            }
        });//Social,Policy,Education,Environment,History,Hacking,Medicine,Work,University,Admission
        Social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","social");
                startActivity(i);
            }
        });
        Policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","policy");
                startActivity(i);
            }
        });
        Education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","education");
                startActivity(i);
            }
        });
        Environment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","environment");
                startActivity(i);
            }
        });//History,Hacking,Medicine,Work,University,Admission
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","history");
                startActivity(i);
            }
        });
        Hacking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","hacking");
                startActivity(i);
            }
        });
        Medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","medicine");
                startActivity(i);
            }
        });//Work, University,Admission
        Work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","work");
                startActivity(i);
            }
        });
        University.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","university");
                startActivity(i);
            }
        });
        Admission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CreateQuestion_1.this,CreateQuestion_2.class);
                i.putExtra("category","admission");
                startActivity(i);
            }
        });
    }
}
