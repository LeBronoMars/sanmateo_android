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

public class ChangePasswordDialogFragment$$ViewBinder<T extends ChangePasswordDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends ChangePasswordDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689767;

    private View view2131689768;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.etOldPassword = finder.findRequiredViewAsType(source, 2131689765, "field 'etOldPassword'", EditText.class);
      target.etNewPassword = finder.findRequiredViewAsType(source, 2131689766, "field 'etNewPassword'", EditText.class);
      target.etConfirmPassword = finder.findRequiredViewAsType(source, 2131689723, "field 'etConfirmPassword'", EditText.class);
      view = finder.findRequiredView(source, 2131689767, "method 'onClick'");
      view2131689767 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
      view = finder.findRequiredView(source, 2131689768, "method 'onClick'");
      view2131689768 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.etOldPassword = null;
      target.etNewPassword = null;
      target.etConfirmPassword = null;

      view2131689767.setOnClickListener(null);
      view2131689767 = null;
      view2131689768.setOnClickListener(null);
      view2131689768 = null;

      this.target = null;
    }
  }
}
