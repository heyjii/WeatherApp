package training.eduonix.weatherapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class  SettingsFragment extends Fragment {


    private TextView textView ;
    private Button enterBtn ;
    private EditText cityTextvalue ;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_settings, container, false);

        textView = (TextView) fragmentView.findViewById(R.id.TitleText);
        textView.setTextColor(getResources().getColor(R.color.material_blue_grey_900));

        cityTextvalue = (EditText) fragmentView.findViewById(R.id.EdtTxtVal);

        enterBtn = (Button) fragmentView.findViewById(R.id.EnterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View view)
                {
                    Toast.makeText(getActivity(),"Entered City is "+cityTextvalue.getText(),Toast.LENGTH_LONG).show();
                }
                                    }
        );

       return fragmentView ;
    }
}
