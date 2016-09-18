package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.dao.LocalOfficial;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.Official;

/**
 * Created by rsbulanon on 9/3/16.
 */
public class OfficialFullInfoActivity extends BaseActivity {

    @BindView(R.id.civPic) CircleImageView civPic;
    @BindView(R.id.pbLoadImage) ProgressBar pbLoadImage;
    @BindView(R.id.tvOfficialName) TextView tvOfficialName;
    @BindView(R.id.tvPosition) TextView tvPosition;
    @BindView(R.id.tvBackground) TextView tvBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_full_info);
        ButterKnife.bind(this);
        setToolbarTitle("San Mateo Officials");
        String fullName;
        String lastName;
        String nickName;
        String position;
        String background;
        String picUrl;

        if (getIntent().getSerializableExtra("localOfficial") instanceof LocalOfficial) {
            final LocalOfficial official = (LocalOfficial) getIntent().getSerializableExtra("localOfficial");
            nickName = official.getNickName().isEmpty() ? " " : " '"+official.getNickName()+"' ";
            fullName = official.getFirstName() + nickName + official.getLastName();
            position = official.getPosition();
            background = official.getBackground();
            picUrl = official.getPic();
        } else {
            final Official official = (Official) getIntent().getSerializableExtra("localOfficial");
            nickName = official.getNickName().isEmpty() ? " " : " '"+official.getNickName()+"' ";
            fullName = official.getFirstName() + nickName + official.getLastName();
            position = official.getPosition();
            background = official.getBackground();
            picUrl = official.getPic();
        }

        tvOfficialName.setText(fullName);
        tvPosition.setText(position);
        pbLoadImage.setVisibility(View.VISIBLE);
        tvBackground.setText(background);
        AppConstants.PICASSO.load(picUrl)
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .into(civPic, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbLoadImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        pbLoadImage.setVisibility(View.GONE);
                    }
                });
    }
}
