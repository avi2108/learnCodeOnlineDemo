package com.example.learncodeonlinedemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.learncodeonlinedemo.utils.CircularAnim;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        final ImageView imageView = findViewById(R.id.imgAtSplash);

        applyIndefiniteZoomInOutAnim(findViewById(R.id.imgAtSplash));
        applyIndefiniteZoomInOutAnim(findViewById(R.id.imgHeartATSplash));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CircularAnim.fullActivity(SplashActivity.this, imageView).go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {

                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        finish();
                    }
                });
            }
        }, 4000);
    }

    /**
     * Applying a zoomin zoom out anim effect for view passed in as Argument
     *
     * @param view
     */
    void applyIndefiniteZoomInOutAnim(View view) {
        //---applying scale animation infinitely
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1.2f);

        AnimatorSet scaleAnim = new AnimatorSet();
        scaleAnim.setDuration(900);
        scaleAnim.play(scaleX).with(scaleY);
        scaleAnim.start();
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
    }
}
