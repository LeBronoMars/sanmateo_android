// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class Disaster101Activity$$ViewBinder<T extends Disaster101Activity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends Disaster101Activity> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.viewPager = finder.findRequiredViewAsType(source, 2131689672, "field 'viewPager'", ViewPager.class);
      target.tabLayout = finder.findRequiredViewAsType(source, 2131689671, "field 'tabLayout'", TabLayout.class);
      target.ivPaalala = finder.findRequiredViewAsType(source, 2131689679, "field 'ivPaalala'", ImageView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.viewPager = null;
      target.tabLayout = null;
      target.ivPaalala = null;

      this.target = null;
    }
  }
}
