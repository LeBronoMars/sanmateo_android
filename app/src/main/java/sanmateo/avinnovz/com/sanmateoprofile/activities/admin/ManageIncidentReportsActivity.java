package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.TabAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.ActiveIncidentReports;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.ForApprovalIncidentFragment;
import sanmateo.avinnovz.com.sanmateoprofile.fragments.admin.ReviewIncidentsFragment;

/**
 * Created by rsbulanon on 9/4/16.
 */
public class ManageIncidentReportsActivity extends BaseActivity {

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> tabNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_incident_reports);
        ButterKnife.bind(this);
        setToolbarTitle("Manage Incident Reports");
        initViewPager();
    }

    private void initViewPager() {
        /** set up viewpager and tab layout */
        tabNames.add("Active Reports");
        tabNames.add("For Approvals");
        tabNames.add("For Reviews");

        fragments.add(ActiveIncidentReports.newInstance());
        fragments.add(ForApprovalIncidentFragment.newInstance());
        fragments.add(ReviewIncidentsFragment.newInstance());

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, tabNames));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    @Subscribe
    public void handleApiResponse(final HashMap<String,Object> map) {
        runOnUiThread(() -> {
            try {
                final JSONObject json = new JSONObject(map.get("data").toString());
                if (json.has("action")) {
                    final String action = json.getString("action");

                    /** new incident notification */
                    if (action.equals("incident_approval")) {
                        viewPager.setCurrentItem(1);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
