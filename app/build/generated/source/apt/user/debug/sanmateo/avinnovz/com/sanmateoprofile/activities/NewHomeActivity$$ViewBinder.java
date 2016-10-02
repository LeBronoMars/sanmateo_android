// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.res.Resources;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;
import java.lang.SuppressWarnings;

public class NewHomeActivity$$ViewBinder<T extends NewHomeActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    Resources res = finder.getContext(source).getResources();
    return new InnerUnbinder<>(target, finder, source, res);
  }

  protected static class InnerUnbinder<T extends NewHomeActivity> implements Unbinder {
    protected T target;

    private View view2131689695;

    private View view2131689696;

    private View view2131689700;

    @SuppressWarnings("ResourceType")
    protected InnerUnbinder(final T target, Finder finder, Object source, Resources res) {
      this.target = target;

      View view;
      target.toolbar = finder.findRequiredViewAsType(source, 2131689670, "field 'toolbar'", Toolbar.class);
      target.collapsingToolbarLayout = finder.findRequiredViewAsType(source, 2131689668, "field 'collapsingToolbarLayout'", CollapsingToolbarLayout.class);
      target.navigationView = finder.findRequiredViewAsType(source, 2131689701, "field 'navigationView'", NavigationView.class);
      target.viewPager = finder.findRequiredViewAsType(source, 2131689672, "field 'viewPager'", AutoScrollViewPager.class);
      target.drawerLayout = finder.findRequiredViewAsType(source, 2131689694, "field 'drawerLayout'", DrawerLayout.class);
      target.rvHomeMenu = finder.findRequiredViewAsType(source, 2131689699, "field 'rvHomeMenu'", RecyclerView.class);
      target.appBarLayout = finder.findRequiredViewAsType(source, 2131689667, "field 'appBarLayout'", AppBarLayout.class);
      target.tvNotification = finder.findRequiredViewAsType(source, 2131689697, "field 'tvNotification'", TextView.class);
      target.llHeader = finder.findRequiredViewAsType(source, 2131689673, "field 'llHeader'", LinearLayout.class);
      view = finder.findRequiredView(source, 2131689695, "method 'showMayorImage'");
      view2131689695 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.showMayorImage();
        }
      });
      view = finder.findRequiredView(source, 2131689696, "method 'showNotifications'");
      view2131689696 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.showNotifications();
        }
      });
      view = finder.findRequiredView(source, 2131689700, "method 'iTextSiMayor'");
      view2131689700 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.iTextSiMayor();
        }
      });

      target.profilePicSize = res.getDimensionPixelSize(2131296857);
      target.headerDisasterManagement = res.getString(2131230830);
      target.headerAlertNotifications = res.getString(2131230871);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.toolbar = null;
      target.collapsingToolbarLayout = null;
      target.navigationView = null;
      target.viewPager = null;
      target.drawerLayout = null;
      target.rvHomeMenu = null;
      target.appBarLayout = null;
      target.tvNotification = null;
      target.llHeader = null;

      view2131689695.setOnClickListener(null);
      view2131689695 = null;
      view2131689696.setOnClickListener(null);
      view2131689696 = null;
      view2131689700.setOnClickListener(null);
      view2131689700 = null;

      this.target = null;
    }
  }
}
