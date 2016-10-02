// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class AnnouncementsAdapter$ViewHolder$$ViewBinder<T extends AnnouncementsAdapter.ViewHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends AnnouncementsAdapter.ViewHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.tvTitle = finder.findRequiredViewAsType(source, 2131689759, "field 'tvTitle'", TextView.class);
      target.tvMessage = finder.findRequiredViewAsType(source, 2131689758, "field 'tvMessage'", TextView.class);
      target.tvDateAnnounced = finder.findRequiredViewAsType(source, 2131689836, "field 'tvDateAnnounced'", TextView.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.tvTitle = null;
      target.tvMessage = null;
      target.tvDateAnnounced = null;

      this.target = null;
    }
  }
}
