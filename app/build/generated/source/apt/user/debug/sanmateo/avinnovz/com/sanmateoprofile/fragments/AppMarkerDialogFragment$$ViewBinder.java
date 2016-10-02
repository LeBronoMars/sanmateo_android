// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class AppMarkerDialogFragment$$ViewBinder<T extends AppMarkerDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends AppMarkerDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689761;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.tvTitle = finder.findRequiredViewAsType(source, 2131689759, "field 'tvTitle'", TextView.class);
      target.viewPager = finder.findRequiredViewAsType(source, 2131689672, "field 'viewPager'", AutoScrollViewPager.class);
      view = finder.findRequiredView(source, 2131689761, "method 'closeDialog'");
      view2131689761 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.closeDialog();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.tvTitle = null;
      target.viewPager = null;

      view2131689761.setOnClickListener(null);
      view2131689761 = null;

      this.target = null;
    }
  }
}
