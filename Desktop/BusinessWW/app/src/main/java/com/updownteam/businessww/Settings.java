package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

public class Settings extends AppCompatActivity {
    private ImageView LogOutImage,MoneyImage,InfoProducerImage,InfoInvestorImage,QuestionImage,AnswerImage,ProducerImage,ProfilImage,AboutUsImage,FriendRequestImage,ProducerRequestImage,InvestorRequestImage;
    private ImageView FriendImage;
    private TextView Friend,Money,Producer,Profil,AboutUs,FriendRequest,LogOut,InfoProducer,InfoInvestor,ProducerRequest,InvestorRequest,Question,Answer;
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        InfoProducer = (TextView)findViewById(R.id.infoproducer);
        InfoInvestor = (TextView)findViewById(R.id.infoinvestor);
        InfoInvestorImage = (ImageView)findViewById(R.id.infoinvestorimage);
        InfoProducerImage = (ImageView)findViewById(R.id.infoproducerimage);
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
        QuestionImage = (ImageView)findViewById(R.id.questionimage);
        Question = (TextView)findViewById(R.id.question);
        InvestorRequest = (TextView)findViewById(R.id.investorrequest);
        InvestorRequestImage = (ImageView)findViewById(R.id.investorrequestimage);
        ProducerRequest = (TextView)findViewById(R.id.producerrequest);
        ProducerRequestImage = (ImageView)findViewById(R.id.producerrequestimage);
        LogOut = (TextView)findViewById(R.id.logout);
        LogOutImage = (ImageView)findViewById(R.id.logoutimage);
        FriendRequestImage = (ImageView)findViewById(R.id.friendrequestimage);
        FriendRequest =(TextView)findViewById(R.id.friendrequest);
        AboutUs = (TextView)findViewById(R.id.aboutus);
        AboutUsImage = (ImageView)findViewById(R.id.aboutusimage);
        Profil = (TextView)findViewById(R.id.profil);
        ProfilImage = (ImageView)findViewById(R.id.profilimage);
        Producer = (TextView)findViewById(R.id.producer);
        ProducerImage = (ImageView)findViewById(R.id.producerimage);
        FriendImage = (ImageView)findViewById(R.id.friendimage);
        Money = (TextView)findViewById(R.id.money);
        MoneyImage = (ImageView)findViewById(R.id.moneyimage);
        InfoProducerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Producer")){
                            Intent i =new Intent(Settings.this,InvestorMain2.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(Settings.this,"Sorry, you must register for producer",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        InfoProducer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Producer")){
                            Intent i =new Intent(Settings.this,InvestorMain2.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(Settings.this,"Sorry, you must register for producer",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        InfoInvestorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Investor")){
                            Intent i =new Intent(Settings.this,InvestorMain.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(Settings.this,"Sorry, you must register for investor",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        InfoInvestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                db1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Investor")){
                            Intent i =new Intent(Settings.this,InvestorMain.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(Settings.this,"Sorry, you must register for investor",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Question_settings.class);
                startActivity(i);
            }
        });
        QuestionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Question_settings.class);
                startActivity(i);
            }
        });
        ProducerRequestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,ProducerRequest_settings.class);
                startActivity(i);
            }
        });
        ProducerRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,ProducerRequest_settings.class);
                startActivity(i);
            }
        });
        InvestorRequestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,InvestorRequest_settings.class);
                startActivity(i);
            }
        });
        InvestorRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,InvestorRequest_settings.class);
                startActivity(i);
            }
        });
        LogOutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i =new Intent(Settings.this,Register.class);
                startActivity(i);
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i =new Intent(Settings.this,Register.class);
                startActivity(i);
            }
        });
        FriendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,FriendRequest_settings.class);
                startActivity(i);
            }
        });
        FriendRequestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,FriendRequest_settings.class);
                startActivity(i);
            }
        });
        AboutUsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,AboutUs_settings.class);
                startActivity(i);
            }
        });
        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,AboutUs_settings.class);
                startActivity(i);
            }
        });
        ProfilImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Profil_settings.class);
                startActivity(i);
            }
        });
        Profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Profil_settings.class);
                startActivity(i);
            }
        });
        ProducerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Producer_settings.class);
                startActivity(i);
            }
        });
        Producer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Settings.this,Producer_settings.class);
                startActivity(i);
            }
        });
        MoneyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Investor_settings.class);
                startActivity(i);
            }
        });
        Money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Investor_settings.class);
                startActivity(i);
            }
        });
        FriendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this,Friend_setting.class);
                startActivity(i);
            }
        });
        Friend = (TextView)findViewById(R.id.friend);
        Friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this,Friend_setting.class);
                startActivity(i);
            }
        });
        BottomNavigationView BotttomNavigationview = (BottomNavigationView)findViewById(R.id.navigation);
        BotttomNavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.socialmedia:
                        Intent  i =new Intent(Settings.this,SocialMedia1.class);
                        startActivity(i);
                        return false;
                    case R.id.questions:
                        Intent av =new Intent(Settings.this,AskAndAnswer_1.class);
                        startActivity(av);
                        return false;
                    case R.id.investors:
                        Intent c = new Intent(Settings.this,InvestorFinder1.class);
                        startActivity(c);
                        return false;
                    case R.id.profile:
                        Intent cdf = new Intent(Settings.this,ProfilGuest.class);
                        cdf.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        startActivity(cdf);
                        return false;
                    case R.id.settings:
                        Intent b =new Intent(Settings.this,Settings.class);
                        startActivity(b);
                        return false;
                }
                return false;
            }
        });
        FriendRequestImage = (ImageView)findViewById(R.id.friendrequestimage);
        FriendRequest = (TextView)findViewById(R.id.friendrequest);
        LogOut = (TextView)findViewById(R.id.logout);
        LogOutImage = (ImageView)findViewById(R.id.logoutimage);
        LogOutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i =new Intent(Settings.this,LogIn.class);
                startActivity(i);
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent  i =new Intent(Settings.this,LogIn.class);
                startActivity(i);
            }
        });
        FriendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this, com.updownteam.businessww.FriendRequest.class);
                startActivity(i);
            }
        });
        FriendRequestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Settings.this, com.updownteam.businessww.FriendRequest.class);
                startActivity(i);
            }
        });
    }
}