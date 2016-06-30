package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 6/30/16.
 */
public class ImageFullViewActivity extends BaseActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_fullview);
        ButterKnife.bind(this);

    }
}
