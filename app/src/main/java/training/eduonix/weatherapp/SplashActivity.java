package training.eduonix.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import training.eduonix.custom.Constants;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setAutoLocationDefValue();

        Handler handler = new Handler() ;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent homeIntent = new Intent(SplashActivity.this,HomeActivity.class) ;
                finish();
                startActivity(homeIntent);
            }
        },3000) ;
    }

    private void setAutoLocationDefValue()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCES,MODE_APPEND) ;
        sharedPreferences.edit().putBoolean(Constants.KEY_AUTO_LOCATION_ENABLED,true) ;
        sharedPreferences.edit().commit() ;
    }

}
