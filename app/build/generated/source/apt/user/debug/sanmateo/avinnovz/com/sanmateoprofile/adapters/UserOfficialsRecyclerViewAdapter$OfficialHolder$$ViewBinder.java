// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.support.v7.widget.CardView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class UserOfficialsRecyclerViewAdapter$OfficialHolder$$ViewBinder<T extends UserOfficialsRecyclerViewAdapter.OfficialHolder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends UserOfficialsRecyclerViewAdapter.OfficialHolder> implements Unbinder {
    protected T target;

    protected InnerUnbinder(T target, Finder finder, Object source) {
      this.target = target;

      target.cvRoot = finder.findRequiredViewAsType(source, 2131689844, "field 'cvRoot'", CardView.class);
      target.civPic = finder.findRequiredViewAsType(source, 2131689710, "field 'civPic'", CircleImageView.class);
      target.tvOfficialName = finder.findRequiredViewAsType(source, 2131689712, "field 'tvOfficialName'", TextView.class);
      target.tvPosition = finder.findRequiredViewAsType(source, 2131689713, "field 'tvPosition'", TextView.class);
      target.pbLoadImage = finder.findRequiredViewAsType(source, 2131689711, "field 'pbLoadImage'", ProgressBar.class);
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.cvRoot = null;
      target.civPic = null;
      target.tvOfficialName = null;
      target.tvPosition = null;
      target.pbLoadImage = null;

      this.target = null;
    }
  }
}
