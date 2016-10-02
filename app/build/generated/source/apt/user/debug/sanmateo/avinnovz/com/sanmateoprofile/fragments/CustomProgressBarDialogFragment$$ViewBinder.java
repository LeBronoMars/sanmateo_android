// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class CustomProgressBarDialogFragment$$ViewBinder<T extends CustomProgressBarDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends CustomProgressBarDialogFragment> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.progressBar = finder.findRequiredViewAsType(source, 2131689757, "field 'progressBar'", ProgressBar.class);
      target.tvProgress = finder.findRequiredViewAsType(source, 2131689756, "field 'tvProgress'", TextView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.progressBar = null;
      target.tvProgress = null;

      this.target = null;
    }
  }
}
