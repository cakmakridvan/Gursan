package com.rotamobile.gursan.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.rotamobile.gursan.Main;
import com.rotamobile.gursan.R;
import com.rotamobile.gursan.ui.login.Login;

public class Splash extends AppCompatActivity {

    private ImageView img_icon1;

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_splash);

        img_icon1 = findViewById(R.id.spl_icon1);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim);
        img_icon1.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent go_main_activity = new Intent(Splash.this,Login.class);
                startActivity(go_main_activity);

                //close this activity
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
