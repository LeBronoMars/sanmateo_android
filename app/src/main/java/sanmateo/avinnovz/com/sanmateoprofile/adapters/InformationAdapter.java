package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.InformationActivity;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class InformationAdapter extends BaseAdapter {

    private String[] header;
    private int[] icons;
    private InformationActivity activity;

    public InformationAdapter(Context context, String[] header, int[] icons) {
        this.header = header;
        this.icons = icons;
        this.activity = (InformationActivity) context;
    }

    @Override
    public int getCount() {
        return header.length;
    }

    @Override
    public String getItem(int i) {
        return header[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.row_info, null, false);
        ImageView ivIcon = (ImageView)view.findViewById(R.id.ivIcon);
        TextView tvHeader = (TextView)view.findViewById(R.id.tvHeader);
        ivIcon.setImageResource(icons[i]);
        tvHeader.setText(getItem(i));
        return view;
    }
}
