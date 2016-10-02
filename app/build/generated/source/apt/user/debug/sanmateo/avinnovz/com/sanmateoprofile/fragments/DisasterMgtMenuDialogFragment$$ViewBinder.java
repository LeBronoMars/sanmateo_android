// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class DisasterMgtMenuDialogFragment$$ViewBinder<T extends DisasterMgtMenuDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends DisasterMgtMenuDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689788;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.tvHeader = finder.findRequiredViewAsType(source, 2131689680, "field 'tvHeader'", TextView.class);
      target.lvDisasterMenu = finder.findRequiredViewAsType(source, 2131689787, "field 'lvDisasterMenu'", ListView.class);
      view = finder.findRequiredView(source, 2131689788, "method 'cancel'");
      view2131689788 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.cancel();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.tvHeader = null;
      target.lvDisasterMenu = null;

      view2131689788.setOnClickListener(null);
      view2131689788 = null;

      this.target = null;
    }
  }
}
