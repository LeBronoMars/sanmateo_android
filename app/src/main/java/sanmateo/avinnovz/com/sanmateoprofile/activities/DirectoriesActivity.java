package sanmateo.avinnovz.com.sanmateoprofile.activities;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sanmateo.avinnovz.com.sanmateoprofile.R;
import sanmateo.avinnovz.com.sanmateoprofile.adapters.DirectoriesAdapter;

/**
 * Created by ctmanalo on 7/6/16.
 */
public class DirectoriesActivity extends BaseActivity {

    @BindView(R.id.lvDirectories) ExpandableListView lvDirectories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directories);
        ButterKnife.bind(this);
        setToolbarTitle("Directories");
        lvDirectories.setAdapter(new DirectoriesAdapter(this, getHeaders(), getData(), getEmail()));
    }

    private ArrayList<String> getHeaders () {
        ArrayList<String> header = new ArrayList<>();
        header.add("Office of the Mayor");
        header.add("Sanguniang Bayan");
        header.add("Committees and Chairperson");
        header.add("Municipal Accounting Office");
        header.add("Municipal Budget Office");
        header.add("Municipal Planning and Development Office");
        header.add("Municipal Engineering Office");
        header.add("Municipal Civil Registrar Office");
        header.add("Municipal Health Office");
        header.add("Municipal Treasurer's Office");
        header.add("Business Permit and Licensing Office");
        header.add("Municipal Social Welfare and Development Office");
        header.add("Municipal Department Heads");
        header.add("Municipal Assessor");
        header.add("Municipal Peso");
        header.add("Department of Agriculture");
        header.add("IT-Department");
        header.add("Sanitary Unit");
        header.add("DILG");
        header.add("MTFRO");
        header.add("MDRRMO");
        header.add("OPSS/TEG");
        header.add("Eagle Base Unit");
        header.add("UPAO/CDO");
        header.add("Rizal Parole and Probation Office No.2");
        header.add("Rizal Parole and Probation in Action");
        header.add("Rizal Parole and Probation in Action 2");
        return header;
    }

    private ArrayList<String> getData () {
        ArrayList<String> data = new ArrayList<>();
        data.add("0915-123-0001");
        data.add("0915-123-0002");
        data.add("0915-123-0003");
        data.add("0915-123-0004");
        data.add("0915-123-0005");
        data.add("0915-123-0006");
        data.add("0915-123-0007");
        data.add("0915-123-0008");
        data.add("0915-123-0009");
        data.add("0915-123-0010");
        data.add("0915-123-0011");
        data.add("0915-123-0012");
        data.add("0915-123-0013");
        data.add("0915-123-0014");
        data.add("0915-123-0015");
        data.add("0915-123-0016");
        data.add("0915-123-0017");
        data.add("0915-123-0018");
        data.add("0915-123-0019");
        data.add("0915-123-0020");
        data.add("0915-123-0021");
        data.add("0915-123-0022");
        data.add("0915-123-0023");
        data.add("0915-123-0024");
        data.add("0915-123-0025");
        data.add("0915-123-0026");
        data.add("0915-123-0027");
        return data;
    }

    private ArrayList<String> getEmail () {
        ArrayList<String> email = new ArrayList<>();
        email.add("sample1@gmail.com");
        email.add("sample2@gmail.com");
        email.add("sample3@gmail.com");
        email.add("sample4@gmail.com");
        email.add("sample5@gmail.com");
        email.add("sample6@gmail.com");
        email.add("sample7@gmail.com");
        email.add("sample8@gmail.com");
        email.add("sample9@gmail.com");
        email.add("sample10@gmail.com");
        email.add("sample11@gmail.com");
        email.add("sample12@gmail.com");
        email.add("sample13@gmail.com");
        email.add("sample14@gmail.com");
        email.add("sample15@gmail.com");
        email.add("sample16@gmail.com");
        email.add("sample17@gmail.com");
        email.add("sample18@gmail.com");
        email.add("sample19@gmail.com");
        email.add("sample20@gmail.com");
        email.add("sample21@gmail.com");
        email.add("sample22@gmail.com");
        email.add("sample23@gmail.com");
        email.add("sample24@gmail.com");
        email.add("sample25@gmail.com");
        email.add("sample26@gmail.com");
        email.add("sample27@gmail.com");
        return email;
    }
}
