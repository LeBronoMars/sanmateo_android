package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 6/27/16.
 */
public class GalleryActivity extends BaseActivity {

    @BindView(R.id.tvDescription) TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        tvDescription.setText("In terms of geography, the town of  San Mateo is just a heartbeat away from Metro Manila.Barangay Banaba is its gateway, whether approaching it from either Nangka in Marikina City or Batasan Road in Quezon City. Upon driving through the arch above General Luna Highwaymarking the entry point to San Mateo, a marker can be seen by the side of the road saying something like you are now leaving the National Capital Region!  \\\\n\\\\nGeneral Luna Highway is San Mateo’s main thoroughfare. It must be a historic road where Andres Bonifacio could have passed by on his way to Pamitinan Cave. The legendary General Licerio Geronimo and his band of tiradores could have marched this road on their way to the victorious Battle of San Mateo.");
    }
}
