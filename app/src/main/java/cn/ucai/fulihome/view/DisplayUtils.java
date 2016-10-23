package cn.ucai.fulihome.view;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.ucai.fulihome.R;
import cn.ucai.fulihome.utils.MFGT;


/**
 * Created by clawpo on 16/8/3.
 */
public class DisplayUtils {
    public static void initBack(final Activity activity){
        activity.findViewById(R.id.LoginBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity.finish();
                MFGT.finish(activity);
            }
        });
    }

    public static void initBackWithTitle(final Activity activity, final String title){
        initBack(activity);
        ((TextView)activity.findViewById(R.id.Login)).setText(title);
    }
}
