package training.eduonix.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import training.eduonix.weatherapp.R;

public class ImageAdapter extends BaseAdapter {
    private Context context ;
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.android_icon, R.drawable.android_icon,
            R.drawable.android_icon, R.drawable.android_icon,
            R.drawable.android_icon, R.drawable.android_icon,
            R.drawable.android_icon, R.drawable.android_icon,
            R.drawable.android_icon, R.drawable.android_icon,
            R.drawable.android_icon, R.drawable.android_icon,
            R.drawable.android_icon, R.drawable.android_icon,
            R.drawable.android_icon, R.drawable.android_icon,
            R.drawable.android_icon, R.drawable.android_icon,
            R.drawable.android_icon, R.drawable.android_icon,
    };

    public ImageAdapter(Context c)
    {
        context = c ;
    }
    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        if (view == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) view;
        }
        imageView.setImageResource(mThumbIds[i]);
        return imageView;
    }
}
