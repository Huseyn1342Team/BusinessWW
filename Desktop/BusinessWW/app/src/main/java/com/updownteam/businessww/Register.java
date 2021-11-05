package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class Register extends AppCompatActivity {
    private Spinner Gender;
    private Button Submit;
    private EditText Name;
    private EditText Surname;
    private CountryCodePicker CCP;
    private EditText PhoneNumber;
    private EditText Password;
    private EditText Email;
    DatePicker Birth;
    private int a,b;
    private String myUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        String[] users = {"Choose your gender!","Female","Male"};
        Gender = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter);
        Submit = (Button)findViewById(R.id.submit);
        Name = (EditText)findViewById(R.id.name);
        Surname = (EditText)findViewById(R.id.surname);
        PhoneNumber = (EditText)findViewById(R.id.phoneNumber);
        CCP = (CountryCodePicker)findViewById(R.id.ccp);
        CCP.registerCarrierNumberEditText(PhoneNumber);
        Password = (EditText)findViewById(R.id.password);
        Birth = (DatePicker)findViewById(R.id.birth);
        Email = (EditText)findViewById(R.id.email);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String NameText = Name.getText().toString();
                final String SurnameText = Surname.getText().toString();
                final String CCPText = CCP.getFullNumberWithPlus();
                final String PasswordText = Password.getText().toString();
                final String BirthDay = String.valueOf(Birth.getDayOfMonth());
                final String BirthMonth = String.valueOf(Birth.getMonth()+1);
                final String BirthYear = String.valueOf(Birth.getYear());
                final String GenderText = Gender.getSelectedItem().toString();
                final String EmailText = Email.getText().toString();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if(NameText.matches("")){
                    Toast.makeText(Register.this,"Adınızı yazmayıbsınız!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(SurnameText.matches("")){
                    Toast.makeText(Register.this,"Soyadınızı yazmayıbsınız!",Toast.LENGTH_LONG).show();
                    return;
                }
                if (CCPText.matches("")){
                    Toast.makeText(Register.this,"Telefon nömrəsini yazmalısınız!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(PasswordText.matches("")){
                    Toast.makeText(Register.this,"Şifrəni yazmalısınız!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(BirthDay.matches("")){
                    Toast.makeText(Register.this,"Doğum haqqında məlumat verin!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(BirthMonth.matches("")){
                    Toast.makeText(Register.this,"Doğum haqqında məlumat verin!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(BirthYear.matches("")){
                    Toast.makeText(Register.this,"Doğum haqqında məlumat verin!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(GenderText.matches("")){
                    Toast.makeText(Register.this,"Cinsiniz müəyyən edilməyib!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(EmailText.matches("")){
                    Toast.makeText(Register.this,"Emailiniz müəyyən edilməyib!",Toast.LENGTH_LONG).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(EmailText,PasswordText)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    myUid = user.getUid();
                                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Users");
                                    db1.child(myUid).child("name").setValue(NameText);
                                    db1.child(myUid).child("surname").setValue(SurnameText);
                                    db1.child(myUid).child("phone").setValue(CCPText);
                                    db1.child(myUid).child("password").setValue(PasswordText);
                                    db1.child(myUid).child("day").setValue(BirthDay);
                                    db1.child(myUid).child("month").setValue(BirthMonth);
                                    db1.child(myUid).child("year").setValue(BirthYear);
                                    db1.child(myUid).child("gender").setValue(GenderText);
                                    db1.child(myUid).child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    db1.child(myUid).child("profil").setValue("https://firebasestorage.googleapis.com/v0/b/bww-infomatrix.appspot.com/o/images%2F0d6aea26-9e9d-4f91-93ec-cb991b4a488f?alt=media&token=cba79185-b471-43d9-b1b6-fcd721ddfee2");
                                    final ProgressDialog csprogress=new ProgressDialog(Register.this);
                                    csprogress.setMessage("Uploading...");
                                    csprogress.show();
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            csprogress.dismiss();
                                            b=5;
                                            Intent i =new Intent(Register.this,SocialMedia1.class);
                                            startActivity(i);
                                        }
                                    }, 7000);
                                }else {
                                    Toast.makeText(Register.this,"Xəta baş verdi!",Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });
            }
        });
    }
}

