package coding.insight.cleanuiloginregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    Button Hospital;

    EditText LoginEmail,LoginPassword;
    Button login;
    DatabaseReference hospitalDatabaseref;
    DatabaseReference userDatabaseRef;
    FirebaseAuth fauth;
    ProgressDialog loader;
    static int c=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        Hospital = findViewById(R.id.Hospital);
        Hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HospitalScreen.class);
                startActivity(intent);
            }
        });

        LoginEmail=findViewById(R.id.loginEmail);
        LoginPassword=findViewById(R.id.loginPassword);
        login=findViewById(R.id.cirLoginButton);
                fauth=FirebaseAuth.getInstance();
        loader=new ProgressDialog(this);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=LoginEmail.getText().toString().trim();
                String password=LoginPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    LoginEmail.setError("Field is required.");
                }
                if(TextUtils.isEmpty(password)){
                    LoginPassword.setError("Field is required.");
                }
                else{
                    loader.setMessage("Logging...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    hospitalDatabaseref = FirebaseDatabase.getInstance().getReference("hospital");

                    Query query = hospitalDatabaseref.orderByChild("Email").equalTo(email);
                    query.addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue()==null){
                                Log.e("Obtained Data","No Data");
                                SignInwithFirebase(email,password);
                            }
                            else{
                                for(DataSnapshot childSnapshot:snapshot.getChildren()){
                                    Log.e("Obtained Data", Objects.requireNonNull(childSnapshot.getValue()).toString());
                                    if(!password.equals(Objects.requireNonNull(childSnapshot.child("Password").getValue()).toString()) ) {
                                        Log.e("Obtained Data", "Password not match");
                                        SignInwithFirebase(email,password);
                                    }
                                    else{
                                        String HospitalId = Objects.requireNonNull(childSnapshot.child("ID").getValue()).toString();
                                        Intent intent = new Intent(LoginActivity.this, HospitalScreen.class);
                                        intent.putExtra("ID",HospitalId);
                                        startActivity(intent);
                                    }
                                }

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //SignInwithFirebase(email,password);

                }
            }
        });

        }

        public void SignInwithFirebase(String email,String password){
            fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Error occured...!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String currentUserID=fauth.getCurrentUser().getUid();
                        userDatabaseRef= FirebaseDatabase.getInstance().getReference("users");



                        userDatabaseRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                if(task.isSuccessful()){
                                    if(dataSnapshot.child(currentUserID).child("type").getValue(String.class).equals("user")){
                                        Toast.makeText(LoginActivity.this,"Logged in.",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                                        c++;
                                        loader.dismiss();
                                        finish();
                                    }
                                    else if(dataSnapshot.child(currentUserID).child("type").getValue(String.class).equals("admin")){
                                        Toast.makeText(LoginActivity.this,"Logged in.",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        c++;
                                        loader.dismiss();
                                        finish();
                                    }

                                }
                            }
                        });
                    }
                }
            });
        }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }
}
