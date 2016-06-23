package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import sanmateo.avinnovz.com.sanmateoprofile.fragments.CustomProgressDialogFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by rsbulanon on 6/22/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void setError(final TextView textView, final String message) {
        textView.setError(message);
        textView.requestFocus();
    }

    public boolean isValidEmail(final String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private CustomProgressDialogFragment customProgressDialogFragment;

    public void showCustomProgress(final String message) {
        if (customProgressDialogFragment == null) {
            customProgressDialogFragment = CustomProgressDialogFragment.newInstance(message);
            customProgressDialogFragment.setCancelable(false);
            customProgressDialogFragment.show(getFragmentManager(),"progress");
        }
    }

    public void dismissCustomProgress() {
        if (customProgressDialogFragment != null) {
            customProgressDialogFragment.dismiss();
            customProgressDialogFragment = null;
        }
    }
}
