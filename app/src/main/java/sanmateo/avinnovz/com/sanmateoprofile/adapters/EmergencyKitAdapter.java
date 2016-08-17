package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by ctmanalo on 8/16/16.
 */
public class EmergencyKitAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> kits;

    @BindView(R.id.tvEmergencyKit) TextView tvEmergencyKit;

    public EmergencyKitAdapter(final Context context, final ArrayList<String> kits) {
        this.inflater = LayoutInflater.from(context);
        this.kits = kits;
    }

    @Override
    public int getCount() {
        return kits.size();
    }

    @Override
    public Object getItem(int i) {
        return kits.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View v = inflater.inflate(R.layout.row_emergency_kit, null);
        ButterKnife.bind(this, v);
        tvEmergencyKit.setText(kits.get(i));
        return v;
    }
}
