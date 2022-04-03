package coding.insight.cleanuiloginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    Button userdata , docdata, hosdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userdata = findViewById(R.id.buttonuser);
        docdata = findViewById(R.id.buttonup);
        hosdata = findViewById(R.id.buttonhosp);



        userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, userData.class);
                startActivity(intent);
            }
        });

        docdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , activity_doctor_data.class);
                startActivity(intent);
            }
        });

        hosdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminHospitalData.class);
                startActivity(intent);
            }
        });
    }
}