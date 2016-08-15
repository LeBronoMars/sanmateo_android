package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.EmergencyKitAdapter;


public class EmergencyKitActivity extends BaseActivity {

    @BindView(R.id.lvEmergencyKits) ListView lvEmergencyKits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_kit);
        ButterKnife.bind(this);
        setToolbarTitle("Emergency Kit");
        initList();
    }

    private void initList() {
        ArrayList<String> kits = new ArrayList<>();
        kits.add("Water, one gallon of water per person per day for atleast three days, for" +
                " drinking and sanitation");
        kits.add("Food, atleast three day supply of non-perishable food");
        kits.add("Battery-powered or hand crank radio and a NOAA Weather Radio with tone alert" +
                " and extra batteries for both");
        kits.add("Flashlight and extra batteries");
        kits.add("First aid kit");
        kits.add("Whistle to signal for help");
        kits.add("Dust mask, to help filter contaminated air and plastic sheeting and duct tape" +
                " to shelter-in-place");
        kits.add("Moist towelettes, garbage bags and plastic ties for personal sanitation");
        kits.add("Wrench or pliers to turn utilities");
        kits.add("Can opener for food (if kit contains canned food)");
        kits.add("Local maps");
        kits.add("Cell phone with chargers, inverter or solar charger");

        EmergencyKitAdapter adapter = new EmergencyKitAdapter(this, kits);
        lvEmergencyKits.setAdapter(adapter);
    }

    @OnClick(R.id.tvLink)
    public void goLink() {
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.fema.gov")));
    }

}
