// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class NewsFullPreviewActivity$$ViewBinder<T extends NewsFullPreviewActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends NewsFullPreviewActivity> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.ivImageBanner = finder.findRequiredViewAsType(source, 2131689705, "field 'ivImageBanner'", ImageView.class);
      target.tvNewsTitle = finder.findRequiredViewAsType(source, 2131689706, "field 'tvNewsTitle'", TextView.class);
      target.tvReportedBy = finder.findRequiredViewAsType(source, 2131689707, "field 'tvReportedBy'", TextView.class);
      target.tvDateReported = finder.findRequiredViewAsType(source, 2131689708, "field 'tvDateReported'", TextView.class);
      target.tvBody = finder.findRequiredViewAsType(source, 2131689709, "field 'tvBody'", TextView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.ivImageBanner = null;
      target.tvNewsTitle = null;
      target.tvReportedBy = null;
      target.tvDateReported = null;
      target.tvBody = null;

      this.target = null;
    }
  }
}
