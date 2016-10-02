// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ImagePreviewDialogFragment$$ViewBinder<T extends ImagePreviewDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends ImagePreviewDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689788;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.ivLocationImage = finder.findRequiredViewAsType(source, 2131689797, "field 'ivLocationImage'", ImageView.class);
      view = finder.findRequiredView(source, 2131689788, "method 'closeDialog'");
      view2131689788 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.closeDialog();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.ivLocationImage = null;

      view2131689788.setOnClickListener(null);
      view2131689788 = null;

      this.target = null;
    }
  }
}
