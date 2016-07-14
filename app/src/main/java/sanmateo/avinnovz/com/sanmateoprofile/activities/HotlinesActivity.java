package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.HotlinesAdapter;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.Hotlines;


/**
 * Created by rsbulanon on 7/14/16.
 */
public class HotlinesActivity extends BaseActivity {

    @BindView(R.id.rvCards) RecyclerView rvCards;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotlines_card);
        ButterKnife.bind(this);
        setToolbarTitle("Emergency Hotlines");
        ArrayList<Hotlines> hotlines = new ArrayList<>();

        /** NDRRMC hotlines*/
        ArrayList<String> trunklines = new ArrayList<>();
        ArrayList<String> optrCenter = new ArrayList<>();
        ArrayList<String> ncr = new ArrayList<>();
        ArrayList<String> r1 = new ArrayList<>();
        ArrayList<String> r4A = new ArrayList<>();
        ArrayList<String> r4B = new ArrayList<>();
        ArrayList<String> car = new ArrayList<>();

        trunklines.addAll(Arrays.asList(new String[]{"911-5061","911-5062","911-5063","911-5064","911-5065"}));
        optrCenter.addAll(Arrays.asList(new String[]{"(02)911-1406","(02)912-2665","(02)912-5668","(02) 911-1873"}));
        ncr.addAll(Arrays.asList(new String[]{"(02) 421-1918","(02) 913-2786"}));
        r1.addAll(Arrays.asList(new String[]{"(072) 607-6528"}));
        r4A.addAll(Arrays.asList(new String[]{"(049) 531-7266"}));
        r4B.addAll(Arrays.asList(new String[]{"(043) 723-4248"}));
        car.addAll(Arrays.asList(new String[]{"(074) 304-2256","(074) 619-0986","(074) 444-5298","(074) 619-0986"}));

        hotlines.add(new Hotlines("NDRRMC", "Trunk Lines", trunklines));
        hotlines.add(new Hotlines("NDRRMC", "Operations Center", optrCenter));
        hotlines.add(new Hotlines("NDRRMC", "Office of Civil Defense  - NCR", ncr));
        hotlines.add(new Hotlines("NDRRMC", "Office of Civil Defense  - Region 1", r1));
        hotlines.add(new Hotlines("NDRRMC", "Office of Civil Defense  - Region IV-A", r4A));
        hotlines.add(new Hotlines("NDRRMC", "NDRRMC Region IV-B", r4B));
        hotlines.add(new Hotlines("NDRRMC", "NDRRMC Region IV-CAR", car));

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvCards.setLayoutManager(llm);
        rvCards.setAdapter(new HotlinesAdapter(this,hotlines));
    }
}
