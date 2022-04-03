package coding.insight.cleanuiloginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HospitalScreen extends AppCompatActivity {
    Button hosinfo;
    String HospitalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_screen);

        hosinfo = findViewById(R.id.buttonhosinfo);

        HospitalID = getIntent().getStringExtra("ID");

        hosinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HospitalScreen.this, HospitalInfoPop.class);
                intent.putExtra("ID",HospitalID);
                startActivity(intent);
            }
        });

    }
}