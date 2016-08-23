package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;

/**
 * Created by rsbulanon on 8/23/16.
 */
public class ManageOfficialsActivity extends BaseActivity {

    @BindView(R.id.rvOfficials) RecyclerView rvOfficials;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_officials);
        ButterKnife.bind(this);
        setToolbarTitle("Manage Officials");
    }

    @OnClick(R.id.btnAdd)
    public void addNewOfficial() {

    }

}
