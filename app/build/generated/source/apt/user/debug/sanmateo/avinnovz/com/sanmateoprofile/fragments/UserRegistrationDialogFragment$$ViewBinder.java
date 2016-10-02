// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class UserRegistrationDialogFragment$$ViewBinder<T extends UserRegistrationDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends UserRegistrationDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689666;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.etEmail = finder.findRequiredViewAsType(source, 2131689721, "field 'etEmail'", EditText.class);
      target.etPassword = finder.findRequiredViewAsType(source, 2131689722, "field 'etPassword'", EditText.class);
      view = finder.findRequiredView(source, 2131689666, "method 'manageLogin'");
      view2131689666 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.manageLogin();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.etEmail = null;
      target.etPassword = null;

      view2131689666.setOnClickListener(null);
      view2131689666 = null;

      this.target = null;
    }
  }
}
