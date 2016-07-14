package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by rsbulanon on 7/15/16.
 */
public class DisasterMenuAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<String> menu;

    public DisasterMenuAdapter(final Context context, final ArrayList<String> menu) {
        this.layoutInflater = LayoutInflater.from(context);
        this.menu = menu;
    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public String getItem(int i) {
        return menu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View v = layoutInflater.inflate(R.layout.row_disaster_menu, null);
        final TextView tvDisasterMenu = (TextView)v.findViewById(R.id.tvDisasterMenu);
        tvDisasterMenu.setText(getItem(i));
        return v;
    }
}
