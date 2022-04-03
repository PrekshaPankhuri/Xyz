package coding.insight.cleanuiloginregister;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class HospitalAppointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button DateButton;
    TextView DateTextView;

    private Spinner spinner;
    private Spinner spinner1;
    private Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_appointment);
        changeStatusBarColor();

        DateButton = findViewById(R.id.DateButton);
        DateTextView = findViewById(R.id.textView5);

        DateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
                }
        });





        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.problem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner1=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this, R.array.doctor, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        spinner2=findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

    }

    private void handleDateButton(){
        Calendar calendar = Calendar.getInstance();

        int YEAR=calendar.get(Calendar.YEAR);
        int MONTH=calendar.get(Calendar.MONTH);
        int DATE=calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String DateString=year+" "+month+" "+dayOfMonth;
                DateTextView.setText(DateString);
            }
        },YEAR,MONTH,DATE);
        datePickerDialog.show();

    }


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(HospitalAppointment.this,HomeScreen.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem=(String) parent.getItemAtPosition(position);
        Toast.makeText(this, "Problem : "+selectedItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}