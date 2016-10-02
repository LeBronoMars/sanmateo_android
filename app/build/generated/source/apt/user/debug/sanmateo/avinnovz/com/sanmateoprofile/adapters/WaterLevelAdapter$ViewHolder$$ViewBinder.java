// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class WaterLevelAdapter$ViewHolder$$ViewBinder<T extends WaterLevelAdapter.ViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends WaterLevelAdapter.ViewHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.tvWaterLevel = finder.findRequiredViewAsType(source, 2131689877, "field 'tvWaterLevel'", TextView.class);
      target.tvAlarmLevel = finder.findRequiredViewAsType(source, 2131689878, "field 'tvAlarmLevel'", TextView.class);
      target.tvDatePosted = finder.findRequiredViewAsType(source, 2131689845, "field 'tvDatePosted'", TextView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.tvWaterLevel = null;
      target.tvAlarmLevel = null;
      target.tvDatePosted = null;

      this.target = null;
    }
  }
}
