package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.AppConstants;
import sanmateo.avinnovz.com.sanmateoprofile.helpers.LogHelper;
import sanmateo.avinnovz.com.sanmateoprofile.models.response.News;

/**
 * Created by rsbulanon on 7/9/16.
 */
public class NewsFullPreviewActivity extends BaseActivity {

    @BindView(R.id.ivImageBanner) ImageView ivImageBanner;
    @BindView(R.id.tvNewsTitle) TextView tvNewsTitle;
    @BindView(R.id.tvReportedBy) TextView tvReportedBy;
    @BindView(R.id.tvDateReported) TextView tvDateReported;
    @BindView(R.id.tvBody) TextView tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full_preview);
        ButterKnife.bind(this);
        setToolbarTitle("Back");
        final News n = getIntent().getParcelableExtra("news");

        tvNewsTitle.setText(n.getTitle());
        tvReportedBy.setText("Reported By : " + n.getReportedBy());
        tvBody.setText(n.getBody());

        try {
            final Date dateReported = getDateFormatter().parse(n.getCreatedAt());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateReported);
            calendar.add(Calendar.HOUR_OF_DAY,8);
            tvDateReported.setText(getSDF().format(calendar.getTime()));
            tvDateReported.setText(getPrettyTime().format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            LogHelper.log("err","error in parsing date --> " + e.getMessage());
        }
        AppConstants.PICASSO.load(n.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .centerCrop()
                .fit()
                .into(ivImageBanner);
    }
}
