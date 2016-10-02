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

public class PanicSettingsDialogFragment$$ViewBinder<T extends PanicSettingsDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends PanicSettingsDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689799;

    private View view2131689788;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.lvContacts = finder.findRequiredViewAsType(source, 2131689801, "field 'lvContacts'", ListView.class);
      target.tvNoContact = finder.findRequiredViewAsType(source, 2131689800, "field 'tvNoContact'", TextView.class);
      view = finder.findRequiredView(source, 2131689799, "method 'openContacts'");
      view2131689799 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.openContacts();
        }
      });
      view = finder.findRequiredView(source, 2131689788, "method 'closeWindow'");
      view2131689788 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.closeWindow();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.lvContacts = null;
      target.tvNoContact = null;

      view2131689799.setOnClickListener(null);
      view2131689799 = null;
      view2131689788.setOnClickListener(null);
      view2131689788 = null;

      this.target = null;
    }
  }
}
