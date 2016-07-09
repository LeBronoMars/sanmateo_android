package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;

/**
 * Created by rsbulanon on 7/9/16.
 */
public class PublicAnnouncementsActivity extends BaseActivity {

    @BindView(R.id.rvAnnouncements) RecyclerView rvAnnouncements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_announcements);
        ButterKnife.bind(this);
    }

    private void initAnnouncements() {

    }
}
