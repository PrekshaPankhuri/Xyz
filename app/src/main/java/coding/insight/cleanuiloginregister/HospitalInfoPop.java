package coding.insight.cleanuiloginregister;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class HospitalInfoPop extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String hospitalId= "6l1th3CA5ehtKETxNjbE7Qm5WyZ2";
    TextView HospitalName;
    EditText mbedit;
    EditText oxedit;
    EditText bbedits;
    EditText covidedit;
    EditText vacedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_info_pop);

        HospitalName = findViewById(R.id.HospitalName);
        mbedit = findViewById(R.id.mbedit);
        oxedit = findViewById(R.id.oxedit);
        bbedits = findViewById(R.id.bbedits);
        covidedit = findViewById(R.id.covidedit);
        vacedit = findViewById(R.id.vacedit);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("hospital").child(hospitalId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.e("firebase", String.valueOf(task.getResult().getValue()));
                    HospitalName.setText(Objects.requireNonNull(task.getResult().child("Name").getValue()).toString());
                    mbedit.setText(Objects.requireNonNull(task.getResult().child("Number").getValue()).toString());
                    oxedit.setText(Objects.requireNonNull(task.getResult().child("Cylinders").getValue()).toString());
                    bbedits.setText(Objects.requireNonNull(task.getResult().child("BloodBank").getValue()).toString());
                    covidedit.setText(Objects.requireNonNull(task.getResult().child("Beds").getValue()).toString());
                    vacedit.setText(Objects.requireNonNull(task.getResult().child("VaccineCentre").getValue()).toString());


                }
            }
        });


    }
}