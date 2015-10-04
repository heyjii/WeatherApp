package training.eduonix.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import training.eduonix.adapters.ImageAdapter;


public class gridViewFragment extends Fragment {

    private GridView gridView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gridViewfragment = inflater.inflate(R.layout.fragment_grid_view, container, false);

        gridView = (GridView)gridViewfragment.findViewById(R.id.GridView) ;
        gridView.setAdapter(new ImageAdapter(getActivity()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(),"position "+position,Toast.LENGTH_LONG).show();

            }
        });
        return gridViewfragment ;
    }
}
