// Generated code from Butter Knife. Do not modify!
package sanmateo.avinnovz.com.sanmateoprofile.fragments.admin;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class AddOfficialDialogFragment$$ViewBinder<T extends AddOfficialDialogFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends AddOfficialDialogFragment> implements Unbinder {
    protected T target;

    private View view2131689779;

    private View view2131689768;

    private View view2131689775;

    private View view2131689778;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.tvHeader = finder.findRequiredViewAsType(source, 2131689680, "field 'tvHeader'", TextView.class);
      target.etFirstName = finder.findRequiredViewAsType(source, 2131689716, "field 'etFirstName'", EditText.class);
      target.etLastName = finder.findRequiredViewAsType(source, 2131689717, "field 'etLastName'", EditText.class);
      target.etNickName = finder.findRequiredViewAsType(source, 2131689784, "field 'etNickName'", EditText.class);
      target.etPosition = finder.findRequiredViewAsType(source, 2131689785, "field 'etPosition'", EditText.class);
      target.etBackground = finder.findRequiredViewAsType(source, 2131689786, "field 'etBackground'", EditText.class);
      target.etImageURL = finder.findRequiredViewAsType(source, 2131689774, "field 'etImageURL'", EditText.class);
      target.llManualInput = finder.findRequiredViewAsType(source, 2131689773, "field 'llManualInput'", LinearLayout.class);
      target.rlImagePreview = finder.findRequiredViewAsType(source, 2131689776, "field 'rlImagePreview'", RelativeLayout.class);
      target.ivImagePreview = finder.findRequiredViewAsType(source, 2131689777, "field 'ivImagePreview'", ImageView.class);
      view = finder.findRequiredView(source, 2131689779, "field 'btnCreate' and method 'createNews'");
      target.btnCreate = finder.castView(view, 2131689779, "field 'btnCreate'");
      view2131689779 = view;
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
      view = finder.findRequiredView(source, 2131689775, "method 'selectFromGallery'");
      view2131689775 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.selectFromGallery();
        }
      });
      view = finder.findRequiredView(source, 2131689778, "method 'removeSelectedImage'");
      view2131689778 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.removeSelectedImage();
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.tvHeader = null;
      target.etFirstName = null;
      target.etLastName = null;
      target.etNickName = null;
      target.etPosition = null;
      target.etBackground = null;
      target.etImageURL = null;
      target.llManualInput = null;
      target.rlImagePreview = null;
      target.ivImagePreview = null;
      target.btnCreate = null;

      view2131689779.setOnClickListener(null);
      view2131689779 = null;
      view2131689768.setOnClickListener(null);
      view2131689768 = null;
      view2131689775.setOnClickListener(null);
      view2131689775 = null;
      view2131689778.setOnClickListener(null);
      view2131689778 = null;

      this.target = null;
    }
  }
}
