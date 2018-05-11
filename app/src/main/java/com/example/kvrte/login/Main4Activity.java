package com.example.kvrte.login;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;

public class Main4Activity extends AppCompatActivity {

    ImageView logo;
    Button signup, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        logo = findViewById(R.id.image);
        ObjectAnimator animator = ObjectAnimator.ofFloat(logo , "translationY"  ,-80f);
        animator.setDuration(1500);
        animator.start();

        ArrayList<View>  viewToFadeIn = new ArrayList<View>();

        viewToFadeIn.add(findViewById(R.id.signup));
        viewToFadeIn.add(findViewById(R.id.login));

        for(View v : viewToFadeIn)
        {
            v.setAlpha(0);
        }


        for(View v: viewToFadeIn)
        {
            v.animate().alpha(1.0f).setDuration(3500).start();
        }

        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main4Activity.this , MainActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Main4Activity.this , Main2Activity.class);
                startActivity(intent1);
            }
        });

    }
}
