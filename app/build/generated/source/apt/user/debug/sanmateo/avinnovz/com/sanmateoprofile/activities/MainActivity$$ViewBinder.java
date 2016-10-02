// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MainActivity$$ViewBinder<T extends MainActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends MainActivity> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.toolbar = finder.findRequiredViewAsType(source, 2131689670, "field 'toolbar'", Toolbar.class);
      target.collapsingToolbarLayout = finder.findRequiredViewAsType(source, 2131689668, "field 'collapsingToolbarLayout'", CollapsingToolbarLayout.class);
      target.navigationView = finder.findRequiredViewAsType(source, 2131689701, "field 'navigationView'", NavigationView.class);
      target.viewPager = finder.findRequiredViewAsType(source, 2131689672, "field 'viewPager'", AutoScrollViewPager.class);
      target.drawerLayout = finder.findRequiredViewAsType(source, 2131689694, "field 'drawerLayout'", DrawerLayout.class);
      target.rvHomeMenu = finder.findRequiredViewAsType(source, 2131689699, "field 'rvHomeMenu'", RecyclerView.class);
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

      this.target = null;
    }
  }
}
