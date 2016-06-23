package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
}
