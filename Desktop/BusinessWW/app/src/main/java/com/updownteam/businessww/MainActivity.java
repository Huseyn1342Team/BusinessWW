package com.updownteam.businessww;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override

            public void run() {
                FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
                if(auth == null){
                    Intent i = new Intent(MainActivity.this, LogIn.class);//LogIn

                    startActivity(i);

                    // close this activity

                }else{
                    Intent i = new Intent(MainActivity.this,SocialMedia1.class);//SocialMedia1

                    startActivity(i);

                    // close this activity

                }


            }

        }, 5000); // wait for 5 seconds
    }
}
