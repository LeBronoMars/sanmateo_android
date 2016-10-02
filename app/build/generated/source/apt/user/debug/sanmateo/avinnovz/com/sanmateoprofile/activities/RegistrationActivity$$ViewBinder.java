// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import com.rey.material.widget.Spinner;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class RegistrationActivity$$ViewBinder<T extends RegistrationActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends RegistrationActivity> implements Unbinder {
    protected T target;

    private View view2131689674;

    private View view2131689693;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.etFirstName = finder.findRequiredViewAsType(source, 2131689716, "field 'etFirstName'", EditText.class);
      target.etLastName = finder.findRequiredViewAsType(source, 2131689717, "field 'etLastName'", EditText.class);
      target.etContactNo = finder.findRequiredViewAsType(source, 2131689718, "field 'etContactNo'", EditText.class);
      target.etAddress = finder.findRequiredViewAsType(source, 2131689719, "field 'etAddress'", EditText.class);
      target.etEmail = finder.findRequiredViewAsType(source, 2131689721, "field 'etEmail'", EditText.class);
      target.etPassword = finder.findRequiredViewAsType(source, 2131689722, "field 'etPassword'", EditText.class);
      target.etConfirmPassword = finder.findRequiredViewAsType(source, 2131689723, "field 'etConfirmPassword'", EditText.class);
      target.spnrGender = finder.findRequiredViewAsType(source, 2131689720, "field 'spnrGender'", Spinner.class);
      view = finder.findRequiredView(source, 2131689674, "method 'onClick'");
      view2131689674 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick(p0);
        }
      });
      view = finder.findRequiredView(source, 2131689693, "method 'onClick'");
      view2131689693 = view;
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

      target.etFirstName = null;
      target.etLastName = null;
      target.etContactNo = null;
      target.etAddress = null;
      target.etEmail = null;
      target.etPassword = null;
      target.etConfirmPassword = null;
      target.spnrGender = null;

      view2131689674.setOnClickListener(null);
      view2131689674 = null;
      view2131689693.setOnClickListener(null);
      view2131689693 = null;

      this.target = null;
    }
  }
}
