package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;

/**
 * Created by rsbulanon on 8/7/16.
 */
public class ManageGalleryActivity extends BaseActivity {

    @BindView(R.id.rvGalleries) RecyclerView rvGalleries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_gallery);
        ButterKnife.bind(this);
    }
}
