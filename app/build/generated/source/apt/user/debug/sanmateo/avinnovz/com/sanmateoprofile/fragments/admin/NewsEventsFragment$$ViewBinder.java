// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class NewsEventsFragment$$ViewBinder<T extends NewsEventsFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends NewsEventsFragment> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.swipeRefreshLayout = finder.findRequiredViewAsType(source, 2131689815, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
      target.rvNews = finder.findRequiredViewAsType(source, 2131689816, "field 'rvNews'", RecyclerView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.swipeRefreshLayout = null;
      target.rvNews = null;

      this.target = null;
    }
  }
}
