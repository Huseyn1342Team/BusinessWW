package com.updownteam.businessww;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    private TextView Text;
    private EditText Email;
    private EditText Password;
    private Button Submit;
    private int a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
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
        Text = (TextView)findViewById(R.id.text);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        Submit = (Button)findViewById(R.id.submit);
        Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(LogIn.this,Register.class);
                startActivity(i);
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmailText = Email.getText().toString();
                String PasswordText = Password.getText().toString();
                if(EmailText.matches("")){
                    Toast.makeText(LogIn.this, "Zəhmət olmasa, mailinizi daxil edin!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(PasswordText.matches("")){
                    Toast.makeText(LogIn.this, "Zəhmət olmasa, şifrənizi daxil edin!", Toast.LENGTH_LONG).show();
                    return;
                }
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(EmailText,PasswordText)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    final ProgressDialog csprogress=new ProgressDialog(LogIn.this);
                                    csprogress.setMessage("Uploading...");
                                    csprogress.show();
                                    new Handler().postDelayed(new Runnable() {

                                        @Override
                                        public void run() {
                                            csprogress.dismiss();
                                            b=5;
                                            Intent i =new Intent(LogIn.this,SocialMedia1.class);
                                            startActivity(i);
                                        }
                                    }, 13000);
                                }else{

                                    Toast.makeText(LogIn.this, "Xəta baş verdi!", Toast.LENGTH_LONG).show();
                                    return;

                                }
                            }
                        });
            }
        });
    }
}

