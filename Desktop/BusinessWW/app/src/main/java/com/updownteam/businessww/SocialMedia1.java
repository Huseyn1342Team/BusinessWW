package com.updownteam.businessww;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.IDNA;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class SocialMedia1 extends AppCompatActivity {
    private EditText Post;
    private ImageButton PutImage;
    private RecyclerView PostList;
    private ArrayList<Activity_1_model> list;
    private Button Submit1;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private List<String> people;
    private StorageReference storageReference;
    private Bitmap bitmap;
    private String ImageUrl;
    private FirebaseStorage storage;
    private AutoCompleteTextView Search;
    private String MonthText;
    private ImageButton SearchButton;
    private String Profil;
    private String Name;
    private String Surname;
    private BottomNavigationView BotttomNavigationview;
    private int a;
    private int b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_social_media1);
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
        BotttomNavigationview = (BottomNavigationView)findViewById(R.id.navigation);
        BotttomNavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.socialmedia:
                        Intent  i =new Intent(SocialMedia1.this,SocialMedia1.class);
                        startActivity(i);
                        finish();
                        return false;
                    case R.id.questions:
                        Intent av =new Intent(SocialMedia1.this,AskAndAnswer_1.class);
                        startActivity(av);
                        finish();
                        Toast.makeText(SocialMedia1.this,"Questions",Toast.LENGTH_SHORT).show();
                        return false;
                    case R.id.investors:
                        Intent c = new Intent(SocialMedia1.this,InvestorFinder1.class);
                        startActivity(c);
                        return false;
                    case R.id.profile:
                        Intent cdf = new Intent(SocialMedia1.this,ProfilGuest.class);
                        cdf.putExtra("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        startActivity(cdf);
                        Toast.makeText(SocialMedia1.this,"Profil",Toast.LENGTH_SHORT).show();
                        return false;
                    case R.id.settings:
                        Intent b =new Intent(SocialMedia1.this,Settings.class);
                        startActivity(b);
                        return false;
                }
                return false;
            }
        });
        final ProgressDialog csprogress=new ProgressDialog(SocialMedia1.this);
        csprogress.setMessage("Please wait a moment...");
        csprogress.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                csprogress.dismiss();
            }
        }, 5000);
        Search = (AutoCompleteTextView)findViewById(R.id.search);
        Search();
        SearchButton = (ImageButton)findViewById(R.id.searchbutton);
        Post = (EditText)findViewById(R.id.post);
        PutImage = (ImageButton) findViewById(R.id.putimage);
        Submit1 = (Button)findViewById(R.id.submit1);
        people = new ArrayList<String >();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String MyUid = user.getUid();
        DatabaseReference db3 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid);
        db3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profil = dataSnapshot.child("profil").getValue().toString();
                Name = dataSnapshot.child("name").getValue().toString();
                Surname = dataSnapshot.child("surname").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("Friends");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                people.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String name = dataSnapshot1.child("name").getValue().toString();
                    String uid = dataSnapshot1.child("uid").getValue().toString();
                    people.add(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,people);
        Search.setAdapter(adapter);
        PostDisplay();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = Search.getText().toString();
                Intent  i =new Intent(SocialMedia1.this,SocialMedia1Search.class);
                i.putExtra("name",a);
                startActivity(i);
            }
        });
        PutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
                a=5;
                b=1;
            }
        });
        Submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String posttext = Post.getText().toString();
                if(posttext.matches("")){
                    Toast.makeText(SocialMedia1.this,"Nəsə yazmalısınız!",Toast.LENGTH_LONG).show();
                    return;
                }
                UploadImage();
                final ProgressDialog csprogress=new ProgressDialog(SocialMedia1.this);
                csprogress.setMessage("Uploading...");
                csprogress.show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        csprogress.dismiss();
                        if(a==5){
                            if(b==5){
                                final String PostText = Post.getText().toString();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String myUid = user.getUid();
                                Calendar calendar = Calendar.getInstance();
                                String hour  =String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                                String time = String.valueOf(calendar.get(Calendar.MINUTE));
                                String day  = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                                String month = String.valueOf(calendar.get(Calendar.MONTH));
                                String year = String.valueOf(calendar.get(Calendar.YEAR));
                                if(month.matches("0") ){
                                    MonthText = "January";
                                }
                                if(month.matches("1")){
                                    MonthText = "February";
                                }
                                if(month.matches("2")){
                                    MonthText = "March";
                                }
                                if(month.matches("3")){
                                    MonthText = "April";
                                }
                                if(month.matches("4")){
                                    MonthText = "May";
                                }
                                if(month.matches("5")){
                                    MonthText = "June";
                                }
                                if(month.matches("6")){
                                    MonthText = "July";
                                }
                                if(month.matches("7")){
                                    MonthText = "August";
                                }
                                if(month.matches("8")){
                                    MonthText = "September";
                                }
                                if(month.matches("9")){
                                    MonthText = "October";
                                }
                                if(month.matches("10")){
                                    MonthText = "November";
                                }
                                if(month.matches("11")){
                                    MonthText = "December";
                                }
                                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(myUid).child("MyPosts");
                                DatabaseReference db2 =FirebaseDatabase.getInstance().getReference().child("Users").child(myUid).child("FriendPost");
                                final String date =  day + " "+ MonthText + ","+ " "+ hour+":"+time + " "+ year;
                                final String Head = date+PostText.substring(0,1);
                                String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DatabaseReference db10 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("Friends");
                                db10.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                            String ReceiverUid = dataSnapshot1.child("uid").getValue().toString();
                                            DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid).child("FriendPost");
                                            db2.child(Head).child("post").setValue(PostText);
                                            db2.child(Head).child("name").setValue(Name);
                                            db2.child(Head).child("date").setValue(date);
                                            db2.child(Head).child("Comments").setValue(Name);
                                            db2.child(Head).child("profilimage").setValue(Profil);
                                            db2.child(Head).child("surname").setValue(Surname);
                                            db2.child(Head).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                db1.child(Head).child("post").setValue(PostText);
                                db1.child(Head).child("name").setValue(Name);
                                db1.child(Head).child("date").setValue(date);
                                db1.child(Head).child("Comments").setValue(Name);
                                db2.child(Head).child("Comments").setValue(Name);
                                db1.child(Head).child("profilimage").setValue(Profil);
                                db1.child(Head).child("surname").setValue(Surname);
                                db1.child(Head).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                db2.child(Head).child("post").setValue(PostText);
                                db2.child(Head).child("name").setValue(Name);
                                db2.child(Head).child("date").setValue(date);
                                db2.child(Head).child("profilimage").setValue(Profil);
                                db2.child(Head).child("surname").setValue(Surname);
                                db2.child(Head).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            }else{
                                UploadImage();
                            }
                        }
                        else{
                            final String PostText = Post.getText().toString();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String myUid = user.getUid();
                            Calendar calendar = Calendar.getInstance();
                            String hour  =String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                            String time = String.valueOf(calendar.get(Calendar.MINUTE));
                            String day  = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                            String month = String.valueOf(calendar.get(Calendar.MONTH));
                            String year = String.valueOf(calendar.get(Calendar.YEAR));
                            if(month.matches("0") ){
                                MonthText = "January";
                            }
                            if(month.matches("1")){
                                MonthText = "February";
                            }
                            if(month.matches("2")){
                                MonthText = "March";
                            }
                            if(month.matches("3")){
                                MonthText = "April";
                            }
                            if(month.matches("4")){
                                MonthText = "May";
                            }
                            if(month.matches("5")){
                                MonthText = "June";
                            }
                            if(month.matches("6")){
                                MonthText = "July";
                            }
                            if(month.matches("7")){
                                MonthText = "August";
                            }
                            if(month.matches("8")){
                                MonthText = "September";
                            }
                            if(month.matches("9")){
                                MonthText = "October";
                            }
                            if(month.matches("10")){
                                MonthText = "November";
                            }
                            if(month.matches("11")){
                                MonthText = "December";
                            }
                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(myUid).child("MyPosts");
                            DatabaseReference db2 =FirebaseDatabase.getInstance().getReference().child("Users").child(myUid).child("FriendPost");
                            final String date =  day + " "+ MonthText + ","+ " "+ hour+":"+time + " "+ year;
                            final String Head = date+PostText.substring(0,1);
                            if(Head == null){

                            }
                            String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference db100 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("Friends");
                            db100.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                        String ReceiverUid = dataSnapshot1.child("uid").getValue().toString();
                                        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid).child("FriendPost");
                                        db2.child(Head).child("post").setValue(PostText);
                                        db2.child(Head).child("name").setValue(Name);
                                        db2.child(Head).child("date").setValue(date);
                                        db2.child(Head).child("Comments").setValue(Name);
                                        db2.child(Head).child("profilimage").setValue(Profil);
                                        db2.child(Head).child("surname").setValue(Surname);
                                        db2.child(Head).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            db1.child(Head).child("post").setValue(PostText);
                            db1.child(Head).child("name").setValue(Name);
                            db1.child(Head).child("date").setValue(date);
                            db1.child(Head).child("profilimage").setValue(Profil);
                            db1.child(Head).child("surname").setValue(Surname);
                            db1.child(Head).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            db1.child(Head).child("Comments").setValue(Name);
                            db2.child(Head).child("Comments").setValue(Name);
                            db2.child(Head).child("post").setValue(PostText);
                            db2.child(Head).child("name").setValue(Name);
                            db2.child(Head).child("date").setValue(date);
                            db2.child(Head).child("profilimage").setValue(Profil);
                            db2.child(Head).child("surname").setValue(Surname);
                            db2.child(Head).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            //HashMap<String,Object> put = new HashMap<>();
                            //put.put("post",PostText);
                            //put.put("name",Name);
                            //put.put("date",date);
                            //put.put("profilimage",Profil);
                            //put.put("surname",Surname);
                            //put.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            //db2.push().setValue(put);
                            //db1.push().setValue(put);
                        }
                            Post.setText("");
                            PutImage.setImageResource(R.drawable.addphoto);
                            ImageUrl ="";
                            b=5;
                    }
                }, 10000);

            }
        });

    }
    private void SendToEveryFriend(){
        String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("Friends");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void PostDisplay(){
        PostList = (RecyclerView)findViewById(R.id.postlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SocialMedia1.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        PostList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String MyUid = user.getUid();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("FriendPost");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Activity_1_model model = dataSnapshot1.getValue(Activity_1_model.class);
                    list.add(model);
                }
                Activity_1_adapter adapter = new Activity_1_adapter(SocialMedia1.this,list);
                PostList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void ChooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                PutImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void UploadImage(){
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // getting image uri and converting into string
                                    Uri downloadUrl = uri;
                                    ImageUrl= downloadUrl.toString();
                                    final String PostText = Post.getText().toString();
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String myUid = user.getUid();
                                    Calendar calendar = Calendar.getInstance();
                                    String hour  =String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                                    String time = String.valueOf(calendar.get(Calendar.MINUTE));
                                    String day  = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                                    String month = String.valueOf(calendar.get(Calendar.MONTH));
                                    String year = String.valueOf(calendar.get(Calendar.YEAR));
                                    if(month.matches("0") ){
                                        MonthText = "January";
                                    }
                                    if(month.matches("1")){
                                        MonthText = "February";
                                    }
                                    if(month.matches("2")){
                                        MonthText = "March";
                                    }
                                    if(month.matches("3")){
                                        MonthText = "April";
                                    }
                                    if(month.matches("4")){
                                        MonthText = "May";
                                    }
                                    if(month.matches("5")){
                                        MonthText = "June";
                                    }
                                    if(month.matches("6")){
                                        MonthText = "July";
                                    }
                                    if(month.matches("7")){
                                        MonthText = "August";
                                    }
                                    if(month.matches("8")){
                                        MonthText = "September";
                                    }
                                    if(month.matches("9")){
                                        MonthText = "October";
                                    }
                                    if(month.matches("10")){
                                        MonthText = "November";
                                    }
                                    if(month.matches("11")){
                                        MonthText = "December";
                                    }
                                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(myUid).child("MyPosts");
                                    DatabaseReference db2 =FirebaseDatabase.getInstance().getReference().child("Users").child(myUid).child("FriendPost");
                                    final String date =  day + " "+ MonthText + ","+ " "+ hour+":"+time + " "+ year;
                                    final String Head = date+PostText.substring(0,1);
                                    String MyUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    DatabaseReference db10 = FirebaseDatabase.getInstance().getReference().child("Users").child(MyUid).child("Friends");
                                    db10.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                                String ReceiverUid = dataSnapshot1.child("uid").getValue().toString();
                                                DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Users").child(ReceiverUid).child("FriendPost");
                                                db2.child(Head).child("post").setValue(PostText);
                                                db2.child(Head).child("Comments").setValue(Name);
                                                db2.child(Head).child("name").setValue(Name);
                                                db2.child(Head).child("date").setValue(date);
                                                db2.child(Head).child("profilimage").setValue(Profil);
                                                db2.child(Head).child("surname").setValue(Surname);
                                                db2.child(Head).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    db1.child(Head).child("image").setValue(ImageUrl);
                                    db1.child(Head).child("post").setValue(PostText);
                                    db1.child(Head).child("name").setValue(Name);
                                    db1.child(Head).child("date").setValue(date);
                                    db1.child(Head).child("profilimage").setValue(Profil);
                                    db1.child(Head).child("surname").setValue(Surname);
                                    db1.child(Head).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    db2.child(Head).child("image").setValue(ImageUrl);
                                    db2.child(Head).child("post").setValue(PostText);
                                    db1.child(Head).child("Comments").setValue(Name);
                                    db2.child(Head).child("Comments").setValue(Name);
                                    db2.child(Head).child("name").setValue(Name);
                                    db2.child(Head).child("date").setValue(date);
                                    db2.child(Head).child("profilimage").setValue(Profil);
                                    db2.child(Head).child("surname").setValue(Surname);
                                    db2.child(Head).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    // HashMap<String,Object> put = new HashMap<>();
                                   // put.put("image",ImageUrl);
                                   // put.put("post",PostText);
                                   // put.put("name",Name);
                                   // put.put("date",date);
                                   // put.put("profilimage",Profil);
                                   // put.put("surname",Surname);
                                   // put.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                   // db2.push().setValue(put);
                                   // db1.push().setValue(put);
                                    filePath = null;
                                    Toast.makeText(SocialMedia1.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SocialMedia1.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploading "+(int)progress+"%");
                        }
                    });
        }
    }
    private void Search(){
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String name = dataSnapshot1.child("name").getValue().toString();
                    List<String> values = new ArrayList<String>();
                    values.add(name);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SocialMedia1.this,android.R.layout.simple_dropdown_item_1line,values);
                    Search.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
