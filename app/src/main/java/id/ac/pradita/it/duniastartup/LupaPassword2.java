package id.ac.pradita.it.duniastartup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LupaPassword2 extends AppCompatActivity {

    private TextView resend;
    private FirebaseAuth fbAuth;
    public EditText passEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password2);

        resend = (TextView)findViewById(R.id.txt_resend);
        fbAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        final String email = intent.getStringExtra(LupaPassword.EXTRA_TEXT);

        final TextView passEmail = (TextView)findViewById(R.id.email);
        passEmail.setText(email);

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LupaPassword2.this, "Password Email sent", Toast.LENGTH_SHORT).show();
//                            finish();
//                            startActivity(new Intent(LupaPassword.this, LupaPassword2.class));
                        }else{
                            Toast.makeText(LupaPassword2.this, "Error in Sending Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
