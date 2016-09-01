package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.ImageRecyclerAdapter;

/**
 * Created by ctmanalo on 9/1/16.
 */
public class ImageUrlRecyclerFragment extends Fragment {

    @BindView(R.id.rvListing) RecyclerView rvListing;
    private List<ParentObject> itemList;

    public static ImageUrlRecyclerFragment newInstance(List<ParentObject> itemList) {
        ImageUrlRecyclerFragment fragment = new ImageUrlRecyclerFragment();
        fragment.itemList = itemList;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_level, container, false);
        ButterKnife.bind(this, view);
        initListing();
        return view;
    }

    private void initListing() {
        ImageRecyclerAdapter adapter = new ImageRecyclerAdapter(getActivity(), itemList);
        adapter.setCustomParentAnimationViewId(R.id.ivArrow);
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        rvListing.setAdapter(adapter);
        rvListing.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
