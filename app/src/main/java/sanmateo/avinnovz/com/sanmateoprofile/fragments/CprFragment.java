package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;

/**
 * Created by ctmanalo on 8/16/16.
 */
public class CprFragment extends Fragment {

    private String type;
    @BindView(R.id.ivCpr) ImageView ivCpr;

    public static CprFragment newInstance(String type) {
        CprFragment cprFragment = new CprFragment();
        cprFragment.type = type;
        return cprFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cpr, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        switch (type) {
            case "Infant":
                ivCpr.setImageResource(R.drawable.cpr_infant);
                break;
            case "Adult":
                ivCpr.setImageResource(R.drawable.cpr_adult);
                break;
        }
    }
}
