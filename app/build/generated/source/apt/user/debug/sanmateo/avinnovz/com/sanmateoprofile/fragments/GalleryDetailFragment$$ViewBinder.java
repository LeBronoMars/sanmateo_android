// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class GalleryDetailFragment$$ViewBinder<T extends GalleryDetailFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends GalleryDetailFragment> implements Unbinder {
    protected T target;

    private View view2131689761;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.ivImage = finder.findRequiredViewAsType(source, 2131689803, "field 'ivImage'", ImageView.class);
      target.pbLoadImage = finder.findRequiredViewAsType(source, 2131689711, "field 'pbLoadImage'", ProgressBar.class);
      target.tvTitle = finder.findRequiredViewAsType(source, 2131689759, "field 'tvTitle'", TextView.class);
      target.tvDescription = finder.findRequiredViewAsType(source, 2131689805, "field 'tvDescription'", TextView.class);
      view = finder.findRequiredView(source, 2131689761, "method 'closeDialog'");
      view2131689761 = view;
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

      target.ivImage = null;
      target.pbLoadImage = null;
      target.tvTitle = null;
      target.tvDescription = null;

      view2131689761.setOnClickListener(null);
      view2131689761 = null;

      this.target = null;
    }
  }
}
