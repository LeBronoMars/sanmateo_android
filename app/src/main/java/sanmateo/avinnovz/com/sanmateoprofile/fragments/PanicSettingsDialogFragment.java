package sanmateo.avinnovz.com.sanmateoprofile.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.activities.BaseActivity;

/**
 * Created by rsbulanon on 7/14/16.
 */
public class PanicSettingsDialogFragment extends DialogFragment {

    private View view;
    private BaseActivity activity;
    private OnPanicContactListener onPanicContactListener;
    @BindView(R.id.lvContacts) ListView lvContacts;
    @BindView(R.id.tvNoContact) TextView tvNoContact;
    private static final int READ_PHONEBOOK = 1;
    private ArrayList<PanicContact> contacts = new ArrayList<>();

    public static PanicSettingsFragment newInstance() {
        final PanicSettingsFragment frag = new PanicSettingsFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.modal_panic_settings,null);
        activity = (MainActivity)getActivity();
        contacts.clear();
        contacts.addAll((ArrayList) DaoHelper.getAllPanicContacs());
        ButterKnife.inject(this, view);
        lvContacts.setAdapter(new PanicContactsAdapter(activity, this, contacts));
        checkContactSize();
        final Dialog mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(view);
        mDialog.setCancelable(false);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        view.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Prefs.getMyIntPref(activity,"panicContactSize") == 0) {
                    activity.showToast("Please add atleast one contact person!");
                } else {
                    mDialog.dismiss();
                }
            }
        });
        return mDialog;
    }

    public void refreshList() {
        contacts.clear();
        contacts.addAll(DaoHelper.getAllPanicContacs());
        ((BaseAdapter)lvContacts.getAdapter()).notifyDataSetChanged();
        checkContactSize();
    }

    private void checkContactSize() {
        if (DaoHelper.getPanicContactSize() == 0) {
            tvNoContact.setVisibility(View.VISIBLE);
            lvContacts.setVisibility(View.GONE);
        } else {
            tvNoContact.setVisibility(View.GONE);
            lvContacts.setVisibility(View.VISIBLE);
        }
        Prefs.setMyIntPref(activity, "panicContactSize",(int)DaoHelper.getPanicContactSize());
    }

    @OnClick(R.id.btnSelect)
    public void openContacts() {
        onPanicContactListener.onOpenPhoneBook();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onPanicContactListener.onDismiss();
    }

    public interface OnPanicContactListener {
        void onOpenPhoneBook();
        void onDismiss();
    }

    public void setOnPanicContactListener(OnPanicContactListener onPanicContactListener) {
        this.onPanicContactListener = onPanicContactListener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_PHONEBOOK) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contact = data.getData();
                ContentResolver cr = getActivity().getContentResolver();
                Cursor c = getActivity().getContentResolver().query(contact, null, null, null, null);
                while (c.moveToNext()) {
                    String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if (DaoHelper.isContactExisting(phone)) {
                                activity.showToast("Contact already in your list");
                            } else {
                                DaoHelper.addContact(new PanicContact(null, name, phone));
                                activity.showToast("Contact successfully added!");
                                refreshList();
                            }
                        }
                    } else {
                        activity.showToast("Empty phone number!");
                    }
                }
            }
        }
    }
}
