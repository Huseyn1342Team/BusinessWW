package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import java.util.Calendar;

public class CreateQuestion_2 extends AppCompatActivity {
    private EditText Question;
    private String Category;
    private Button Submit;
    private String Name;
    private String Profil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question_2);
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
        Submit = (Button)findViewById(R.id.submit);
        Category = getIntent().getStringExtra("category");
        Question = (EditText)findViewById(R.id.question);
        String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference dt = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
        dt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Name = dataSnapshot.child("name").getValue().toString();
                Profil = dataSnapshot.child("profil").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String QuestionString  = Question.getText().toString();
                if(QuestionString.matches("")){
                    Toast.makeText(CreateQuestion_2.this,"Sorry, you must write something",Toast.LENGTH_SHORT).show();
                    return;
                }
                String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Calendar calendar = Calendar.getInstance();
                String hour  =String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                String time = String.valueOf(calendar.get(Calendar.MINUTE));
                String Date = hour+":"+time;
                String head = QuestionString.substring(0,1);
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
                db1.child("Question").child(head+Date).child("question").setValue(QuestionString);
                db1.child("Question").child(head+Date).child("name").setValue(Name);
                db1.child("Question").child(head+Date).child("time").setValue(Date);
                db1.child("Question").child(head+Date).child("profil").setValue(Profil);
                db1.child("Question").child(head+Date).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                db1.child("Question").child(head+Date).child("category").setValue(Category);
                Intent i=new Intent(CreateQuestion_2.this,AskAndAnswer_1.class);
                startActivity(i);
            }
        });
    }
}
