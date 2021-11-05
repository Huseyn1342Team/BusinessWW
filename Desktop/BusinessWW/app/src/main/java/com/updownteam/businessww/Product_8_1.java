package com.updownteam.businessww;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Product_8_1 extends AppCompatActivity {
    //category,character,price,experience,need,concept,team,marketing,future
    private String Category,Character,Price,Experience,Need,Concept,Team,Marketing,Future;
    private Spinner Countries;
    private Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_8_1);
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
        Countries = (Spinner)findViewById(R.id.countries);
        Submit = (Button)findViewById(R.id.submit);
        Category = getIntent().getStringExtra("category");
        Character = getIntent().getStringExtra("character");
        Price = getIntent().getStringExtra("price");
        Experience = getIntent().getStringExtra("experience");
        Need = getIntent().getStringExtra("need");
        Concept = getIntent().getStringExtra("concept");
        Team = getIntent().getStringExtra("team");
        Marketing = getIntent().getStringExtra("marketing");
        Future = getIntent().getStringExtra("future");
        Locale[] locale = Locale.getAvailableLocales();
        final ArrayList<String> countries = new ArrayList<String>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
        Countries.setAdapter(adapter);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //category,character,price,experience,need,concept,team,marketing,future
                String countrytext = Countries.getSelectedItem().toString();
                if(countrytext.matches("")){
                    Toast.makeText(Product_8_1.this,"Sorry, you must write something",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i =new Intent(Product_8_1.this,Producer_9.class);
                i.putExtra("category",Category);
                i.putExtra("character",Character);
                i.putExtra("price",Price);
                i.putExtra("experience",Experience);
                i.putExtra("need",Need);
                i.putExtra("concept",Concept);
                i.putExtra("team",Team);
                i.putExtra("marketing",Marketing);
                i.putExtra("future",Future);
                i.putExtra("country",countrytext);
                startActivity(i);
            }
        });
    }
}
