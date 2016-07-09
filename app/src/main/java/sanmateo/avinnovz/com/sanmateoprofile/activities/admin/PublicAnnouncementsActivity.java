package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.AnnouncementsAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Announcement;
import sanmateo.avinnovz.com.sanmateoprofile.singletons.AnnouncementsSingleton;

/**
 * Created by rsbulanon on 7/9/16.
 */
public class PublicAnnouncementsActivity extends BaseActivity {

    @BindView(R.id.rvAnnouncements) RecyclerView rvAnnouncements;
    private AnnouncementsSingleton announcementsSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_announcements);
        ButterKnife.bind(this);
        announcementsSingleton = AnnouncementsSingleton.getInstance();
        initAnnouncements();
    }

    private void initAnnouncements() {
        final AnnouncementsAdapter adapter = new AnnouncementsAdapter(this,announcementsSingleton.getAnnouncements());
        rvAnnouncements.setAdapter(adapter);
        rvAnnouncements.setLayoutManager(new LinearLayoutManager(this));
    }
}
