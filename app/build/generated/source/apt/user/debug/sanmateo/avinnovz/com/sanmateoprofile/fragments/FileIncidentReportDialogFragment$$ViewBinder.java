// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.support.v7.widget.RecyclerView;
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

public class FileIncidentReportDialogFragment$$ViewBinder<T extends FileIncidentReportDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends FileIncidentReportDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689794;

    private View view2131689796;

    private View view2131689764;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.etIncidentDescription = finder.findRequiredViewAsType(source, 2131689791, "field 'etIncidentDescription'", EditText.class);
      target.etIncidentLocation = finder.findRequiredViewAsType(source, 2131689792, "field 'etIncidentLocation'", EditText.class);
      target.spnrIncidentType = finder.findRequiredViewAsType(source, 2131689793, "field 'spnrIncidentType'", Spinner.class);
      target.rvImages = finder.findRequiredViewAsType(source, 2131689795, "field 'rvImages'", RecyclerView.class);
      view = finder.findRequiredView(source, 2131689794, "field 'llAddPhoto' and method 'addPhoto'");
      target.llAddPhoto = finder.castView(view, 2131689794, "field 'llAddPhoto'");
      view2131689794 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.addPhoto();
        }
      });
      view = finder.findRequiredView(source, 2131689796, "method 'fileIncidentReport'");
      view2131689796 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.fileIncidentReport();
        }
      });
      view = finder.findRequiredView(source, 2131689764, "method 'cancelReport'");
      view2131689764 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.cancelReport();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.etIncidentDescription = null;
      target.etIncidentLocation = null;
      target.spnrIncidentType = null;
      target.rvImages = null;
      target.llAddPhoto = null;

      view2131689794.setOnClickListener(null);
      view2131689794 = null;
      view2131689796.setOnClickListener(null);
      view2131689796 = null;
      view2131689764.setOnClickListener(null);
      view2131689764 = null;

      this.target = null;
    }
  }
}
