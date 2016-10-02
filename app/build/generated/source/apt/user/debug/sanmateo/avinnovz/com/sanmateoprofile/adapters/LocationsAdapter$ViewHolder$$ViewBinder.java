// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class LocationsAdapter$ViewHolder$$ViewBinder<T extends LocationsAdapter.ViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends LocationsAdapter.ViewHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.llLocation = finder.findRequiredViewAsType(source, 2131689863, "field 'llLocation'", LinearLayout.class);
      target.rlImageView = finder.findRequiredViewAsType(source, 2131689868, "field 'rlImageView'", RelativeLayout.class);
      target.tvLocationName = finder.findRequiredViewAsType(source, 2131689864, "field 'tvLocationName'", TextView.class);
      target.tvCategory = finder.findRequiredViewAsType(source, 2131689865, "field 'tvCategory'", TextView.class);
      target.tvLocationAddress = finder.findRequiredViewAsType(source, 2131689866, "field 'tvLocationAddress'", TextView.class);
      target.tvContactNo = finder.findRequiredViewAsType(source, 2131689867, "field 'tvContactNo'", TextView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.llLocation = null;
      target.rlImageView = null;
      target.tvLocationName = null;
      target.tvCategory = null;
      target.tvLocationAddress = null;
      target.tvContactNo = null;

      this.target = null;
    }
  }
}
