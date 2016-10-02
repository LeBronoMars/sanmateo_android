// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class GalleryAdapter$ViewHolder$$ViewBinder<T extends GalleryAdapter.ViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends GalleryAdapter.ViewHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.ivPhoto = finder.findRequiredViewAsType(source, 2131689856, "field 'ivPhoto'", ImageView.class);
      target.tvTitle = finder.findRequiredViewAsType(source, 2131689759, "field 'tvTitle'", TextView.class);
      target.pbLoadImage = finder.findRequiredViewAsType(source, 2131689711, "field 'pbLoadImage'", ProgressBar.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.ivPhoto = null;
      target.tvTitle = null;
      target.pbLoadImage = null;

      this.target = null;
    }
  }
}
