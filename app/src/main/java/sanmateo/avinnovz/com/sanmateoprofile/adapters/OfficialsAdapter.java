package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.OfficialsActivity;

/**
 * Created by ctmanalo on 7/7/16.
 */
public class OfficialsAdapter extends BaseAdapter {

    private OfficialsActivity activity;
    private ArrayList<String> dummyText;

    public OfficialsAdapter(Context context, ArrayList<String> dummyText) {
        this.activity = (OfficialsActivity) context;
        this.dummyText = dummyText;
    }

    @Override
    public int getCount() {
        return dummyText.size();
    }

    @Override
    public String getItem(int i) {
        return dummyText.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String dummy = dummyText.get(i);

        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = activity.getLayoutInflater().inflate(R.layout.row_officials, null, false);
            holder.ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);
            holder.tvName = (TextView) view.findViewById(R.id.tvName);
            holder.tvPosition = (TextView) view.findViewById(R.id.tvPosition);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.ivProfilePic.setImageResource(R.drawable.officials);
        holder.tvName.setText(dummy);
        holder.tvName.setText(dummy);
        return view;
    }

    private class ViewHolder {
        ImageView ivProfilePic;
        TextView tvName;
        TextView tvPosition;
    }
}
