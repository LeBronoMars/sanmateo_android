package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.annotations.Beta;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.OfficialsAdapter;

/**
 * Created by ctmanalo on 7/7/16.
 */
public class OfficialsActivity extends BaseActivity {

    @BindView(R.id.lvOfficials) ListView lvOfficials;
    private ArrayList<String> dummyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officials);
        ButterKnife.bind(this);
        setToolbarTitle("Officials");
        initDummyDisplay();
    }

    private void initDummyDisplay() {
        dummyText = new ArrayList<>();
        dummyText.add("San Mateo App");
        dummyText.add("San Mateo App");
        dummyText.add("San Mateo App");
        dummyText.add("San Mateo App");
        dummyText.add("San Mateo App");
        OfficialsAdapter adapter = new OfficialsAdapter(this, dummyText);
        lvOfficials.setAdapter(adapter);
    }
}
