// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.content.res.Resources;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;
import java.lang.SuppressWarnings;

public class AdminMainActivity$$ViewBinder<T extends AdminMainActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    Resources res = finder.getContext(source).getResources();
    return new InnerUnbinder<>(target, finder, source, res);
  }

  protected static class InnerUnbinder<T extends AdminMainActivity> implements Unbinder {
    protected T target;

    @SuppressWarnings("ResourceType")
    protected InnerUnbinder(T target, Finder finder, Object source, Resources res) {
      this.target = target;

      target.toolbar = finder.findRequiredViewAsType(source, 2131689670, "field 'toolbar'", Toolbar.class);
      target.collapsingToolbarLayout = finder.findRequiredViewAsType(source, 2131689668, "field 'collapsingToolbarLayout'", CollapsingToolbarLayout.class);
      target.navigationView = finder.findRequiredViewAsType(source, 2131689701, "field 'navigationView'", NavigationView.class);
      target.viewPager = finder.findRequiredViewAsType(source, 2131689672, "field 'viewPager'", AutoScrollViewPager.class);
      target.drawerLayout = finder.findRequiredViewAsType(source, 2131689694, "field 'drawerLayout'", DrawerLayout.class);
      target.rvHomeMenu = finder.findRequiredViewAsType(source, 2131689699, "field 'rvHomeMenu'", RecyclerView.class);
      target.llLatestNewsAndEvents = finder.findRequiredViewAsType(source, 2131689698, "field 'llLatestNewsAndEvents'", LinearLayout.class);
      target.appBarLayout = finder.findRequiredViewAsType(source, 2131689667, "field 'appBarLayout'", AppBarLayout.class);
      target.tvNotification = finder.findRequiredViewAsType(source, 2131689697, "field 'tvNotification'", TextView.class);
      target.llHeader = finder.findRequiredViewAsType(source, 2131689673, "field 'llHeader'", LinearLayout.class);
      target.ivMayorImage = finder.findRequiredViewAsType(source, 2131689695, "field 'ivMayorImage'", ImageView.class);

      target.profilePicSize = res.getDimensionPixelSize(2131296857);
      target.headerDisasterManagement = res.getString(2131230830);
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
      target.llLatestNewsAndEvents = null;
      target.appBarLayout = null;
      target.tvNotification = null;
      target.llHeader = null;
      target.ivMayorImage = null;

      this.target = null;
    }
  }
}
