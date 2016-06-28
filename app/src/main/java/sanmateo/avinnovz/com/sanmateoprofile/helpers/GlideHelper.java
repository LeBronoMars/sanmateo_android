package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by rsbulanon on 6/26/16.
 */
public class GlideHelper {

    public static void loadImage(final Context context, final String url, final ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }
}