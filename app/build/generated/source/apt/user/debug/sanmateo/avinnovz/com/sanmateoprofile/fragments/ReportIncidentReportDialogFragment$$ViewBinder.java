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

public class ReportIncidentReportDialogFragment$$ViewBinder<T extends ReportIncidentReportDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends ReportIncidentReportDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689806;

    private View view2131689764;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.etRemarks = finder.findRequiredViewAsType(source, 2131689762, "field 'etRemarks'", EditText.class);
      view = finder.findRequiredView(source, 2131689806, "method 'submitReport'");
      view2131689806 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.submitReport();
        }
      });
      view = finder.findRequiredView(source, 2131689764, "method 'cancel'");
      view2131689764 = view;
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

      target.etRemarks = null;

      view2131689806.setOnClickListener(null);
      view2131689806 = null;
      view2131689764.setOnClickListener(null);
      view2131689764 = null;

      this.target = null;
    }
  }
}
