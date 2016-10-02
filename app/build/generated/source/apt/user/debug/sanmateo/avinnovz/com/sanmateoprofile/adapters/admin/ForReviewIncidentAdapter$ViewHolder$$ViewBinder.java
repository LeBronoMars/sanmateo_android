// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters.admin;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ForReviewIncidentAdapter$ViewHolder$$ViewBinder<T extends ForReviewIncidentAdapter.ViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends ForReviewIncidentAdapter.ViewHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.tvDescription = finder.findRequiredViewAsType(source, 2131689805, "field 'tvDescription'", TextView.class);
      target.tvAddress = finder.findRequiredViewAsType(source, 2131689847, "field 'tvAddress'", TextView.class);
      target.tvDateReported = finder.findRequiredViewAsType(source, 2131689708, "field 'tvDateReported'", TextView.class);
      target.tvTimeAgo = finder.findRequiredViewAsType(source, 2131689848, "field 'tvTimeAgo'", TextView.class);
      target.tvReportedBy = finder.findRequiredViewAsType(source, 2131689707, "field 'tvReportedBy'", TextView.class);
      target.civReporterImage = finder.findRequiredViewAsType(source, 2131689846, "field 'civReporterImage'", CircleImageView.class);
      target.civMaliciousReportBy = finder.findRequiredViewAsType(source, 2131689850, "field 'civMaliciousReportBy'", CircleImageView.class);
      target.rvImages = finder.findRequiredViewAsType(source, 2131689795, "field 'rvImages'", RecyclerView.class);
      target.llBlock = finder.findRequiredViewAsType(source, 2131689855, "field 'llBlock'", LinearLayout.class);
      target.llApprove = finder.findRequiredViewAsType(source, 2131689854, "field 'llApprove'", LinearLayout.class);
      target.llUnblockReport = finder.findRequiredViewAsType(source, 2131689849, "field 'llUnblockReport'", LinearLayout.class);
      target.llApproveBlock = finder.findRequiredViewAsType(source, 2131689853, "field 'llApproveBlock'", LinearLayout.class);
      target.tvMaliciousReportBy = finder.findRequiredViewAsType(source, 2131689851, "field 'tvMaliciousReportBy'", TextView.class);
      target.tvRemarks = finder.findRequiredViewAsType(source, 2131689852, "field 'tvRemarks'", TextView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.tvDescription = null;
      target.tvAddress = null;
      target.tvDateReported = null;
      target.tvTimeAgo = null;
      target.tvReportedBy = null;
      target.civReporterImage = null;
      target.civMaliciousReportBy = null;
      target.rvImages = null;
      target.llBlock = null;
      target.llApprove = null;
      target.llUnblockReport = null;
      target.llApproveBlock = null;
      target.tvMaliciousReportBy = null;
      target.tvRemarks = null;

      this.target = null;
    }
  }
}
