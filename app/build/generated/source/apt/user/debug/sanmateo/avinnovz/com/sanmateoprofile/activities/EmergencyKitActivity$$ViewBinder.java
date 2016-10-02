// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class EmergencyKitActivity$$ViewBinder<T extends EmergencyKitActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends EmergencyKitActivity> implements Unbinder {
    protected T target;

    private View view2131689677;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.lvEmergencyKits = finder.findRequiredViewAsType(source, 2131689681, "field 'lvEmergencyKits'", ListView.class);
      view = finder.findRequiredView(source, 2131689677, "method 'goToLink'");
      view2131689677 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.goToLink();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.lvEmergencyKits = null;

      view2131689677.setOnClickListener(null);
      view2131689677 = null;

      this.target = null;
    }
  }
}
