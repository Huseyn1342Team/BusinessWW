package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;


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

public class InvestorMain2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private LinearLayout Info,Request,Search;
    private LinearLayout ScaleInfo,ScalerRequest,ScaleSearch;
    private ScrollView InfoScroll,RequestScroll,SearchScroll;
    private TextView Category;
    private String CategoryText;
    private TextView Character;
    private String CharacterString;
    private TextView Experience;
    private String ExperienceText;
    private TextView Need,Concept,Team,Marketing,Future,Price,Country;
    private String NeedText,ConceptText,TeamText,MarketingText,FutureText,PriceText,CountryText;
    private String[] category = {"Agriculture","Education","Machine Learning","Artificial Inteligence","App development","Restaurant business","Hardware product"};
    private RecyclerView PostList;
    private EditText Amount;
    private Spinner CountrySpinner;
    private Spinner CategorySpinner;
    private ArrayList<InvestorMain_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_main2);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(InvestorMain2.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String MyUid = user.getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("Producer").child("Request");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    InvestorMain_model model = dataSnapshot1.getValue(InvestorMain_model.class);
                    list.add(model);
                }
                InvestorMain_Adapter adapter = new InvestorMain_Adapter(InvestorMain2.this,list);
                PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        InfoScroll = (ScrollView)findViewById(R.id.infoscroll);
        RequestScroll = (ScrollView)findViewById(R.id.requestscroll);
        SearchScroll = (ScrollView)findViewById(R.id.searchscroll);
        ScaleSearch = (LinearLayout)findViewById(R.id.scalesearch);
        ScalerRequest = (LinearLayout)findViewById(R.id.scalerequest);
        Info = (LinearLayout)findViewById(R.id.info);
        Request = (LinearLayout)findViewById(R.id.request);
        Search = (LinearLayout)findViewById(R.id.search);
        ScaleInfo = (LinearLayout)findViewById(R.id.scaleinfo);
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleInfo.setVisibility(View.VISIBLE);
                InfoScroll.setVisibility(View.VISIBLE);
                RequestScroll.setVisibility(View.GONE);
                SearchScroll.setVisibility(View.GONE);
                ScalerRequest.setVisibility(View.GONE);
                ScaleSearch.setVisibility(View.GONE);
            }
        });
        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScalerRequest.setVisibility(View.VISIBLE);
                RequestScroll.setVisibility(View.VISIBLE);
                InfoScroll.setVisibility(View.GONE);
                ScaleInfo.setVisibility(View.GONE);
                SearchScroll.setVisibility(View.GONE);
                ScaleSearch.setVisibility(View.GONE);
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleSearch.setVisibility(View.VISIBLE);
                SearchScroll.setVisibility(View.VISIBLE);
                InfoScroll.setVisibility(View.GONE);
                RequestScroll.setVisibility(View.GONE);
                ScaleInfo.setVisibility(View.GONE);
                ScalerRequest.setVisibility(View.GONE);
            }
        });
        final ProgressDialog csprogress=new ProgressDialog(InvestorMain2.this);
        csprogress.setMessage("Please wait a moment...");
        csprogress.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                csprogress.dismiss();
            }
        }, 5000);
        Amount = (EditText)findViewById(R.id.amount);
        CountrySpinner = (Spinner)findViewById(R.id.countryspinner);
        CategorySpinner = (Spinner)findViewById(R.id.category);
        CategorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CategorySpinner.setAdapter(aa);
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
        PostList = (RecyclerView)findViewById(R.id.postlist);
        Category = (TextView)findViewById(R.id.categorytext);
        Character = (TextView)findViewById(R.id.character);
        Experience = (TextView)findViewById(R.id.experience);
        Need = (TextView)findViewById(R.id.need);
        Concept = (TextView)findViewById(R.id.concept);
        Team = (TextView)findViewById(R.id.team);
        Marketing = (TextView)findViewById(R.id.marketing);
        Future = (TextView)findViewById(R.id.future);
        Price = (TextView)findViewById(R.id.price);
        Country = (TextView)findViewById(R.id.country);
        Country.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        //Price,Character,Category,Concept,Team,Experience,Need,Marketing,Future
        Price.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain2.this,Producer_1.class);
                startActivity(i);
                return false;
            }
        });
        Future.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain2.this,Producer_9.class);
                startActivity(i);
                return false;
            }
        });
        Marketing.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain2.this,Producer_8.class);
                startActivity(i);
                return false;
            }
        });
        Team.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain2.this,Producer_5.class);
                startActivity(i);
                return false;
            }
        });
        Concept.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        Need.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain2.this,Producer_7.class);
                startActivity(i);
                return false;
            }
        });
        Experience.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain2.this,Producer_6.class);
                startActivity(i);
                return false;
            }
        });
        Concept.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain2.this,Producer_4.class);
                startActivity(i);
                return false;
            }
        });
        Character.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain2.this,Producer_2.class);
                startActivity(i);
                return false;
            }
        });
        Category.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i =new Intent(InvestorMain2.this,Producer_3.class);
                startActivity(i);
                return false;
            }
        });
        String MyUid10 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db10 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid10);
        db10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CategoryText = dataSnapshot.child("Producer").child("category").getValue().toString();
                CharacterString = dataSnapshot.child("Producer").child("character").getValue().toString();
                ExperienceText = dataSnapshot.child("Producer").child("experience").getValue().toString();
                NeedText = dataSnapshot.child("Producer").child("need").getValue().toString();
                ConceptText = dataSnapshot.child("Producer").child("concept").getValue().toString();
                TeamText = dataSnapshot.child("Producer").child("team").getValue().toString();
                MarketingText = dataSnapshot.child("Producer").child("marketing").getValue().toString();
                FutureText = dataSnapshot.child("Producer").child("future").getValue().toString();
                PriceText = dataSnapshot.child("Producer").child("price").getValue().toString();
                CountryText = dataSnapshot.child("Producer").child("country").getValue().toString();
                Category.setText(CategoryText);
                Character.setText(CharacterString);
                Experience.setText(ExperienceText);
                Need.setText(NeedText);
                Concept.setText(ConceptText);
                Team.setText(TeamText);
                Marketing.setText(MarketingText);
                Future.setText(FutureText);
                Price.setText(PriceText);
                Country.setText(CountryText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Search();
    }
    private void Search(){
        Button Submit = (Button)findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AmountText = Amount.getText().toString();
                String CountryText = CountrySpinner.getSelectedItem().toString();
                String CategoryText = CategorySpinner.getSelectedItem().toString();
                Intent i = new Intent(InvestorMain2.this,investorfindersearch1.class);
                i.putExtra("amount",AmountText);
                i.putExtra("country",CountryText);
                i.putExtra("category",CategoryText);
                startActivity(i);
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
