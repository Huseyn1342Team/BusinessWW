package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class InvestorMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private LinearLayout Info;
    private LinearLayout Request;
    private ScrollView InfoScroll;
    private LinearLayout ScaleInfo;
    private LinearLayout ScaleRequest;
    private TextView Category;
    private TextView Character;
    private TextView Experience;
    private LinearLayout Search;
    private EditText Amount;
    private Spinner CountrySpinner;
    private Spinner CategorySpinner;
    private Button SubmitSearch;
    private ScrollView SearchScroll;
    private LinearLayout ScaleSearch;
    private TextView Country;
    private ArrayList<InvestorMain_model> list;
    private TextView Price;
    private ScrollView RequestScroll;
    private RecyclerView PostList;
    private String[] category = {"Agriculture","Education","Machine Learning","Artificial Inteligence","App development","Restaurant business","Hardware product"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_main);
        Toast.makeText(InvestorMain.this,"If you want change info,make a long click in info!",Toast.LENGTH_LONG).show();
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
        SubmitSearch = (Button)findViewById(R.id.submit);
        Amount = (EditText)findViewById(R.id.amount);
        CountrySpinner = (Spinner)findViewById(R.id.countryspinner);
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
        CountrySpinner.setAdapter(adapter);
        CategorySpinner = (Spinner)findViewById(R.id.category);
        CategorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CategorySpinner.setAdapter(aa);
        SearchScroll = (ScrollView)findViewById(R.id.searchscroll);
        Search = (LinearLayout)findViewById(R.id.search);
        ScaleSearch = (LinearLayout)findViewById(R.id.scalesearch);
        SubmitSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AmountText = Amount.getText().toString();
                String CountryText = CountrySpinner.getSelectedItem().toString();
                String CategoryText = CategorySpinner.getSelectedItem().toString();
                Intent i =new Intent(InvestorMain.this,producerfindersearch1.class);
                i.putExtra("amount",AmountText);
                i.putExtra("country",CountryText);
                i.putExtra("category",CategoryText);
                startActivity(i);
            }
        });
        PostList = (RecyclerView)findViewById(R.id.postlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(InvestorMain.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String MyUid = user.getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("Investor").child("Request");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    InvestorMain_model model = dataSnapshot1.getValue(InvestorMain_model.class);
                    list.add(model);
                }
                InvestorMain_Adapter adapter = new InvestorMain_Adapter(InvestorMain.this,list);
                PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        RequestScroll = (ScrollView)findViewById(R.id.requestscroll);
        Price = (TextView)findViewById(R.id.price);
        Country = (TextView)findViewById(R.id.country);
        Experience = (TextView)findViewById(R.id.experience);
        Character = (TextView)findViewById(R.id.character);
        Category = (TextView)findViewById(R.id.categorytext);
        InfoScroll = (ScrollView)findViewById(R.id.infoscroll);
        Info = (LinearLayout)findViewById(R.id.info);
        Request = (LinearLayout)findViewById(R.id.request);
        ScaleInfo = (LinearLayout)findViewById(R.id.scaleinfo);
        ScaleRequest  =(LinearLayout)findViewById(R.id.scalerequest);
        setInfo();
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleRequest.setVisibility(View.GONE);
                ScaleSearch.setVisibility(View.GONE);
                ScaleInfo.setVisibility(View.VISIBLE);
                InfoScroll.setVisibility(View.VISIBLE);
                RequestScroll.setVisibility(View.GONE);
                SearchScroll.setVisibility(View.GONE);
            }
        });
        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleInfo.setVisibility(View.GONE);
                ScaleSearch.setVisibility(View.GONE);
                ScaleRequest.setVisibility(View.VISIBLE);
                InfoScroll.setVisibility(View.GONE);
                RequestScroll.setVisibility(View.VISIBLE);
                SearchScroll.setVisibility(View.GONE);
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleRequest.setVisibility(View.GONE);
                ScaleInfo.setVisibility(View.GONE);
                ScaleSearch.setVisibility(View.VISIBLE);
                InfoScroll.setVisibility(View.GONE);
                RequestScroll.setVisibility(View.GONE);
                SearchScroll.setVisibility(View.VISIBLE);
            }
        });
        changeInfo();
    }
    private void changeInfo(){
        Character.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain.this,Investors_2.class);
                startActivity(i);
                return false;
            }
        });
        Category.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain.this,Investor_3.class);
                startActivity(i);
                return false;
            }
        });
        Experience.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain.this,Investor_4.class);
                startActivity(i);
                return false;
            }
        });
        Price.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain.this,Investor_1.class);
                startActivity(i);
                return false;
            }
        });
        Country.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain.this,Investor_5.class);
                startActivity(i);
                return false;
            }
        });
    }
    private void setInfo(){
        String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String category = dataSnapshot.child("Investor").child("category").getValue().toString();
                String character = dataSnapshot.child("Investor").child("character").getValue().toString();
                String country = dataSnapshot.child("Investor").child("country").getValue().toString();
                String experience = dataSnapshot.child("Investor").child("experience").getValue().toString();
                String price = dataSnapshot.child("Investor").child("price").getValue().toString();
                Category.setText(category);
                Character.setText(character);
                Country.setText(country);
                Experience.setText(experience);
                Price.setText(price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
