package training.eduonix.weatherapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class ControlFragment extends Fragment  {

    private CheckBox showImageCheckBox ;
    private ImageView iconImageView  ;
    private Switch switchObject ;
    private ProgressBar progressBar ;
    private TextView progressView ;
    private  int progressStatus = 0 ;
    private Handler handler = new Handler() ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_control, container, false);

        showImageCheckBox = (CheckBox) fragmentView.findViewById(R.id.checkBox2) ;
        iconImageView = (ImageView) fragmentView.findViewById(R.id.imageView) ;
        switchObject = (Switch) fragmentView.findViewById(R.id.switch1) ;
        progressBar = (ProgressBar) fragmentView.findViewById(R.id.progressBar) ;
        progressView = (TextView) fragmentView.findViewById(R.id.progressTextView) ;

        switchObject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isOn) {
                if(isOn)
                {
                    Toast.makeText(getActivity(),"Switch is ON",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Switch is OFF",Toast.LENGTH_LONG).show();
                }

            }
        });


        showImageCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    iconImageView.setVisibility(View.VISIBLE);
                }
                else
                {
                    iconImageView.setVisibility(View.GONE);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            progressView.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }


            }
        }).start();

        return fragmentView ;
    }







}
