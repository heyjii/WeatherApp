package training.eduonix.weatherapp;

import android.app.Activity;
import android.app.FragmentManager;

import android.app.FragmentTransaction;
import android.os.Bundle;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AddLocationFragment addLocationFragment = new AddLocationFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_container, addLocationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
