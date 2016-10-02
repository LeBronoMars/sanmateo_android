// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class CreateAnnouncementDialogFragment$$ViewBinder<T extends CreateAnnouncementDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends CreateAnnouncementDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689771;

    private View view2131689768;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.etTitle = finder.findRequiredViewAsType(source, 2131689769, "field 'etTitle'", EditText.class);
      target.etMessage = finder.findRequiredViewAsType(source, 2131689770, "field 'etMessage'", EditText.class);
      view = finder.findRequiredView(source, 2131689771, "method 'createNews'");
      view2131689771 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.createNews();
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

      target.etTitle = null;
      target.etMessage = null;

      view2131689771.setOnClickListener(null);
      view2131689771 = null;
      view2131689768.setOnClickListener(null);
      view2131689768 = null;

      this.target = null;
    }
  }
}
