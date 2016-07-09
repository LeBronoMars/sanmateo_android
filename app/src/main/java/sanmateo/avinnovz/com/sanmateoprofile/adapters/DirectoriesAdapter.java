package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.DirectoriesActivity;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Incident;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class DirectoriesAdapter extends BaseExpandableListAdapter {

    private DirectoriesActivity activity;
    private LayoutInflater inflater;
    private ArrayList<String> header;
    private ArrayList<String> contacts;
    private ArrayList<String> email;

    public DirectoriesAdapter(Context context, ArrayList<String> header, ArrayList<String> contacts,
                              ArrayList<String> email) {
        this.activity = (DirectoriesActivity) context;
        this.inflater = LayoutInflater.from(context);
        this.header = header;
        this.contacts = contacts;
        this.email = email;
    }

    @Override
    public int getGroupCount() {
        return header.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public String getGroup(int i) {
        return header.get(i);
    }

    @Override
    public String getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.header_directories, null, false);
        TextView tvHeader = (TextView)view.findViewById(R.id.tvHeader);
        tvHeader.setText(getGroup(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.row_directories, null, false);
        final TextView tvContact = (TextView)view.findViewById(R.id.tvContact);
        final TextView tvEmail = (TextView)view.findViewById(R.id.tvEmail);
        tvContact.setText(contacts.get(i));
        tvEmail.setText(email.get(i));
        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tvContact.getText().toString()));
                activity.startActivity(intent);
            }
        });
        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                        tvEmail.getText().toString(), null));
                activity.startActivity(Intent.createChooser(intent, "Send email..."));
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}