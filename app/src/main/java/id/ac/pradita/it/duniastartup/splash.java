package id.ac.pradita.it.duniastartup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread(){
             public void run(){
                 try{
                     sleep(4100);
                 }
                 catch(InterruptedException e){
                     e.printStackTrace();
                 } finally {
                     Intent intent = new Intent(splash.this, WelcomeActivity.class);
                     startActivity(intent);
                 }
             }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
