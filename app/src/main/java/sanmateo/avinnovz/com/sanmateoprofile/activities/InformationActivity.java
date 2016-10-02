package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.InformationAdapter;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class InformationActivity extends BaseActivity {

    @BindView(R.id.lvInformation) ListView lvInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        setToolbarTitle("Information");
        String[] headers = new String[]{"History","Officials","Government"};
        int[] icons = new int[]{R.drawable.history, R.drawable.officials, R.drawable.government};
        lvInformation.setAdapter(new InformationAdapter(this, headers, icons));
        lvInformation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = null;
                if (i == 0) {
                    intent = new Intent(InformationActivity.this, HistoryActivity.class);
                } else if (i == 1) {
                    intent = new Intent(InformationActivity.this, OfficialsActivity.class);
                } else if (i == 2) {
                    intent = new Intent(InformationActivity.this, GovtInfoActivity.class);
                }
                startActivity(intent);
                animateToLeft(InformationActivity.this);
            }
        });
    }
}
