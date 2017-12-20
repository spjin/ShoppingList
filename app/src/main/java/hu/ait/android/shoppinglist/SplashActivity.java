package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView ivSplash = (ImageView) findViewById(R.id.ivSplash);
        final Animation anim = AnimationUtils.loadAnimation(
                SplashActivity.this, R.anim.show_anim);


        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intentDetails = new Intent();
                intentDetails.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intentDetails);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        ivSplash.startAnimation(anim);

    }
}
