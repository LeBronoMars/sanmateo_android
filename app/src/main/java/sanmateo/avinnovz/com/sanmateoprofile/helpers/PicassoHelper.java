package sanmateo.avinnovz.com.sanmateoprofile.helpers;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;

import sanmateo.avinnovz.com.sanmateoprofile.R;


/**
 * Created by rsbulanon on 7/3/16.
 */
public class PicassoHelper {

    public static void loadImageFromURL(final String url,final int size,final int color,
                                        final ImageView imageView, final ProgressBar progressBar) {
        LogHelper.log("pic","load pic url ---> " + url);
        progressBar.setVisibility(View.VISIBLE);
        AppConstants.PICASSO.load(url)
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .resize(size,size)
                .transform(new CircleTransform(color, 1)).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                LogHelper.log("pic","profile pic loaded successfully");
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
                LogHelper.log("pic","profile pic loaded unable to load");
            }
        });
    }

    public static void loadImageFromDrawable(int drawable, ImageView imageView) {
        AppConstants.PICASSO.load(drawable).into(imageView);
    }

    public static void loadImageFromDrawable(int drawable, ImageView imageView, int transformColor,
                                             int size) {
        AppConstants.PICASSO.load(drawable)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .resize(size,size)
                .transform(new CircleTransform(transformColor, 1))
                .placeholder(drawable)
                .into(imageView);
    }

    public static void loadBlurImageFromURL(String url, int placeholder, int blurStrength, ImageView imageView) {
        AppConstants.PICASSO.load(url)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(placeholder)
                .transform(new StackBlurTransformation(blurStrength))
                .into(imageView);
    }

    public static void loadBlurImageFromURL(String url, int placeholder, int size, int blurStrength, ImageView imageView) {
        AppConstants.PICASSO.load(url)
                .resize(size,size)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(placeholder)
                .transform(new StackBlurTransformation(blurStrength))
                .into(imageView);
    }

    public static void loadBlurImageFromDrawable(int drawable, int blurStrength, ImageView imageView) {
        AppConstants.PICASSO.load(drawable)
                .fit()
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(drawable)
                .transform(new StackBlurTransformation(blurStrength))
                .into(imageView);
    }

    public static void loadImageFromURL(final String url, final ImageView imageView, final ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        AppConstants.PICASSO.load(url)
                .fit()
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        Log.d("pic", "Image loading done -> " + url);
                    }

                    @Override
                    public void onError() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        Log.d("pic", "Image loading failed");
                    }
                });
    }

}
