package training.eduonix.application;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("inside", "onCreate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d("inside", "onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d("inside", "onTrimMemory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
