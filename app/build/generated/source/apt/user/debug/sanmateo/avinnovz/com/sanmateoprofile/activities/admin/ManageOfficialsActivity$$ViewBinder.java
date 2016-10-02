// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.activities.admin;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ManageOfficialsActivity$$ViewBinder<T extends ManageOfficialsActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends ManageOfficialsActivity> implements Unbinder {
    protected T target;

    private View view2131689686;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.rvOfficials = finder.findRequiredViewAsType(source, 2131689703, "field 'rvOfficials'", RecyclerView.class);
      view = finder.findRequiredView(source, 2131689686, "method 'addNewOfficial'");
      view2131689686 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.addNewOfficial();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.rvOfficials = null;

      view2131689686.setOnClickListener(null);
      view2131689686 = null;

      this.target = null;
    }
  }
}
