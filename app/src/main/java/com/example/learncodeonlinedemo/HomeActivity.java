package com.example.learncodeonlinedemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.learncodeonlinedemo.utils.CircularAnim;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.homeactivity_text);
        findViewById(R.id.layoutItemForDataStructures).setOnClickListener(this);
        findViewById(R.id.layoutItemForCategory2).setOnClickListener(this);
        findViewById(R.id.layout_footer_ad).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_footer_ad: //open site in browser
                String url = "https://courses.learncodeonline.in";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            default:
                final Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                if (v.getId() == R.id.layoutItemForDataStructures) {//sending the topic data to MainActivity screen
                    intent.putExtra("conceptName", getString(R.string.concept_name_1));
                } else {
                    intent.putExtra("conceptName", getString(R.string.concept_name_2));
                }
                CircularAnim.fullActivity(HomeActivity.this, v).go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        startActivity(intent);
                    }
                });

        }

    }
}
