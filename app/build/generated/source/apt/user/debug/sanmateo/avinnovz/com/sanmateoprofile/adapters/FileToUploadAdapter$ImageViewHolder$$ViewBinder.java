// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class FileToUploadAdapter$ImageViewHolder$$ViewBinder<T extends FileToUploadAdapter.ImageViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends FileToUploadAdapter.ImageViewHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.ivImage = finder.findRequiredViewAsType(source, 2131689803, "field 'ivImage'", ImageView.class);
      target.ivRemove = finder.findRequiredViewAsType(source, 2131689778, "field 'ivRemove'", ImageView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.ivImage = null;
      target.ivRemove = null;

      this.target = null;
    }
  }
}
