// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class NewsAdapter$ViewHolder$$ViewBinder<T extends NewsAdapter.ViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends NewsAdapter.ViewHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.llNews = finder.findRequiredViewAsType(source, 2131689869, "field 'llNews'", LinearLayout.class);
      target.ivImageUrl = finder.findRequiredViewAsType(source, 2131689870, "field 'ivImageUrl'", ImageView.class);
      target.tvTitle = finder.findRequiredViewAsType(source, 2131689759, "field 'tvTitle'", TextView.class);
      target.tvReportedBy = finder.findRequiredViewAsType(source, 2131689707, "field 'tvReportedBy'", TextView.class);
      target.tvDateReported = finder.findRequiredViewAsType(source, 2131689708, "field 'tvDateReported'", TextView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.llNews = null;
      target.ivImageUrl = null;
      target.tvTitle = null;
      target.tvReportedBy = null;
      target.tvDateReported = null;

      this.target = null;
    }
  }
}
