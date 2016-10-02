// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class HomeMenuAdapter$ViewHolder$$ViewBinder<T extends HomeMenuAdapter.ViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends HomeMenuAdapter.ViewHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.llMenu = finder.findRequiredViewAsType(source, 2131689798, "field 'llMenu'", LinearLayout.class);
      target.ivMenuIcon = finder.findRequiredViewAsType(source, 2131689857, "field 'ivMenuIcon'", ImageView.class);
      target.tvMenuTitle = finder.findRequiredViewAsType(source, 2131689858, "field 'tvMenuTitle'", TextView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.llMenu = null;
      target.ivMenuIcon = null;
      target.tvMenuTitle = null;

      this.target = null;
    }
  }
}
