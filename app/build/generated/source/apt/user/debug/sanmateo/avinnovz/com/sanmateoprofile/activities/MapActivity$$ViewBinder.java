// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MapActivity$$ViewBinder<T extends MapActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends MapActivity> implements Unbinder {
    protected T target;

    private View view2131689673;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.viewPager = finder.findRequiredViewAsType(source, 2131689672, "field 'viewPager'", ViewPager.class);
      target.tabLayout = finder.findRequiredViewAsType(source, 2131689671, "field 'tabLayout'", TabLayout.class);
      target.appBarLayout = finder.findRequiredViewAsType(source, 2131689667, "field 'appBarLayout'", AppBarLayout.class);
      view = finder.findRequiredView(source, 2131689673, "method 'goBack'");
      view2131689673 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.goBack();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.viewPager = null;
      target.tabLayout = null;
      target.appBarLayout = null;

      view2131689673.setOnClickListener(null);
      view2131689673 = null;

      this.target = null;
    }
  }
}
