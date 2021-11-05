package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AskAndAnswer_3 extends AppCompatActivity {
    private RecyclerView PostList;
    private String UidString,QuestionString,TimeString,ProfilString;
    private CircleImageView ProfilImage;
    private TextView Name;
    private TextView Time, Question;
    private String Head;
    private ArrayList<AskAndAnswer_3_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_and_answer_3);
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
        final ProgressDialog csprogress=new ProgressDialog(AskAndAnswer_3.this);
        csprogress.setMessage("Please wait a moment...");
        csprogress.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                csprogress.dismiss();
            }
        }, 5000);
        UidString = getIntent().getStringExtra("uid");
        QuestionString = getIntent().getStringExtra("question");
        Head = QuestionString.substring(0,1);
        TimeString = getIntent().getStringExtra("time");
        ProfilString = getIntent().getStringExtra("profil");
        PostList = (RecyclerView)findViewById(R.id.postlist);
        ProfilImage = (CircleImageView)findViewById(R.id.profilimage);
        Name = (TextView)findViewById(R.id.name);
        Time = (TextView)findViewById(R.id.time);
        Question = (TextView)findViewById(R.id.question);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AskAndAnswer_3.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String MyUid = user.getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(UidString).child("Question").child(Head+TimeString).child("Comments");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    AskAndAnswer_3_model model = dataSnapshot1.getValue(AskAndAnswer_3_model.class);
                    list.add(model);
                }
                AskAndAnswer_3_adapter adapter = new AskAndAnswer_3_adapter(AskAndAnswer_3.this,list);
                PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        SendComment();
        setInfo();
    }
    private void setInfo(){
        CircleImageView ProfilImage = (CircleImageView)findViewById(R.id.profilimage);
        final TextView Name = (TextView)findViewById(R.id.name);
        TextView Time = (TextView)findViewById(R.id.time);
        TextView Question = (TextView)findViewById(R.id.question);
        Picasso.get().load(ProfilString).into(ProfilImage);
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String NameText = dataSnapshot.child("name").getValue().toString();
                Name.setText(NameText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Time.setText(TimeString);
        Question.setText(QuestionString);
    }
    private void SendComment(){
        final CircleImageView Profil = (CircleImageView)findViewById(R.id.profil);
        final TextView Name2 = (TextView)findViewById(R.id.name2);
        final String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ProfilText = dataSnapshot.child("profil").getValue().toString();
                String NameText = dataSnapshot.child("name").getValue().toString();
                Name2.setText(NameText);
                Picasso.get().load(ProfilText).into(Profil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final EditText Comment = (EditText)findViewById(R.id.comment);
        Button Send = (Button)findViewById(R.id.send);
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                String minuter = String.valueOf(calendar.get(Calendar.MINUTE));
                String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                String date = hour+":"+minuter;
                String CommentString  = Comment.getText().toString();
                String Commentboy = CommentString.substring(0,1);
                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(UidString).child("Question").child(Head+TimeString).child("Comments");
                db1.child(Commentboy+date).child("comment").setValue(CommentString);
                db1.child(Commentboy+date).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                db1.child(Commentboy+date).child("time").setValue(date);
                Comment.setText("");
            }
        });
    }
}
