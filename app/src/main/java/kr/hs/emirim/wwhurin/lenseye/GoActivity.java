package kr.hs.emirim.wwhurin.lenseye;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class GoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go);

        Handler starthandle = new Handler( );
        starthandle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(GoActivity.this, MainActivity.class);
                          startActivity(i);
                           finish();
            }
        }, 1000);
    }
}
