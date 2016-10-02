// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class EmergencyKitAdapter$ViewHolder$$ViewBinder<T extends EmergencyKitAdapter.ViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends EmergencyKitAdapter.ViewHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.tvEmergencyKit = finder.findRequiredViewAsType(source, 2131689843, "field 'tvEmergencyKit'", TextView.class);
      target.cbEmergencyKit = finder.findRequiredViewAsType(source, 2131689842, "field 'cbEmergencyKit'", CheckBox.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.tvEmergencyKit = null;
      target.cbEmergencyKit = null;

      this.target = null;
    }
  }
}
