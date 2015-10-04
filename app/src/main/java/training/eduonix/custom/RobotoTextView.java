package training.eduonix.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.TextureView;
import android.widget.TextView;

public class RobotoTextView extends TextView {
    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoBold.ttf");
        setTypeface(typeface);
    }
}