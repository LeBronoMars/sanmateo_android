package sanmateo.avinnovz.com.sanmateoprofile.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;
import sanmateo.avinnovz.com.sanmateoprofile.dao.PanicContact;

/**
 * Created by rsbulanon on 7/11/15.
 */
public class PanicContactsAdapter extends BaseAdapter {

    private BaseActivity baseActivity;
    private Context context;
    private ArrayList<PanicContact> contacts;
    private OnDeleteContactListener onDeleteContactListener;

    public PanicContactsAdapter(Context context, ArrayList<PanicContact> contacts) {
        this.context = context;
        this.contacts = contacts;
        this.baseActivity = (BaseActivity)context;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public PanicContact getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final PanicContact contact = getItem(i);
        view = baseActivity.getLayoutInflater().inflate(R.layout.row_panic_contacts, null, false);
        final TextView tvContactName = (TextView)view.findViewById(R.id.tvContactName);
        final TextView tvContactNo = (TextView)view.findViewById(R.id.tvContactNo);
        final ImageView ivDelete = (ImageView)view.findViewById(R.id.ivDelete);

        tvContactName.setText(contact.getContactName());
        tvContactNo.setText(contact.getContactNo());
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteContactListener.onDeleteContact(contact);
            }
        });
        return view;
    }

    public interface OnDeleteContactListener {
        void onDeleteContact(PanicContact contact);
    }

    public void setOnDeleteContactListener(OnDeleteContactListener onDeleteContactListener) {
        this.onDeleteContactListener = onDeleteContactListener;
    }
}
