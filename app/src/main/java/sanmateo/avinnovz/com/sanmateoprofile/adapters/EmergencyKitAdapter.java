package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.models.others.EmergencyKits;

/**
 * Created by ctmanalo on 8/16/16.
 */
public class EmergencyKitAdapter extends ArrayAdapter<EmergencyKits> {

    private LayoutInflater inflater;
    private ArrayList<EmergencyKits> kits;
    private OnCheckBoxPressed onCheckBoxPressed;

    public EmergencyKitAdapter(final Context context, final ArrayList<EmergencyKits> kits) {
        super(context, R.layout.row_emergency_kit, kits);
        this.inflater = LayoutInflater.from(context);
        this.kits = kits;
    }

    public static class ViewHolder {
        @BindView(R.id.tvEmergencyKit) TextView tvEmergencyKit;
        @BindView(R.id.cbEmergencyKit) CheckBox cbEmergencyKit;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public ArrayList<EmergencyKits> getAllItems() {
        return kits;
    }

    @Override
    public int getCount() {
        return kits.size();
    }

    @Override
    public EmergencyKits getItem(int i) {
        return kits.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.row_emergency_kit, null);
            viewHolder = new ViewHolder(view);
            viewHolder.cbEmergencyKit.setOnCheckedChangeListener((compoundButton, b) -> {
                int getPosition = (Integer) compoundButton.getTag();
                kits.get(getPosition).setCheckedState(compoundButton.isChecked());
                onCheckBoxPressed.onPressed(kits);
            });
            view.setTag(viewHolder);
            view.setTag(R.id.tvEmergencyKit, viewHolder.tvEmergencyKit);
            view.setTag(R.id.cbEmergencyKit, viewHolder.cbEmergencyKit);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.cbEmergencyKit.setTag(i);
        viewHolder.tvEmergencyKit.setText(kits.get(i).getLabel());
        viewHolder.cbEmergencyKit.setChecked(kits.get(i).isCheckedState());
        return view;
    }

    public interface OnCheckBoxPressed {
        void onPressed(ArrayList<EmergencyKits> emergencyKits);
    }

    public void setOnCheckBoxPressed (OnCheckBoxPressed onCheckBoxPressed) {
        this.onCheckBoxPressed = onCheckBoxPressed;
    }
}
