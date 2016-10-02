// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class AdminLoginActivity$$ViewBinder<T extends AdminLoginActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends AdminLoginActivity> implements Unbinder {
    protected T target;

    private View view2131689692;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      view = finder.findRequiredView(source, 2131689692, "field 'btnSignIn' and method 'showLoginDialogFragment'");
      target.btnSignIn = finder.castView(view, 2131689692, "field 'btnSignIn'");
      view2131689692 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.showLoginDialogFragment();
        }
      });
      target.btnCreateAccount = finder.findRequiredViewAsType(source, 2131689693, "field 'btnCreateAccount'", Button.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.btnSignIn = null;
      target.btnCreateAccount = null;

      view2131689692.setOnClickListener(null);
      view2131689692 = null;

      this.target = null;
    }
  }
}
