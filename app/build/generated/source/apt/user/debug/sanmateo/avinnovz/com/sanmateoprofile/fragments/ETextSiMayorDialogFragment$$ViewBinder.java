// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments;

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

public class ETextSiMayorDialogFragment$$ViewBinder<T extends ETextSiMayorDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends ETextSiMayorDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689790;

    private View view2131689768;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.spnrClassification = finder.findRequiredViewAsType(source, 2131689789, "field 'spnrClassification'", Spinner.class);
      target.etMessage = finder.findRequiredViewAsType(source, 2131689770, "field 'etMessage'", EditText.class);
      view = finder.findRequiredView(source, 2131689790, "method 'sendSMSToMayor'");
      view2131689790 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.sendSMSToMayor();
        }
      });
      view = finder.findRequiredView(source, 2131689768, "method 'cancel'");
      view2131689768 = view;
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

      target.spnrClassification = null;
      target.etMessage = null;

      view2131689790.setOnClickListener(null);
      view2131689790 = null;
      view2131689768.setOnClickListener(null);
      view2131689768 = null;

      this.target = null;
    }
  }
}